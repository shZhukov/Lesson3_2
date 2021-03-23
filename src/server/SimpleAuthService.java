package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService{
    private Connection connection;
    private Statement stmt;
    private  PreparedStatement pstmt;
    private  ResultSet rezSet;


    private class UserData {
        String login;
        String password;
        String nickName;

        public UserData(String login, String password, String nickName) {
            this.login = login;
            this.password = password;
            this.nickName = nickName;
        }
    }

    List<UserData> users;

    public SimpleAuthService() {
        users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new UserData("login" + i, "pass" + i, "nick" + i));
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:testDb.db");
            stmt = connection.createStatement();
            rezSet = stmt.executeQuery("SELECT * FROM User");
            while (rezSet.next())
            {
                users.add(new UserData(rezSet.getString("Login"), rezSet.getString("Pwd"), rezSet.getString("UserName")));

            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            // return false;
            e.printStackTrace();
        }
/*        users.add(new UserData("qwe", "qwe", "qwe"));
        users.add(new UserData("asd", "asd", "asd"));
        users.add(new UserData("zxc", "zxc", "zxc")); */


    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) throws ClassNotFoundException, SQLException {
        for(UserData user : users){
            if(user.login.equals(login) && user.password.equals(password)){
                return user.nickName;
            }
        }

//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection("jdbc:sqlite:testDb.db");
//            stmt = connection.createStatement();
//            rezSet = stmt.executeQuery("SELECT * FROM User WHERE Login =" + login + "AND Pwd =" + password);
//            if (rezSet.getString("UserName") != null ){
//                return rezSet.getString("UserName");
//            }



        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        for (UserData user : users) {
            if(user.login.equals(login) || user.nickName.equals(nickname)){
                return false;
            }
        }
        users.add(new UserData(login, password, nickname));
        return true;
    }
}
