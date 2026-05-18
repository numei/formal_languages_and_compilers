public class Chain {
    String type;
    String hook;
    String priority;
    String policy;
    String name;
    FirewallElement[] rules;
    public Chain(String name, String type, String hook, String priority, String policy, FirewallElement[] rules) {
        this.name = name;
        this.type = type;
        this.hook = hook;
        this.priority = priority;
        this.policy = policy;
        this.rules = rules;
    }
    public String toXML() {
        StringBuilder xml = new StringBuilder();    
        xml.append("    <firewall \n").append(" defaultAction=\"").append(policy).append("\">\n");
        if(rules != null){
            for(FirewallElement rule : rules) {
                xml.append(rule.toXML());
            }    
        }
        xml.append("    </firewall>\n");
        return xml.toString();
    }
}
