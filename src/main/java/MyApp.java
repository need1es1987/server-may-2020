import lombok.SneakyThrows;
import services.Server;

import java.util.Locale;
import java.util.ResourceBundle;

public class MyApp {

    @SneakyThrows
    public static void main(String[] args) {
        new Server().start();

//        Properties props = new Properties();
//        props.load(MyApp.class.getResourceAsStream("application.properties"));
//        System.out.println("props.getProperty(\"db.url\") = " + Utils.getValue("db.url"));

//        UserDAO dao = new UserDAOImpl();
//
//        System.out.println("dao.FindByName(\"nastya\") = " + dao.FindByName("nastya"));

//        Locale locale = new Locale("ru","RU");
////        System.out.println(locale.getDisplayCountry());
//        System.out.println(locale.getDisplayName());
//
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle",locale);
//        System.out.println("resourceBundle.getString(\"hello\") = " + resourceBundle.getString("hello"));


    }


}
