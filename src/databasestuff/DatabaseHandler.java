package databasestuff;

import bean.Order;
import bean.Trade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 2015-03-02.
 */
public class DatabaseHandler {

    private Connection conn;

    public DatabaseHandler(Connection conn){
        this.conn=conn;
    }

    public void addOrder(String name, String type, double price, int amount, String userID) throws SQLException {
        String query =
                "INSERT INTO orders (name,type,price,amount,uid)" +
                " VALUES(?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,name);
        statement.setString(2,type);
        statement.setDouble(3,price);
        statement.setInt(4,amount);
        statement.setString(5,userID);
        statement.executeUpdate();
    }

    public void addSecurity(String name) throws SQLException {
        String query =
                "INSERT INTO securities(name)" +
                " VALUES(?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,name);
        statement.executeUpdate();
    }

    public ArrayList<Trade> getTrades(String stockName) throws SQLException {
        String query =
                "SELECT name,price,amount,dt,buyer,seller" +
                " FROM trades" +
                " WHERE name=?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,stockName);
        ResultSet result = statement.executeQuery();

        ArrayList<Trade> trades = new ArrayList<Trade>();
        while(result.next()){
            Trade trade = new Trade();
            trade.setName(result.getString("name"));
            trade.setPrice(result.getDouble("price"));
            trade.setAmount(result.getInt("amount"));
            trade.setDate(result.getDate("dt"));
            trade.setBuyer(result.getString("buyer"));
            trade.setSeller(result.getString("seller"));
            trades.add(trade);
        }
        return trades;
    }

    public ArrayList<Order> getOrders(String name, String type, double price) throws SQLException {
        String query =
                "SELECT id,name,type,price,amount,uid" +
                        " FROM orders" +
                        " WHERE name=? AND type=? AND price=?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, name);
        statement.setString(2, type);
        statement.setDouble(3, price);
        ResultSet result = statement.executeQuery();

        ArrayList<Order> orders = new ArrayList<Order>();
        while (result.next()) {
            Order order = new Order();
            order.setId(result.getInt("id"));
            order.setName(result.getString("name"));
            order.setType(result.getString("type"));
            order.setPrice(result.getDouble("price"));
            order.setAmount(result.getInt("amount"));
            order.setUserID(result.getString("uid"));
            orders.add(order);
        }
        return orders;
    }

    public void addTrade(String name, double price, int amount, String buyer, String seller) throws SQLException {
        String query =
                "INSERT INTO trades (name,price,amount,buyer,seller)" +
                " VALUES(?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,name);
        statement.setDouble(2, price);
        statement.setInt(3, amount);
        statement.setString(4, buyer);
        statement.setString(5,seller);
        statement.executeUpdate();
    }

    public void updateOrder(int id, int newAmount) throws SQLException {
        PreparedStatement statement;
        if(newAmount==0){
            String query =
                "DELETE FROM orders " +
                "WHERE id=?";
            statement = conn.prepareStatement(query);
            statement.setInt(1,id);
        }

        else {
            String query =
                "UPDATE orders " +
                "SET amount=? " +
                "WHERE id=?";
            statement = conn.prepareStatement(query);
            statement.setInt(1,newAmount);
            statement.setInt(2,id);
        }
        statement.executeUpdate();
    }

    public List<String> getSecurities() throws SQLException {
        String query =
                "SELECT name" +
                " FROM securities";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);

        List<String> securities = new ArrayList<String>();

        while (result.next()) {
            securities.add(result.getString("name"));
        }
        return securities;

    }
}
