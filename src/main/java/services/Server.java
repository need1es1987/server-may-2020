package services;

import lombok.SneakyThrows;
import utils.MessageSource;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Observable {
    public static final int PORT = 0001;
    private final List<Observer> observerList = new ArrayList<>();

    @SneakyThrows
    public void start() {
        System.out.println(MessageSource.getMessage("server.start"));
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = serverSocket.accept();

            if (socket != null) {
                new Thread(new ClientRunnable(socket, this)).start();
            }
        }
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observerList) {
            observer.notifyMe(message);
        }
    }

    @Override
    public void deleteObserver(Observer obs) {
        observerList.remove(obs);
    }

    @Override
    public void addObserver(Observer obs) {
        observerList.add(obs);
    }

    @Override
    public void notifyObserversExceptObserver(String message,Observer exceptObserver) {
        for (Observer observer : observerList) {
            if (!observer.equals(exceptObserver)) {
                observer.notifyMe(message);
            }
        }
    }
}
