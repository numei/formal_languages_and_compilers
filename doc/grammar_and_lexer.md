# Grammar and Lexer Explanation

This document explains the supported lexer and grammar used by the nftables to
XML translator.

## Overview

The translator is intentionally small. It recognizes a simplified nftables
firewall subset and maps it to XML through JAXB model classes.

Pipeline:

```text
nftables input
    -> JFlex scanner
    -> Java CUP parser
    -> AST-like Java objects
    -> JAXB XML
    -> XSD validation in bash.sh
```

The lexer is defined in `src/scanner.jflex`.
The grammar is defined in `src/parser.cup`.

## JFlex Lexical Analysis

The scanner recognizes keywords, symbols, numbers, names, IPv4 forms, and
comments.

### Whitespace and Comments

```jflex
WhiteSpace = [ \t\r\n]+
comment = "#" [^\n]*
```

Whitespace and comments are ignored.

### Names and Numbers

```jflex
name = [a-zA-Z_][a-zA-Z0-9_]*
quoted_name = \"([^\"\\]|\\.)*\"
number = \-?[0-9]+
```

Supported table and chain names can be simple identifiers or quoted strings.
Numbers are returned as `Long` values so that XML fields such as `<id>` can
support long integer values.

### IPv4 Forms

```jflex
ipv4 = <octet> "." <octet> "." <octet> "." <octet>
cidr = <ipv4> "/" <number>
ip_range = <ipv4> "-" <ipv4>
```

The lexer only recognizes the shape of an IPv4 address, CIDR value, or range.
Semantic validation is done later in `Validator.java`:

- each IPv4 octet must be between 0 and 255
- CIDR prefixes must be between 0 and 32
- IPv4 ranges must have a start address less than or equal to the end address

### Keywords

The scanner recognizes only nftables-style input keywords. XML/XSD values such
as `ALLOW`, `DENY`, and `directional` are not lexer tokens.

Main token groups:

```text
table, chain
type, hook, priority, policy
ip
saddr, daddr, id
tcp, udp
sport, dport
accept, drop
```

### Lexical Errors

Any unrecognized character is reported with a short diagnostic:

```text
[LEXICAL] line <n>, column <m>: invalid character '<char>'
```

## CUP Grammar

The grammar parses the supported nftables structure:

```text
tables -> table*
table  -> table ip <name> { chains }
chain  -> chain <name> {
              type filter hook <hook> priority <number>;
              [policy accept|drop;]
              rules
          }
rule   -> matches [accept|drop] [;]
```

The action is optional because the XML schema defines a default action. When no
rule action is present, JAXB omits `<action>` and the XSD default applies.

## Table and Chain Rules

The XML schema used by this project has one `<node>` root and allows one
`<configuration>` under it. The grammar can recover from extra tables or chains,
but the parser reports an `[XSD]` diagnostic and keeps the first valid one.

```text
table ip filter_table {
    chain forward_chain {
        type filter hook forward priority 0; policy drop;
        ...
    }
}
```

Mapping:

```text
table -> <node>
chain -> <configuration>
policy accept -> defaultAction="ALLOW"
policy drop   -> defaultAction="DENY"
```

## Rule Matches

Supported matches:

```text
ip saddr <addr>
ip daddr <addr>
ip id <number>
tcp sport <port>
tcp dport <port>
udp sport <port>
udp dport <port>
```

Supported address values:

```text
192.168.1.10
192.168.1.0/24
192.168.1.10-192.168.1.20
{ 192.168.1.10, 192.168.1.20, 192.168.1.30-192.168.1.40 }
```

Each valid rule must contain both source and destination addresses because the
target XML `<elements>` requires `<source>` and `<destination>`.

## Semantic Validation

The parser calls `Validator.java` for value checks:

- priority must be one of the supported nftables priority values
- IPv4 octets must be valid
- CIDR prefixes must be valid
- IPv4 ranges must be ordered
- ports must be between 0 and 65535

Short validation errors use this form:

```text
[ERROR] line <n>, column <m>: invalid ipv4: <value>
[ERROR] line <n>, column <m>: invalid cidr: <value>
[ERROR] line <n>, column <m>: invalid port: <value>
[ERROR] line <n>, column <m>: invalid priority: <value>
```

## Error Recovery

The grammar uses CUP `error` productions around tables, chains, rules, address
sets, and ports. Recovery is intentionally limited: invalid pieces are reported
and skipped when the parser can safely continue.

Recovery examples are documented in `doc/invalid_syntax_recovery.md`.

## XML Generation

After parsing, `Main.java` receives a `Config` object. JAXB classes then produce
XML:

```text
Table           -> <node>
Chain           -> <configuration>
Firewall        -> <firewall>
FirewallElement -> <elements>
```

The generated XML is validated by `bash.sh` with:

```bash
xmllint --noout --schema xsd/verigraph.xsd <output.xml>
```
