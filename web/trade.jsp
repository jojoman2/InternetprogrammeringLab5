<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="databasestuff.MysqlConnection" %>
<%@ page import="databasestuff.DatabaseHandler" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
<meta charset="UTF-8">
<title>Trade 0.1 Beta</title>
</head>

<body>

<%=request.getParameter("message")%>

<h3>Addera ett v&auml;rdepapper</h3>
<form action="/TradeController">
<input type="hidden" name="action" value="addSecurity">
<input type="text" name="security" value=""><br>
<input type="submit" value="Utf&ouml;r">
</form>

<h3>L&auml;gg en k&ouml;p/s&auml;ljorder p&aring; ett v&auml;rdepapper</h3>
<form action="/TradeController">
<input type="hidden" name="action" value="addOrder">
V&auml;rdepapper:
<%
    DatabaseHandler db = new DatabaseHandler(MysqlConnection.getInstance());
    List<String> securities = null;
    try {
        securities = db.getSecurities();
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>

<select name="security">
    <% for(String security : securities){ %>
        <option value="<%=security%>"><%=security%></option>
    <%}%>
</select>
    <br>
K&ouml;p: <input type="radio" name="buyOrSell" value="Buy" checked>
S&auml;lj: <input type="radio" name="buyOrSell" value="Sell"><br>
Pris: <input type="number" name="price" value="" min="0.01" step="0.01"><br>
Antal: <input type="number" name="amount" value=""><br>
<input type="submit" value="Utf&ouml;r">
</form>

<h3>Visa avslutade aff&auml;rer i ett v&auml;rdepapper</h3>
<form action="/TradeController">
<input type="hidden" name="action" value="viewTrades">
V&auml;rdepapper:
<select name="security">
    <% for(String security : securities){ %>
        <option value="<%=security%>"><%=security%></option>
    <%}%>
</select><br>
<input type="submit" value="Utf&ouml;r">
</form>


</body>

</html>
