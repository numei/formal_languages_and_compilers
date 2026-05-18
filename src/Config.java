public class Config {
    Table[] tables;
    public Config(Table[] tables) {
        this.tables = tables;
    }
    public String toXML() {
        StringBuilder xml = new StringBuilder();
        if(tables != null) for(Table table : tables) xml.append(table.toXML());;
        return xml.toString();
    }
}
