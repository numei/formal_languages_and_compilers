#!/usr/bin/env bash
set -e

cd "$(dirname "$0")/src"

jflex scanner.jflex
java java_cup.Main -expect 8 parser.cup
javac *.java
java Main input.txt output.xml
