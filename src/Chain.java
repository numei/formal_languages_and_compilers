import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Chain {
    @XmlAttribute(name = "name")
    private String configurationName = "test";

    @XmlElement(name = "firewall")
    private Firewall firewall;

    @XmlTransient
    private String type;

    @XmlTransient
    private String hook;

    @XmlTransient
    private Integer priority;

    @XmlTransient
    private String policy;

    @XmlTransient
    private String name;

    public Chain() {
    }

    public Chain(String name, String type, String hook, Integer priority, String policy, FirewallElement[] rules) {
        this.name = name;
        this.type = type;
        this.hook = hook;
        this.priority = priority;
        this.policy = policy;
        this.firewall = new Firewall(policy, rules);
    }
}
