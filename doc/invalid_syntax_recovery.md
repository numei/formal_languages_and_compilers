# Invalid Syntax Recovery

This document explains how the translator handles invalid nftables input while
still behaving like a small compiler front end.

## Goal

The assignment asks the translator to report syntax errors with precise line and
column information. This project goes one step further for selected errors: it
tries to recover after the error, keep parsing later valid input, and still
produce XML that conforms to `xsd/verigraph.xsd`.

Recovery does not mean the invalid input is accepted silently. A recovery test
passes only when the translator reports the problem and the recovered output is
valid XML.

## What Counts as a Passing Recovery Test

A recovery test is considered successful when all of the following are true:

1. The parser reports the syntax or schema-related issue.
2. The diagnostic includes a line and column.
3. The diagnostic includes a concise expected syntax message.
4. Parsing continues far enough to keep later valid rules, chains, or tables
   when recovery is possible.
5. The generated XML validates against `xsd/verigraph.xsd`.

The `bash.sh` script checks the final condition with `xmllint`. The parser
diagnostics are printed to standard error during the run.

## Diagnostic Format

The parser uses a small set of diagnostic labels:

```text
[SYNTAX]   unexpected token with line and column
[EXPECTED] concise syntax pattern expected at that point
[XSD]      schema-related limitation or XML mapping constraint
[ERROR]    semantic validation failure, such as an invalid IP range
[WARNING]  parse completed after one or more recovered errors
```

Example:

```text
[SYNTAX] line 5, column 52: unexpected RBRACE
[EXPECTED] firewall rule: ip saddr <ipv4|cidr> ip daddr <ipv4|cidr|range|set> [tcp|udp sport <number>] [tcp|udp dport <number>] [accept|drop|queue]
[XSD] current output maps nftables verdicts to XML actions: accept->ALLOW, drop->DENY, queue->ALLOW_COND; directional uses the XSD default true
```

## Recovery Cases Covered by Tests

The recovery inputs live under `src/tests/recovery/`.

```text
src/tests/recovery/1/input.txt
```

Invalid address set syntax. The parser reports the malformed set, skips the
invalid rule, and keeps the following valid rule.

```text
src/tests/recovery/2/input.txt
```

Invalid port value. The parser reports that `dport` expects a number and keeps
later valid input.

```text
src/tests/recovery/3/input.txt
```

Invalid chain header. The parser reports the broken chain syntax and recovers
when it reaches the next valid chain.

```text
src/tests/recovery/4/input.txt
```

Invalid table header. The parser reports the malformed table and recovers when
it reaches the next valid table.

```text
src/tests/recovery/5/input.txt
```

More than one valid chain in a table. This is valid nftables-style input, but
the current XML schema allows only one `<configuration>` under `<node>`. The
parser reports an `[XSD]` limitation and keeps the first valid chain.

```text
src/tests/recovery/6/input.txt
```

More than one valid table. The generated XML root is one `<node>`, so the parser
reports an `[XSD]` limitation and keeps the first valid table.

## Why Some Invalid Inputs Still Produce XML

The translator is designed to preserve the valid part of the input when doing
so does not violate the XML schema. This is useful for demonstration because it
shows both compiler-style diagnostics and end-to-end XML generation.

For example, if the first rule in a chain is invalid but a later rule is valid,
the invalid rule is discarded and the valid rule can still become an
`<elements>` entry.

## Limitations

Recovery is intentionally limited and deterministic:

- It does not attempt to repair arbitrary nftables syntax.
- It does not infer missing source or destination addresses.
- It does not emit XML/XSD keywords as nftables input.
- It does not parse a `directional` keyword; the XML relies on the XSD default
  value `true`.
- It maps the valid nftables verdict `queue` to XML action `ALLOW_COND`.

If parsing cannot produce any valid AST, `Main` exits with a non-zero status and
does not treat the input as a successful recovery.
