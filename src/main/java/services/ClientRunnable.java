package services;

import dao.UserDAO;
import dao.UserDAOImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.User;
import org.apache.log4j.Logger;
import utils.MessageSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class ClientRunnable implements Runnable, Observer {
    private final Socket socket;
    private User user;
    private UserDAO userDAO = new UserDAOImpl();
    private final Server server;

    private static final Logger log = Logger.getLogger(ClientRunnable.class);

    @SneakyThrows
    @Override
    public void run() {

        System.out.println(MessageSource.getMessage("clientConnected"));
        log.info("Вывели надпись подключение клиента");

        BufferedReader readerFromClient =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String clientInput;
        while ((clientInput = readerFromClient.readLine()) != null) {
            if (clientInput.startsWith("!@#$auto")) {
                final String[] authorizationStrings = clientInput.substring(8).split(":");
                user = authorization(authorizationStrings[0], authorizationStrings[1]);
                if (user != null) {
                    server.addObserver(this);
                    log.info("Авторизация пользователя");
                } else {
                    notifyMe(MessageSource.getMessage("notWriteNameOfClient"));
                    log.error("Ошибка в имени или пароле");
                    notifyMe("401");
                    log.error("Ошибка 401");
                    socket.close();
                    log.info("Закрытие соккета");
                    break;
                }
            } else if (clientInput.startsWith("!@#$reg")) {
                final String[] registrationStrings = clientInput.substring(7).split(":");
                user = registration(registrationStrings[0], registrationStrings[1]);
                if (user != null) {
                    server.addObserver(this);
                    log.info("Регистрация пользователя");
                }
            } else {
                System.out.println(user.getLogin() + ":" + clientInput);
                log.info("Сообщение от пользователя: " + user.getLogin());
                writeUsingFiles(clientInput);
                server.notifyObserversExceptObserver(user.getLogin() + ":" + clientInput, this);
            }
        }
    }

    public User authorization(String name, String password) {
        User user = userDAO.FindByName(name);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User registration(String name, String password) {
        User user = userDAO.CreateNewUser(name, password);
        return user;
    }

    @SneakyThrows
    @Override
    public void notifyMe(String message) {
        PrintWriter printWriterToClient = new PrintWriter(socket.getOutputStream());
        printWriterToClient.println(message);
        printWriterToClient.flush();
    }

    private static void writeUsingFiles(String data) {
        try {
            Files.write(Paths.get("C:\\Program Files\\ideaProjects\\IdeaProjects\\server-may-2020\\src\\main\\resources\\chat_conversations.txt"), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
