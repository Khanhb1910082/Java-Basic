package bussiness;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;


public class executeFunction {

	static Connection conn = connection.createConnection();
	static CallableStatement cStmt;
	static PreparedStatement pStmt;
	static Scanner sc = new Scanner(System.in);
	static Scanner t = new Scanner(System.in);
	static String masp;
	static Locale localeVN = new Locale("vi", "VN");
    static NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
	public static void search(String masp) {
		try {
			pStmt = conn.prepareStatement("SELECT * FROM sanpham WHERE ma_sp=?");
			pStmt.setString(1, masp);
			ResultSet rs = pStmt.executeQuery();
			rs.next();
			System.out.println("--------------------------------");
			System.out.println("-----Sản phẩm-----");
			System.out.println("\nMã sản phẩm: " + rs.getString("ma_sp")
								+"\nTên sản phẩm: "+rs.getString("ten_sp")
								+"\nSố lượng: "+rs.getInt("so_luong")
								+"\nSize: "+rs.getString("size")
								+"\nMàu: "+rs.getString("mau")
								+"\nGiá: "+currencyVN.format(rs.getInt("gia")));
			System.out.println("--------------------------------");
		}catch (SQLException e) {
			System.out.println("\nNOTE: Không tìm thấy sản phẩm\n");
			System.out.println(e.getMessage());
		}
	}
	public static void add() {
		try {
			String[] arr = new String[6];
			String[] format = {"Nhập mã SP: ","Nhập tên SP: ","Nhập số lượng SP: "
								,"Nhập size: ","Nhập màu: ","Nhập giá sản phẩm: "};
			System.out.println("--------Thêm sản phẩm--------");
			for (int i=0;i<arr.length;i++) {
				System.out.print("\n"+format[i]);
				arr[i] = t.nextLine();
			}
			
			pStmt = conn.prepareStatement("insert into sanpham values (?,?,?,?,?,?);");
			for(int i=1 ; i<=arr.length;i++)
				pStmt.setString(i, arr[i-1]);
			pStmt.executeUpdate();
		
			System.out.println("\n-----Thêm thành công-----\n");
		} catch (SQLException e) {
			System.out.println("Lỗi hoặc cú pháp bị sai!!!");
			System.out.println(e.getMessage());
		}
	}
	public static void delete(String masp) {
//		PreparedStatement pStmt = null;
		try {
			executeFunction.search(masp);
			cStmt = conn.prepareCall("{call xoa_sp(?)}");
			cStmt.setString(1, masp);
			cStmt.executeQuery();
			System.out.println("\n-----Xóa thành công-----\n");
		} catch (SQLException e) {
			System.out.println("\nLỗi hoặc cú pháp bị sai!!!\n");
			System.out.println(e.getMessage());
		}
	}
	
	public static void edit(String masp) {
		try {
			//executeFunction.search(masp);
			String[] title = {"ten_sp","so_luong"
					,"size","mau","gia"};
			System.out.println("Thuộc tính của sản phẩm");
			for (int i=0;i<title.length;i++) {
				System.out.print((i+1)+". "+title[i]+" | ");
			}
			System.out.print("\nChọn thuộc tính muốn sửa(VD:1): ");
			int i = sc.nextInt();
			System.out.print("Sửa đổi thành: ");
			String replace = t.nextLine();
			String update = "update sanpham set "+title[i-1]+"='"+replace+"'"
							+ "where ma_sp='"+masp+"'";
			pStmt.executeUpdate(update);
			System.out.println("\n-----Sửa thành công-----\n");
		} catch (SQLException e) {
			System.out.println("\nLỗi hoặc cú pháp bị sai!!!\n");
			System.out.println(e.getMessage());
		}
	}
	public static void print() {
		try {
			pStmt = conn.prepareStatement("SELECT * FROM sanpham");
			ResultSet rs = pStmt.executeQuery();
			
			System.out.println("--------------------------------");
			System.out.println("-----Sản phẩm-----");
			while(rs.next()) {
				System.out.println("\nMã sản phẩm: " + rs.getString("ma_sp")
									+"\nTên sản phẩm: "+rs.getString("ten_sp")
									+"\nSố lượng: "+rs.getInt("so_luong")
									+"\nSize: "+rs.getString("size")
									+"\nMàu: "+rs.getString("mau")
									+"\nGiá: "+currencyVN.format(rs.getInt("gia"))
									+"\n-------------------------");
			}
			System.out.println("--------------------------------");
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void sale(int year) {
		try {
			cStmt = conn.prepareCall("{call sales(?)}");
			System.out.println("-------Sản phẩm không bán được------");				
			cStmt.setInt(1, year);
			ResultSet rs = cStmt.executeQuery();
			
			if(!rs.wasNull())
				while(rs.next()) {
					System.out.println("\nMã sản phẩm: "+rs.getString("ma_sp")
									+ "\nTên sản phẩm: "+rs.getString("ten_sp")
									+ "\nSố lượng: "+rs.getInt("so_luong"));
					System.out.println("--------------------------------");
			}
			else System.out.println("\nNOTES: Không có sản phẩm nào!!\n");
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public static void revenue(int year, int req) {
		try {
			if(req == 1) {
				cStmt = conn.prepareCall("{call DT_day(?)}");
				System.out.println("-------Doanh số theo ngày------");				
			}else {
				cStmt = conn.prepareCall("{call DT_month(?)}");
				System.out.println("-------Doanh số theo tháng------");				
			}
			cStmt.setInt(1, year);
			ResultSet rs = cStmt.executeQuery();
			
			while(rs.next()) {
				System.out.println("Date: "+rs.getString("ngay_lap")
								+ "\nDoanh số: "+currencyVN.format(rs.getInt("doanh_thu")));
				System.out.println("--------------------------------");
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

}
