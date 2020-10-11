package services;

public interface Observable {
    void notifyObservers (String message);
    void deleteObserver (Observer obs);
    void addObserver (Observer obs);
    void notifyObserversExceptObserver (String message,Observer obs);
}
