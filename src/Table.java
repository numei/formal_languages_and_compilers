public class Table {
    String name;
    Chain[] chains;
    public Table(String name, Chain[] chains) {
        this.name = name;
        this.chains = chains;
    }
    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<node function_type=\"FIREWALL\" name=\"").append(name).append("\">\n");
        xml.append("  <configuration name=\"test\">\n");
        if(chains != null) for(Chain chain : chains)  xml.append(chain.toXML());
        xml.append("  </configuration>\n");
        xml.append("</node>\n");
        return xml.toString();
    }
}
