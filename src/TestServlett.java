import bean.Trade;
import databasestuff.DatabaseHandler;
import databasestuff.MysqlConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestServlett extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void printArrayList(List<Trade> list, PrintWriter writer){
        for (Trade aList : list) {
            writer.print(aList.getName());
        }
        writer.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        Connection conn = MysqlConnection.getInstance();
        DatabaseHandler handler = new DatabaseHandler(conn);
        try {
            handler.addOrder("sdfsd","sdf",234.2342,234,"dgdfg");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
