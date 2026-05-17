public class FirewallElement {
    private String action;
    private String source;
    private String destination;
    private String protocol;
    private String destPort;

    public FirewallElement(String action, String src, String dst, String proto, String dPort) {
        this.action = action;
        this.source = src;
        this.destination = dst;
        this.protocol = proto;
        this.destPort = dPort;
    }

    // Core: Manual XML generation logic (No external dependencies)
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