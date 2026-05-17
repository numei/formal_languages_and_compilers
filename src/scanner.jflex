import java_cup.runtime.*;


%%
%class scanner
%unicode
%cup
%line
%column

WhiteSpace = [ \t\r\n]+

name = [a-zA-Z_][a-zA-Z0-9_]*
singleIPv4 = (25[0-5])|(2[0-4][0-9])|([01]?[0-9][0-9]?)
CIDR = ("/"(3[0-2]|[12]?[0-9]))
IPv4_val = ({singleIPv4}"."){3}{singleIPv4}{CIDR} 
port_val     =  (6553[0-5]) | (655[0-2][0-9]) | (65[0-4][0-9]{2}) |( 6[0-4][0-9]{3}) |([1-5][0-9]{4}) | ([1-9][0-9]{0,3}) | 0 
priority_val = ("raw"|"mangle"|"filter"|"dstnat"|"dstnat"|"security"|"out") | (-300|-200|-150|-100|0|50|100|300)

hook_val = ("ingress"|"prerouting"|"input"|"output"|"postrouting"|"forward"|"egress")
type_val = ("filter"|"nat"|"route")
table_address_family = ("ip")
table_name = {name}
chain_name = {name}
policy_val = ("drop"|"accept") 
action_val = ("accept"|"drop"|"reject")

%{
  // Helper methods to generate symbols with line and column tracking
  private Symbol symbol(int type) {
    return new Symbol(type, yyline + 1, yycolumn + 1);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline + 1, yycolumn + 1, value);
  }
%}

%%
// Keywords
"table"   { return symbol(sym.TABLE); }
"chain"   { return symbol(sym.CHAIN); }
"saddr"   { return symbol(sym.SADDR); }
"daddr"   { return symbol(sym.DADDR); }
"type"    { return symbol(sym.TYPE); }
"hook"    { return symbol(sym.HOOK); }
"priority" { return symbol(sym.PRIORITY); }
"policy"  { return symbol(sym.POLICY); }
"dport"    { return symbol(sym.DPORT); }




// Symbols
";"       { return symbol(sym.SEMICOLON); }
"{"       { return symbol(sym.LBRACE); }
"}"       { return symbol(sym.RBRACE); }


// Values
{table_address_family} { return symbol(sym.ADDRESS_FAMILY, yytext()); }
{IPv4_val} { return symbol(sym.IP_VAL, yytext()); }
{port_val} { return symbol(sym.PORT_VAL, yytext()); }
{table_name} { return symbol(sym.TABLE_NAME, yytext()); }
{chain_name} { return symbol(sym.CHAIN_NAME, yytext()); }
{hook_val} { return symbol(sym.HOOK_VAL, yytext()); }
{type_val} { return symbol(sym.TYPE_VAL, yytext()); }
{policy_val} { return symbol(sym.POLICY_VAL, yytext()); }
{priority_val} { return symbol(sym.PRIORITY_VAL, yytext()); }
{action_val} { return symbol(sym.ACTION_VAL, yytext()); }

// Ignore whitespaces
{WhiteSpace} { /* ignore */ }

// Ignore comments 
{#[^\n]*} { /* ignore */ }

// Catch-all fallback: Report lexical errors for unrecognized characters
[^] { 
    System.err.println("Lexical Error at Line " + (yyline+1) + ", Column " + (yycolumn+1) + 
                       ": Unrecognized character '" + yytext() + "'"); 
}