package databasestuff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class MysqlConnection {

    private MysqlConnection(){}

    public static Connection getInstance(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/stockdata","root","");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
