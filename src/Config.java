import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.FIELD)
public class Config {
    @XmlElement(name = "node")
    private Table[] tables;

    public Config() {
    }

    public Config(Table[] tables) {
        this.tables = tables;
    }

    public void writeXML(Writer writer) throws JAXBException {
        Marshaller marshaller = createMarshaller();
        if (tables != null && tables.length == 1) {
            marshaller.marshal(tables[0], writer);
        } else {
            marshaller.marshal(this, writer);
        }
    }

    public String toXML() {
        try {
            StringWriter writer = new StringWriter();
            writeXML(writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to generate XML with JAXB", e);
        }
    }

    private Marshaller createMarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Config.class, Table.class, Chain.class, Firewall.class, FirewallElement.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        return marshaller;
    }
}
