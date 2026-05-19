@echo off
setlocal

cd /d "%~dp0src"

jflex scanner.jflex
if errorlevel 1 exit /b %errorlevel%

java java_cup.Main parser.cup
if errorlevel 1 exit /b %errorlevel%

javac *.java
if errorlevel 1 exit /b %errorlevel%

java Main input.txt output.xml
exit /b %errorlevel%
