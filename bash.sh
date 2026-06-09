#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
SRC_DIR="$ROOT_DIR/src"
XSD_FILE="$ROOT_DIR/xsd/verigraph.xsd"
M2_REPO="$HOME/.m2/repository"
JAXB_CP="$ROOT_DIR/lib/*:$M2_REPO/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:$M2_REPO/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:$M2_REPO/com/sun/xml/bind/jaxb-core/2.3.0/jaxb-core-2.3.0.jar:$M2_REPO/org/glassfish/jaxb/txw2/2.3.0/txw2-2.3.0.jar:$M2_REPO/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:$M2_REPO/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:$M2_REPO/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:$M2_REPO/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar"
CP=".:${CLASSPATH:-}:$JAXB_CP"

if ! command -v xmllint >/dev/null 2>&1; then
    echo "Error: xmllint is required to validate generated XML against $XSD_FILE" >&2
    exit 1
fi

cd "$SRC_DIR"

jflex scanner.jflex
java -cp "$CP" java_cup.Main parser.cup
javac -cp "$CP" *.java

if [[ "${1:-}" == "--single" ]]; then
    java -cp "$CP" Main input.txt output.xml
    exit $?
fi

total=0
success=0

run_test() {
    local test_name="$1"
    local input_file="$2"
    local output_file
    output_file="$(dirname "$input_file")/output.xml"

    total=$((total + 1))
    echo "Running test $test_name..."
    rm -f "$output_file"
    if [[ -f "$input_file" ]] &&
        java -cp "$CP" Main "$input_file" "$output_file" &&
        xmllint --noout --schema "$XSD_FILE" "$output_file"; then
        success=$((success + 1))
        echo "[PASS] test $test_name"
    else
        echo "[FAIL] test $test_name"
    fi
}

for test_number in {1..10}; do
    run_test "$test_number" "tests/$test_number/input.txt"
done

for recovery_input in tests/recovery/*/input.txt; do
    recovery_name="recovery/$(basename "$(dirname "$recovery_input")")"
    run_test "$recovery_name" "$recovery_input"
done

echo "Tests passed: $success/$total"

if [[ "$success" -ne "$total" ]]; then
    exit 1
fi
