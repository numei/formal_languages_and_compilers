# Formal Languages and Compilers Project

This project is a simplified end-to-end translator for a supported subset of
`nftables` firewall configuration syntax. It uses JFlex for lexical analysis,
Java CUP for syntax analysis, and JAXB to generate XML that is validated against
`xsd/verigraph.xsd`.

The supported language subset focuses on IPv4 firewall rules, TCP/UDP ports,
basic verdicts, and error recovery. It intentionally does not accept XML/XSD
keywords as input syntax.

## Project Structure

```text
.
├── bash.sh                         # macOS/Linux/Git Bash build and test script
├── bash.bat                        # Windows single-file build and run script
├── lib/                            # Optional local JAXB jars
├── src/
│   ├── Main.java                   # Program entry point
│   ├── Config.java                 # JAXB XML root/helper model
│   ├── Table.java                  # JAXB <node> model
│   ├── Chain.java                  # JAXB <configuration> model
│   ├── Firewall.java               # JAXB <firewall> model
│   ├── FirewallElement.java        # JAXB <elements> model
│   ├── Validator.java              # Value checks and nftables-to-XSD mapping
│   ├── scanner.jflex               # JFlex lexer definition
│   ├── parser.cup                  # Java CUP parser definition
│   ├── input.txt                   # Default input for --single mode
│   └── tests/                      # Valid and recovery test inputs
├── xsd/verigraph.xsd               # XML schema used by the test script
└── doc/
    ├── Context.pdf                 # Assignment context
    ├── Context.md                  # Text version of the assignment context
    ├── keywords.txt                # Supported nftables-to-XML mapping table
    └── invalid_syntax_recovery.md  # Recovery behavior notes
```

Generated files such as `src/parser.java`, `src/sym.java`, `src/scanner.java`,
compiled `.class` files, and generated XML outputs are ignored by Git.

## Requirements

Make sure the following tools are installed and available from the command line:

- Java JDK
- JFlex
- Java CUP
- JAXB 2.x jars
- `xmllint` for the macOS/Linux/Git Bash test script

The original course setup guide configures Java CUP through `PATH` and
`CLASSPATH`: <https://www.skenz.it/compilers/install_macos>.

JDK 15 does not include JAXB in the standard library, so the JAXB jars must be
available on the classpath. The scripts look for:

- jars placed directly in `lib/`
- jars in the Maven cache under `~/.m2/repository` on macOS/Linux or
  `%USERPROFILE%\.m2\repository` on Windows

If JAXB is missing, put the jars listed in `lib/README.md` into `lib/`, or
install them through Maven.

## Supported nftables Subset

The current implementation supports:

- `table ip <name> { ... }`
- `chain <name> { type filter hook <hook> priority <number>; ... }`
- optional chain policy: `policy accept;` or `policy drop;`
- IPv4 `ip saddr` and `ip daddr`
- IPv4 CIDR values
- IPv4 ranges such as `192.168.1.10-192.168.1.20`
- address sets such as `{ 192.168.1.10, 192.168.1.20 }`
- optional `ip id <number>`
- TCP/UDP `sport` and `dport`
- rule verdicts: `accept`, `drop`, and `queue`
- optional rule action, relying on the XSD default when omitted

The mapping from nftables syntax to XML tags is documented in
`doc/keywords.txt`.

Important mapping notes:

- `accept` maps to XML action `ALLOW`
- `drop` maps to XML action `DENY`
- `queue` maps to XML action `ALLOW_COND`
- `directional` is not an nftables keyword in this subset; the generated XML
  omits it and relies on the XSD default value `true`

## How to Run

### macOS / Linux / Git Bash

Run the full build and test suite:

```bash
chmod +x bash.sh
./bash.sh
```

The default script:

1. Generates the lexer from `src/scanner.jflex`
2. Generates the parser from `src/parser.cup`
3. Compiles the Java source files
4. Runs all test inputs under `src/tests/`
5. Writes each generated XML file to the same case folder as `output.xml`
6. Validates every generated XML file against `xsd/verigraph.xsd`
7. Prints the number of passing tests

Run only the default single input file:

```bash
./bash.sh --single
```

Single mode reads `src/input.txt` and writes `src/output.xml`.

### Windows

Run the project from the repository root with Command Prompt or PowerShell:

```bat
bash.bat
```

The Windows script currently performs a single-file build and run:

1. Generates the lexer from `src/scanner.jflex`
2. Generates the parser from `src/parser.cup`
3. Compiles the Java source files
4. Runs `src/input.txt`
5. Writes `src/output.xml`

The Windows script does not currently run the full numbered test suite or XSD
validation. Use `bash.sh` from Git Bash for the same batch behavior as
macOS/Linux.

## Manual Run

After generating and compiling the project, `Main` accepts an input file path
and an output XML path:

```bash
cd src
java -cp "$CP" Main path/to/input.txt path/to/output.xml
```

The exact classpath depends on where the JAXB jars are installed. See
`bash.sh` and `bash.bat` for the classpath used by the scripts.

## Tests

Valid test inputs are stored in:

```text
src/tests/1/input.txt
...
src/tests/10/input.txt
```

After running `./bash.sh`, each case also contains its generated XML:

```text
src/tests/1/output.xml
...
src/tests/10/output.xml
```

Recovery-oriented inputs are stored in:

```text
src/tests/recovery/*/input.txt
```

Their recovered XML outputs are written beside them:

```text
src/tests/recovery/*/output.xml
```

For recovery tests, a passing result means:

- the parser reports the syntax or schema-related issue with line and column
  information
- parsing continues when possible
- the recovered XML output validates against `xsd/verigraph.xsd`

See `doc/invalid_syntax_recovery.md` for details.

## Documentation

- `doc/keywords.txt`: supported nftables keywords and XML tag mapping
- `doc/invalid_syntax_recovery.md`: how invalid syntax recovery is handled
- `doc/Context.md`: text version of the assignment brief and questions

## License

This project is licensed under the MIT License. See `LICENSE` for details.
