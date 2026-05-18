public class FirewallElement {
    private String action;
    private String source;
    private String destination;
    private String protocol;
    // TBD: support srcPort and destPort
    private String srcPort;
    private String destPort;

    //TBD: support default values for optional fields, e.g., protocol and destPort
    public FirewallElement(String action, String src, String dst,String proto, String dPort) {
        this.action = action;
        this.source = src;
        this.destination = dst;
        this.protocol = proto;
        this.destPort = dPort;
    }

    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("    <elements>\n");
        xml.append("        <action>").append(action).append("</action>\n");
        xml.append("        <source>").append(source).append("</source>\n");
        xml.append("        <destination>").append(destination).append("</destination>\n");
        xml.append("        <protocol>").append(protocol).append("</protocol>\n");
        xml.append("        <dest_port>").append(destPort).append("</dest_port>\n");
        xml.append("    </elements>\n");
        return xml.toString();
    }
}