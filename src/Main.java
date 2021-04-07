
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;
    public static ResultSet rezSet;



    public  static void main(String[] args){
 // Lesson3_6
        // Задание 2 урок 6
        int[]  initArry = new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7};
      //  int[]  initArry = new int[]{1, 2, 0, 0, 2, 3, 0, 1, 7};

        Integer[] readyArr = arrayToArry(initArry);
        for(int i = 0; i< readyArr.length; i++){
            System.out.print(readyArr[i] + " ");
        }



  //      connect();
  //       createTable("Test1");
  //      addRow("Test1", "('fio', 'phone') VALUES ('Petya', 125453)");
  //      readRow("Test1", 1);
  //      dellRow("Test1",1);
  //      dellTable("Test1");
  //      disconnect();
    }

    // Задание 2 урок 6
    public static Integer[] arrayToArry (int[] inArr) {
        System.out.println("Запустился");

        ArrayList<Integer> newArr = new ArrayList<Integer>();
        int j = 0;
        for( int i = inArr.length - 1; i >= 0; i--){
            if(inArr[i] == 4){
                j++;
            }
            // System.out.println(inArr[i]);
            if (inArr[i] != 4 && j == 0){
                newArr.add(inArr[i]);
            //    System.out.println(inArr[i]);
            }

            if(i == 0 && j == 0){
                throw new RuntimeException("В массиве нет цифры 4");
            }
        }
        Collections.reverse(newArr);
        Integer[] outArr = newArr.toArray(new Integer[newArr.size()]);
        return outArr;

    }

    public static boolean have4or1(int [] inArr) {
        int j4 = 0;
        int k1 = 0;
        for (int i = 0; i < inArr.length; i++){
            if (inArr[i] == 4) {
                j4++;
            }
            if (inArr[i] == 1){
                k1++;
            }
        }
        if (j4 == 0 || k1 == 0){
            return false;
        }else {
            return true;
        }

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
