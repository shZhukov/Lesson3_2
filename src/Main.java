
import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;
    public static ResultSet rezSet;

    public  static void main(String[] args){
        connect();

  //       createTable("Test1");
  //      addRow("Test1", "('fio', 'phone') VALUES ('Petya', 125453)");
  //      readRow("Test1", 1);
  //      dellRow("Test1",1);
        dellTable("Test1");
        disconnect();
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:testDb.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createTable(String name) {
        try {
            stmt.execute("CREATE TABLE if not exists " + name + " ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'fio' text, 'phone' INT);");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public  static void addRow(String tableName, String row){
        try {
            stmt.execute("INSERT INTO " + tableName + " " + row + "; ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void readRow(String tableName, double id){
        try {
            rezSet = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE Id=" + id);
            while (rezSet.next())
            {
                System.out.println(rezSet.getInt("Id"));
                System.out.println(rezSet.getString("fio"));
                System.out.println(rezSet.getDouble("phone"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void dellRow(String tableName, double id){
        try {
            stmt.execute("DELETE FROM " + tableName + " WHERE Id=" + id + "; ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void dellTable(String tableName){
        try {
            stmt.execute("DROP TABLE " + tableName + "; ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
