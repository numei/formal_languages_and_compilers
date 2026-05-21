@echo off
setlocal

set "ROOT_DIR=%~dp0"
set "SRC_DIR=%ROOT_DIR%src"
set "M2_REPO=%USERPROFILE%\.m2\repository"
set "JAXB_CP=%ROOT_DIR%lib\*;%M2_REPO%\javax\xml\bind\jaxb-api\2.3.1\jaxb-api-2.3.1.jar;%M2_REPO%\org\glassfish\jaxb\jaxb-runtime\2.3.1\jaxb-runtime-2.3.1.jar;%M2_REPO%\com\sun\xml\bind\jaxb-core\2.3.0\jaxb-core-2.3.0.jar;%M2_REPO%\org\glassfish\jaxb\txw2\2.3.0\txw2-2.3.0.jar;%M2_REPO%\com\sun\istack\istack-commons-runtime\3.0.7\istack-commons-runtime-3.0.7.jar;%M2_REPO%\org\jvnet\staxex\stax-ex\1.8\stax-ex-1.8.jar;%M2_REPO%\com\sun\xml\fastinfoset\FastInfoset\1.2.15\FastInfoset-1.2.15.jar;%M2_REPO%\javax\activation\javax.activation-api\1.2.0\javax.activation-api-1.2.0.jar"
set "CP=.;%CLASSPATH%;%JAXB_CP%"

cd /d "%SRC_DIR%"

jflex scanner.jflex
if errorlevel 1 exit /b %errorlevel%

java -cp "%CP%" java_cup.Main parser.cup
if errorlevel 1 exit /b %errorlevel%

javac -cp "%CP%" *.java
if errorlevel 1 exit /b %errorlevel%

java -cp "%CP%" Main input.txt output.xml
exit /b %errorlevel%
