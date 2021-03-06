package ocs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//CART TABLEにUser_ID、Goods_ID,Quantityを登録


@WebServlet("/CartAdd")
public class CartAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String URL = "jdbc:mysql://" + HOST_NAME + "/" + DB_NAME + "?serverTimezone=JST";
	private static final String DB_NAME   = "webapp2019_OCSshop";

	//家用
//	private static final String HOST_NAME = "localhost:3306";
//	private static final String  USER_NAME = "root";
//	private static final String USER_PASS = "";

	//学校用
			public static final String HOST_NAME = "10.15.121.37:3306";
			public static final String USER_NAME = "user_OCSshop";
			public static final String USER_PASS = "OCSshop";


	CartInfoBean cartInfoBean = new CartInfoBean();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goods_name = null;
		HttpSession session = request.getSession();
		goods_name= (String)session.getAttribute("goods_name");
		//ゲットできた
		System.out.println(goods_name);

		String user_id = (String)session.getAttribute("userid");
		System.out.println(user_id);
		int goods_id = (int) session.getAttribute("goods_id");
		System.out.println(goods_id);
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		System.out.println(quantity);

		try{
			String sql = "INSERT INTO CART VALUES('"+user_id+"',"+goods_id+","+quantity+")";

			//MySQL用
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("org.mariadb.jdbc.Driver");
			//学校用

			Connection con = DriverManager.getConnection(URL,USER_NAME, USER_PASS);

			PreparedStatement stmt = con.prepareStatement(sql);
			//件数取得
			int count = stmt.executeUpdate();
			if(count == 1) {
				System.out.println("成功");
				CartRecordBean cartRecordBean = new CartRecordBean();
				cartRecordBean.setGoodsname(goods_name);
				cartRecordBean.setQuantity(quantity);
				cartInfoBean.addCartlist(cartRecordBean);
				request.setAttribute("count",count);
				ServletContext context = request.getServletContext();
				RequestDispatcher rd = context.getRequestDispatcher("/home.jsp");
				rd.forward(request, response);
			}
		}catch(Exception ex) {
			System.out.println("失敗");
			ex.printStackTrace();
		}
	}

}
