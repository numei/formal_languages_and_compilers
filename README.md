# Formal Languages and Compilers Project

This project is a simple translator for a subset of `nftables` firewall configuration syntax. It uses JFlex for lexical analysis and Java CUP for syntax analysis, then generates an XML output file from the parsed configuration.

## Project Structure

```text
.
├── bash.sh              # Startup script
├── bash.bat             # Windows startup script
├── src/                 # Source code, grammar files, and sample input/output
│   ├── Main.java        # Program entry point
│   ├── scanner.jflex    # JFlex lexer definition
│   ├── parser.cup       # Java CUP parser definition
│   ├── input.txt        # Default input file
│   └── output.xml       # Default generated output file
├── src/tests/           # Test inputs and outputs
├── xsd/                 # XML schema files
└── doc/                 # Project notes and documentation
```

## Requirements

Make sure the following tools are installed and available from the command line:

- Java JDK
- JFlex
- Java CUP

The startup script expects `jflex` and `java_cup.Main` to be available in your environment.

## How to Run

### macOS / Linux / Git Bash

```bash
chmod +x bash.sh
./bash.sh
```

### Windows

Run the project from the repository root with Command Prompt or PowerShell:

```bat
bash.bat
```

The script will:

1. Generate the lexer from `src/scanner.jflex`
2. Generate the parser from `src/parser.cup`
3. Compile the Java source files
4. Run the translator with `src/input.txt`
5. Write the result to `src/output.xml`

## Manual Run

If you want to run the steps manually:

### macOS / Linux / Git Bash

```bash
cd src
jflex scanner.jflex
java java_cup.Main -expect 8 parser.cup
javac *.java
java Main input.txt output.xml
```

### Windows

```bat
cd src
jflex scanner.jflex
java java_cup.Main -expect 8 parser.cup
javac *.java
java Main input.txt output.xml
```

You can also provide your own input and output files:

```bash
cd src
java Main path/to/input.txt path/to/output.xml
```

## Tests

Sample test inputs are stored in `src/tests/`. To test another file, copy or reference it when running `Main`:

```bash
cd src
java Main tests/1/input.txt tests/1/output.xml
```

## License

This project is licensed under the MIT License. See `LICENSE` for details.
