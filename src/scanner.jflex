import java_cup.runtime.*;

%%
%class scanner
%unicode
%cup
%line
%column

WhiteSpace = [ \t\r\n]+
comment = "#" [^\n]*


name = [a-zA-Z_][a-zA-Z0-9_]* 
number = \-?[0-9]+

ipv4 = {number}"."{number}"."{number}"."{number}
cidr = {ipv4}"/"{number}

%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline + 1, yycolumn + 1);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline + 1, yycolumn + 1, value);
  }
%}

%%

// Keywords 
"table"    { return symbol(sym.TABLE); }
"chain"    { return symbol(sym.CHAIN); }
"saddr"    { return symbol(sym.SADDR); }
"daddr"    { return symbol(sym.DADDR); }
"type"     { return symbol(sym.TYPE); }
"hook"     { return symbol(sym.HOOK); }
"priority" { return symbol(sym.PRIORITY); }
"policy"   { return symbol(sym.POLICY); }
"dport"    { return symbol(sym.DPORT); }

// Symbols
";"        { return symbol(sym.SEMICOLON); }
"{"        { return symbol(sym.LBRACE); }
"}"        { return symbol(sym.RBRACE); }


{cidr}     { return symbol(sym.IP_CIDR, yytext()); }
{ipv4}     { return symbol(sym.IP_RAW, yytext()); }
{number}   { return symbol(sym.NUMBER, Integer.valueOf(yytext())); }
{name}     { return symbol(sym.NAME, yytext()); }


{WhiteSpace} { /* ignore */ }
{comment}    { /* ignore */ }

[^] { 
    System.err.println("Lexical Error at Line " + (yyline+1) + ", Column " + (yycolumn+1) + 
                       ": Unrecognized character '" + yytext() + "'"); 
}