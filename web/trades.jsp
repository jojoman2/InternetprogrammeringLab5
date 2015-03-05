<%--
  Created by IntelliJ IDEA.
  User: Fredrik
  Date: 2015-03-02
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="bean.Trade" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
  <meta charset="UTF-8">
  <title>Trade 0.1 Beta</title>
</head>
<body>
  <%
    List<Trade> list=(List<Trade>)request.getAttribute("array");
  %>
  <table>
    <thead>
      <td>Name</td>
      <td>Price</td>
      <td>Amount</td>
      <td>Date</td>
      <td>Buyer</td>
      <td>Seller</td>
    </thead>
    <tbody>
      <%
        for(Trade thisTrade : list){

      %>
        <tr>
          <td><%=thisTrade.getName()%></td>
          <td><%=thisTrade.getPrice()%></td>
          <td><%=thisTrade.getAmount()%></td>
          <td><%=thisTrade.getDate()%></td>
          <td><%=thisTrade.getBuyer()%></td>
          <td><%=thisTrade.getSeller()%></td>
        </tr>
      <%
          }
      %>
    </tbody>
  </table>
</body>
</html>
