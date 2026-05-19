jflex scanner.jflex
# java java_cup.MainDrawTree -dump_states -dump_grammar parser.cup
java java_cup.Main parser.cup
javac *.java
java Main input.txt output.xml
