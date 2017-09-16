package util;

import java.util.UUID;

public class UuidUtil {
    public static String getUuid(){
        UUID uuid = UUID.randomUUID();
        String uuidStr =  uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }

    public static void main(String[] args) {
        System.out.println(UuidUtil.getUuid());
    }

}
