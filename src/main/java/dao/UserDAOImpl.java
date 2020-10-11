package dao;

import model.User;
import utils.MessageSource;
import utils.Props;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    public static final String DB_URL = Props.getValue("db.url");
    public static final String DB_LOGIN = Props.getValue("db.login");
    public static final String DB_PASSWORD = Props.getValue("db.password");

    @Override
    public User FindByName(String name) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
             PreparedStatement preparedStatement =
                     connection.prepareStatement("select u.id, u.login, u.password from new_schema_spt_2020.users u where u.login = ?")) {

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                System.out.println(MessageSource.getMessage("findClient") + login + " " + password);
                return new User(login, password);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public User CreateNewUser(String name, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
             PreparedStatement preparedStatement =
                     connection.prepareStatement("insert into new_schema_spt_2020.users(login, password) values(?,?);")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();

            System.out.println(MessageSource.getMessage("addNewClient") + name + " " + password);
            return new User(name, password);


        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }


}

//1. Url, login и password к БД
//2. Драйвер в зависимостях
//3. JDBC (встроен внутрь JDK)
//4. Написать свой DAO