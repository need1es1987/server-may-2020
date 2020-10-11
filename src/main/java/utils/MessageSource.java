package utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageSource {
    private final static ResourceBundle resourceBundle = ResourceBundle.
            getBundle("bundle", new Locale(Props.getValue("language"), Props.getValue("country")));

    public static String getMessage(String key) {
        return resourceBundle.getString(key);
    }
}
