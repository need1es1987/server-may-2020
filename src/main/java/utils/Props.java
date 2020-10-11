package utils;

import java.io.IOException;
import java.util.Properties;

public class Props {

    public static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(Props.class.getResourceAsStream("/application.properties"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return PROPERTIES.getProperty(key);
    }

}
