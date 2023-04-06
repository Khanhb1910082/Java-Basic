package bussiness;

import java.util.Scanner;

public class UInterface {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Scanner t = new Scanner(System.in);
		String masp;
		int year;
		int n=0;
		do {
			System.out.println("----QUẢN LÝ SẢN PHẨM----");
			System.out.print(
					  "1. Tìm kiếm sản phẩm\n"
					+ "2. Thêm sản phẩm\n"
					+ "3. Xóa sản phẩm\n"
					+ "4. Sửa sản phẩm\n"
					+ "5. Hiển thị danh sách sản phẩm\n"
					+ "6. Tìm sản phẩm không bán được theo năm\n"
					+ "7. Thống kê doanh thu\n"
					+ "0. Thoát\n"
					+ "Lựa chọn: ");
			n = sc.nextInt();
		switch(n) {
		case 1:
			System.out.print("Nhập mã sản phẩm cần tìm: ");
			masp = t.nextLine();
			executeFunction.search(masp);
			
			break;
		case 2:
			executeFunction.add();
			
			break;
		case 3:
			System.out.print("Nhập mã sản phẩm cần xóa: ");
			masp = t.nextLine();
			executeFunction.delete(masp);
			break;
		case 4:
			System.out.print("Nhập mã sản phẩm cần sửa: ");
			masp = t.nextLine();		
			executeFunction.edit(masp);
			
			break;
		case 5:
			executeFunction.print();
			
			break;
		case 6:
			System.out.print("Sản phẩm không bán được theo năm: ");
			year = sc.nextInt();
			executeFunction.sale(year);
			break;
		case 7:
			System.out.print("Nhập năm cần thống kê doanh thu: ");
			year = sc.nextInt();
			System.out.println("1. Thống kê theo ngày"
							+"\n2. Thống kê theo tháng"
							+"\n0. Thoát");
			System.out.print("Nhập: ");
			int req = t.nextInt();
			if(req==1 || req==2) executeFunction.revenue(year,req);
			break;
		default: 
			System.out.println("***********Sai cú pháp***********");
			break;
		}
		}while(n!=0);
	}

}
