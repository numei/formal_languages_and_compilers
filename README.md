# Formal Languages and Compilers Project

This project is a simple translator for a subset of `nftables` firewall configuration syntax. It uses JFlex for lexical analysis, Java CUP for syntax analysis, and JAXB for XML generation.

## Project Structure

```text
.
├── bash.sh              # Startup script
├── bash.bat             # Windows startup script
├── lib/                 # Optional local JAXB jars
├── src/                 # Source code, grammar files, and sample input/output
│   ├── Main.java        # Program entry point
│   ├── Config.java      # JAXB XML root/helper model
│   ├── Table.java       # JAXB node model
│   ├── Chain.java       # JAXB configuration model
│   ├── Firewall.java    # JAXB firewall model
│   ├── FirewallElement.java
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
- JAXB 2.x jars

The startup scripts expect `jflex` and `java_cup.Main` to be available in your environment. The original course setup guide configures Java CUP through `PATH` and `CLASSPATH`: <https://www.skenz.it/compilers/install_macos>.

JDK 15 does not include JAXB in the standard library, so the JAXB jars must be added to the classpath. The scripts already include:

- jars placed directly in `lib/`
- jars in the Maven cache under `~/.m2/repository`

If JAXB is missing, put the jars listed in `lib/README.md` into `lib/`, or install them through Maven.

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
CP=".:$CLASSPATH:../lib/*:$HOME/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:$HOME/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:$HOME/.m2/repository/com/sun/xml/bind/jaxb-core/2.3.0/jaxb-core-2.3.0.jar:$HOME/.m2/repository/org/glassfish/jaxb/txw2/2.3.0/txw2-2.3.0.jar:$HOME/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:$HOME/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:$HOME/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:$HOME/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar"
java -cp "$CP" java_cup.Main parser.cup
javac -cp "$CP" *.java
java -cp "$CP" Main input.txt output.xml
```

### Windows

```bat
cd src
jflex scanner.jflex
set "M2_REPO=%USERPROFILE%\.m2\repository"
set "CP=.;%CLASSPATH%;..\lib\*;%M2_REPO%\javax\xml\bind\jaxb-api\2.3.1\jaxb-api-2.3.1.jar;%M2_REPO%\org\glassfish\jaxb\jaxb-runtime\2.3.1\jaxb-runtime-2.3.1.jar;%M2_REPO%\com\sun\xml\bind\jaxb-core\2.3.0\jaxb-core-2.3.0.jar;%M2_REPO%\org\glassfish\jaxb\txw2\2.3.0\txw2-2.3.0.jar;%M2_REPO%\com\sun\istack\istack-commons-runtime\3.0.7\istack-commons-runtime-3.0.7.jar;%M2_REPO%\org\jvnet\staxex\stax-ex\1.8\stax-ex-1.8.jar;%M2_REPO%\com\sun\xml\fastinfoset\FastInfoset\1.2.15\FastInfoset-1.2.15.jar;%M2_REPO%\javax\activation\javax.activation-api\1.2.0\javax.activation-api-1.2.0.jar"
java -cp "%CP%" java_cup.Main parser.cup
javac -cp "%CP%" *.java
java -cp "%CP%" Main input.txt output.xml
```

You can also provide your own input and output files:

```bash
cd src
java -cp "$CP" Main path/to/input.txt path/to/output.xml
```

## Tests

Sample test inputs are stored in `src/tests/`. To test another file, copy or reference it when running `Main`:

```bash
cd src
java -cp "$CP" Main tests/1/input.txt tests/1/output.xml
```

## License

This project is licensed under the MIT License. See `LICENSE` for details.
