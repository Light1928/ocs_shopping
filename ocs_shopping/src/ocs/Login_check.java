package ocs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class Login_check
 */

@WebServlet("/Login_check")
public class Login_check extends HttpServlet {
	private  final String URL = "jdbc:mysql://" + HOST_NAME + "/" + DB_NAME + "?serverTimezone=JST";
	private static final String DB_NAME   = "webapp2019_OCSshop";
	//家用
	private  static final String HOST_NAME = "localhost:3306";
	public  static final String  USER_NAME = "root";
	public  static final String USER_PASS = "";
	//学校用
//	private static final String HOST_NAME = "10.15.121.37:3306";
//	private static final String USER_NAME = "user_OCSshop";
//	private static final String USER_PASS = "OCSshop";


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("userid");
		String password = request.getParameter("password");
		System.out.println("ID \t\t:[" + id + "]");
		System.out.println("PASSWORD \t:[" + password + "]");
		String pagePath = "ng.jsp";
		// check the user account.
		if (user(id, password)) {
			HttpSession session = request.getSession(true);
			System.out.println(session.getId());
			session.setAttribute("userid", id);
			pagePath = "main_menu_sample.jsp";
		}
		//フォワードでの遷移
//		ServletContext context = request.getServletContext();
//		RequestDispatcher dispatch = request.getRequestDispatcher(pagePath);
//		dispatch.forward(request, response);

		response.sendRedirect(pagePath);
	}
	protected boolean user(String id, String password) {
		// nullのとき

		if (id == null) {
			System.out.println("id is null value.");
			return false;
		}
		// lengthが0のとき
		if (id.length() == 0) {
			System.out.println("id is empty.");
			return false;
		}
		if(password == null) {
			System.out.println("password is null value.");
			return false;
		}
		if (password.length() == 0) {
			System.out.println("password is empty.");
			return false;
		}

		try {
			String sql = "select * from  CUSTOMER  where User_ID = ? and Password = ?";
			//MySQL用
			//Class.forName("com.mysql.cj.jdbc.Driver");
			//学校用
			Class.forName("org.mariadb.jdbc.Driver");
			//家用
			//Connection con = DriverManager.getConnection(URL,"root","");
			//学校用
			Connection con = DriverManager.getConnection(URL,USER_NAME, USER_PASS);
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, id);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				System.out.println("exist ID/PASSWORD pattern.");
				return true;
			} else {
				System.out.println("doesn't exist ID/PASSWORD pattern.");
				return false;
			}
		} catch (Exception ex) {
			String msg = "ドライバのロードに失敗しました";
			System.out.println(ex);
			return false;
		}
	}
}