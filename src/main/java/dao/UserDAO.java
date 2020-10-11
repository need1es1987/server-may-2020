package dao;

import model.User;

public interface UserDAO {

    User FindByName (String name);

    User CreateNewUser (String name, String password);

//    void updateUser (User user);
}
