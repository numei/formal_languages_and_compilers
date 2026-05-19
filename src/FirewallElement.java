
public class FirewallElement {

    private  String action;
    private  String source;
    private  String sourcePort;
    private  String destination;
    private  String destinationPort;
    private  String protocol;
    private  String id;
    
    
    public String getAction() { return action; }
    public String getSource() { return source; }
    public String getSourcePort() { return sourcePort; }
    public String getDestination() { return destination; }
    public String getDestinationPort() { return destinationPort; }
    public String getProtocol() { return protocol; }
    public String getId() { return id; }
    public void setAction(String action) { this.action = action; }
    public void setSource(String source) {this.source = source;}
    public void setDestination(String destination) {this.destination = destination;}
    public void setProtocol(String protocol) {this.protocol = protocol;}
    public void setSourcePort(String sourcePort) {this.sourcePort = sourcePort;}
    public void setDestinationPort(String destinationPort) {this.destinationPort = destinationPort;}
    public void setId(String id) {this.id = id;}



    public String toXML() {


        StringBuilder xml = new StringBuilder();
        xml.append("      <elements>\n");
        if(id != null){xml.append("          <id>").append(id).append("</id>\n");}
        if(action!=null){xml.append("          <action>").append(action).append("</action>\n");}
        xml.append("          <source>").append(source).append("</source>\n");
        xml.append("          <destination>").append(destination).append("</destination>\n");
        if(protocol != null){xml.append("          <protocol>").append(protocol).append("</protocol>\n");}
        if(sourcePort != null){xml.append("          <src_port>").append(sourcePort).append("</src_port>\n");}
        if(destinationPort != null){xml.append("          <dst_port>").append(destinationPort).append("</dst_port>\n");}
        xml.append("      </elements>\n");
        return xml.toString();
    }
}