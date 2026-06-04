import javax.xml.bind.annotation.*;

@XmlRootElement(name = "elements")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "action", "source", "destination", "protocol", "sourcePort", "destinationPort", "priority", "directional"})
public class FirewallElement {

    private String id;
    private String action;
    private String source;
    private String destination;
    private String protocol;

    @XmlElement(name = "src_port")
    private String sourcePort;

    @XmlElement(name = "dst_port")
    private String destinationPort;

    private String priority;

    private Boolean directional;
    
    
    public String getAction() { return action; }
    public String getSource() { return source; }
    public String getSourcePort() { return sourcePort; }
    public String getDestination() { return destination; }
    public String getDestinationPort() { return destinationPort; }
    public String getProtocol() { return protocol; }
    public String getId() { return id; }
    public String getPriority() { return priority; }
    public Boolean getDirectional() { return directional; }
    public void setAction(String action) { this.action = action; }
    public void setSource(String source) {this.source = source;}
    public void setDestination(String destination) {this.destination = destination;}
    public void setProtocol(String protocol) {this.protocol = protocol;}
    public void setSourcePort(String sourcePort) {this.sourcePort = sourcePort;}
    public void setDestinationPort(String destinationPort) {this.destinationPort = destinationPort;}
    public void setId(String id) {this.id = id;}
    public void setPriority(String priority) {this.priority = priority;}
    public void setDirectional(Boolean directional) {this.directional = directional;}
}
