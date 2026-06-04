import java.io.*;



public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage Error!");
            System.err.println("Please provide input and output file paths.");
            System.err.println("Example: java Main input.txt output.xml");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            System.err.println("Error: Cannot find input file -> " + inputFilePath);
            System.exit(1);
        }

        System.out.println("Starting to parse file: " + inputFilePath + " ...");


        try (Reader reader = new FileReader(inputFilePath);
             Writer writer = new FileWriter(outputFilePath)) {

            scanner lexer = new scanner(reader);
            parser p = new parser(lexer);
            
            Config result = (Config)(p.parse().value);
            int syntaxErrors = p.getSyntaxErrorCount();
            
            if (result != null ) { 
                result.writeXML(writer);
                if (syntaxErrors > 0) {
                    System.err.println("[WARNING] Parsing recovered from " + syntaxErrors + " syntax error(s).");
                }
                System.out.println("[SUCCESS] Parsing complete. XML saved to: " + outputFilePath);
            } else {
                 System.err.println("[WARNING] Parsing finished, but no valid AST was generated.");
                 System.exit(1);
            }

        } catch (Exception e) {
            System.err.println("[ERROR] An exception occurred during execution: " + e.getMessage());
            System.exit(1);
        }
    }
}
