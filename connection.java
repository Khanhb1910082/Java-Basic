package bussiness;

import java.sql.Connection;
import java.sql.DriverManager;

public class connection {
	public static Connection createConnection() {
		Connection conn = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost/QL_ban_hang?" + "user=root");
//			System.out.println("Noi ket thanh cong");
		} catch (Exception ex) {
//			System.out.println("Noi ket khong thanh cong");
			ex.printStackTrace();
		}
		return conn;
	}
}