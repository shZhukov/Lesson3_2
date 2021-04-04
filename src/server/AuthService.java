package server;

import java.sql.SQLException;

public interface AuthService {

    /**
     * @return nickname если пользователь есть
     * @return null если пользователя нет
     */
    String getNickByLoginAndPassword(String login, String password) throws ClassNotFoundException, SQLException;

    boolean registration(String login, String password, String nickname);
}
