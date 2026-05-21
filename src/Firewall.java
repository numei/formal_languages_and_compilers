import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "firewall")
@XmlAccessorType(XmlAccessType.FIELD)
public class Firewall {
    @XmlAttribute(name = "defaultAction")
    private String defaultAction;

    @XmlElement(name = "elements")
    private FirewallElement[] elements;

    public Firewall() {
    }

    public Firewall(String defaultAction, FirewallElement[] elements) {
        this.defaultAction = defaultAction;
        this.elements = elements;
    }
}
