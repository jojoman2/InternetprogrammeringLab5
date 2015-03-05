import bean.Order;
import databasestuff.DatabaseHandler;
import databasestuff.MysqlConnection;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class TradeController extends HttpServlet{

	private static final int USER_ID_LENGTH = 8;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		if(session.getAttribute("userId")==null){
			session.setAttribute("userId",randomString(USER_ID_LENGTH));
		}

		Connection conn = MysqlConnection.getInstance();
		DatabaseHandler db = new DatabaseHandler(conn);
	
		String message = "";
		String action = request.getParameter("action");
		if (action != null) {
			if(action.equals("addSecurity")){
				try {
                    String securityToAdd = request.getParameter("security");
                    securityToAdd = StringEscapeUtils.escapeHtml4(securityToAdd);
                    db.addSecurity(securityToAdd);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if(action.equals("addOrder")){
				try {
					String security =request.getParameter("security");
					String buyOrSell = request.getParameter("buyOrSell");
					boolean buying = buyOrSell.equals("Buy");
                    int amount = Integer.parseInt(request.getParameter("amount")); //20
					double price = Double.parseDouble(request.getParameter("price"));

                    String thisUserId=(String)session.getAttribute("userId");


					List<Order> orders = db.getOrders(security, buying ? "sell" : "buy", price); //6 15

                    int oppositeActionTotal = 0;
					for(Order order : orders){
						oppositeActionTotal += order.getAmount(); //21
					}
                    int toRemove = Math.min(amount,oppositeActionTotal); //20
                    if(amount-toRemove>0){ //false
                        db.addOrder(security,buyOrSell,price,amount-toRemove,thisUserId);
                    }

                    int leftToRemove = toRemove; //14
                    for(Order order : orders){
                        int orderAmount = order.getAmount(); //15
                        int toRemoveFromThis = Math.min(leftToRemove,orderAmount); //14
                        leftToRemove -= toRemoveFromThis; //0
                        db.updateOrder(order.getId(), orderAmount - toRemoveFromThis); //15-14=1

                        String buyer = buying ? thisUserId : order.getUserID();
                        String seller = buying ? order.getUserID() : thisUserId;
                        db.addTrade(security,price,toRemoveFromThis,buyer,seller);
                        if(leftToRemove<=0){
                            break;
                        }
                    }

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if(action.equals("viewTrades")){
				try {
					request.setAttribute("array",db.getTrades(request.getParameter("security")));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// Kod för att lägga en köp eller säljorder
				message = "viewTrades";
			}
		}
		try{
			RequestDispatcher rd;
			if(action.equals("viewTrades")){
				rd = request.getRequestDispatcher("trades.jsp");
			}
			else {
				rd =request.getRequestDispatcher("trade.jsp?message=" + message);
			}
			rd.forward(request, response);
		}
		catch(ServletException e){
			System.out.print(e.getMessage());
		}
		catch(IOException e){
			System.out.print(e.getMessage());
		}
    }

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();

	private static String randomString(int len)
	{
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ )
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}
} 