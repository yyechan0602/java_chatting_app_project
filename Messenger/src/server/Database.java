package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private String DRIVER_CLASS = "org.mariadb.jdbc.Driver";
	private String URL = "jdbc:mariadb://localhost:3307/chatting";

	private String USERNAME = "root";
	private String PASSWORD = "qwe123";
	Connection conn = null;
	Statement stmt = null;

	public Database(){
		try {
			Class.forName(DRIVER_CLASS);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("연결성공");
			stmt = conn.createStatement();

		} catch (ClassNotFoundException e) {
			System.out.println("드라이브 로딩 실패");
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
		}
	}

	public void clients() {
		String sql = "Select * from clients;";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String sex = rs.getString("sex");
				System.out.println(id + "|" + password + "|" + name + "|" + sex);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insert_client(String id, String password, String name, String sex) {
		String sql = ("insert into clients values('" + id + "', '" + password + "', '" + name + "', '" + sex + "');");
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void delete_client(String id) {
		String sql = ("delete from clients where id = '" + id + "';");
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
