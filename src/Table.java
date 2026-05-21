import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "node")
@XmlAccessorType(XmlAccessType.FIELD)
public class Table {
    @XmlAttribute(name = "functional_type")
    private String functionalType = "FIREWALL";

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "configuration")
    private Chain[] chains;

    public Table() {
    }

    public Table(String name, Chain[] chains) {
        this.name = name;
        this.chains = chains;
    }
}
