import java.util.HashMap;

public class Validator {
    static HashMap<String,String> actionMap = new HashMap<>();

    public Validator() {
        actionMap.put("accept","ALLOW");
        actionMap.put("drop","DENY");
    }
    

    public static  enum PriorityEnum {
        NF_IP_PRI_CONNTRACK_DEFRAG(-400),
        NF_IP_PRI_RAW(-300),
        NF_IP_PRI_SELINUX_FIRST(-225),
        NF_IP_PRI_CONNTRACK(-200),
        NF_IP_PRI_MANGLE(-150),
        NF_IP_PRI_NAT_DST(-100),
        NF_IP_PRI_FILTER(0),
        NF_IP_PRI_SECURITY(50),
        NF_IP_PRI_NAT_SRC(100),
        NF_IP_PRI_SELINUX_LAST(225),
        NF_IP_PRI_CONNTRACK_HELPER(300);

        private final int value;

        PriorityEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static boolean fromValue(int value) {
            for (PriorityEnum priority : PriorityEnum.values()) {
                if (priority.getValue() == value) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isValidPriority(int priority){
        return PriorityEnum.fromValue(priority);
    }


    public static boolean isValidPort(int port) {
        try {
            return port >= 0 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidIPv4Prefix(String prefix) {
        try {
            int num = Integer.parseInt(prefix);
            return num >= 0 && num <= 32;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidIpv4CIDR(String cidr) {
        String[] parts = cidr.split("/");
        if (parts.length != 2) return false;
        String ip = parts[0];
        String prefix = parts[1];
        return isValidIPv4Addres(ip) && isValidIPv4Prefix(prefix);
    }
    public static boolean isValidIPv4Addres(String ip) {
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        for (String part : parts) {
            try {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidIPv4Range(String range) {
        String[] parts = range.split("-");
        return parts.length == 2 && isValidIPv4Range(parts[0], parts[1]);
    }

    public static boolean isValidIPv4Range(String start, String end) {
        if (!isValidIPv4Addres(start) || !isValidIPv4Addres(end)) return false;
        return toIPv4Long(start) <= toIPv4Long(end);
    }

    private static long toIPv4Long(String ip) {
        String[] parts = ip.split("\\.");
        long result = 0;
        for (String part : parts) {
            result = (result << 8) + Integer.parseInt(part);
        }
        return result;
    }
}
