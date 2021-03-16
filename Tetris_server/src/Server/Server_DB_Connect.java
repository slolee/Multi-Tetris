package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server_DB_Connect {
	public static Connection dbConn;
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String user = "tetrisDB";
			String passwd = "cks14579";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, passwd); //디비연동 완료.
			
			System.out.println("===== DB 연동 성공 =====");
		} catch (ClassNotFoundException cnfe) {
			System.err.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.err.println("DB 접속 실패 :" + sqle.toString());
		} catch (Exception e) {
			System.err.println("Unknown error!");
			e.printStackTrace();
		}
		return conn;
	}
}
