#!/usr/bin/env bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
M2_REPO="$HOME/.m2/repository"
JAXB_CP="$ROOT_DIR/lib/*:$M2_REPO/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:$M2_REPO/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:$M2_REPO/com/sun/xml/bind/jaxb-core/2.3.0/jaxb-core-2.3.0.jar:$M2_REPO/org/glassfish/jaxb/txw2/2.3.0/txw2-2.3.0.jar:$M2_REPO/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:$M2_REPO/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:$M2_REPO/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:$M2_REPO/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar"
CP=".:$CLASSPATH:$JAXB_CP"

cd "$SCRIPT_DIR"

jflex scanner.jflex
# java java_cup.MainDrawTree -dump_states -dump_grammar parser.cup
java -cp "$CP" java_cup.Main parser.cup
javac -cp "$CP" *.java
java -cp "$CP" Main input.txt output.xml
