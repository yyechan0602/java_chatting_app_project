package server;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private String URL = "jdbc:mysql://localhost:3306/chatting";

	private String USERNAME = "root";
	private String PASSWORD = "seulin001024*";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	boolean result = false;
	String resultStr = null;

	public Database() {
		try {
			Class.forName(DRIVER_CLASS);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Database connection successful...");
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
			rs = stmt.executeQuery(sql);
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

	public boolean SignUp(String id) {
		String sql = ("select id from clients where id = '" + id + "';");
		try {
			rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	public boolean LogIn(String id, String password) {
		String sql = ("select password from clients where id = '" + id + "';");
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				String spassword = rs.getString("password");
				if (spassword.equals(password)) {
					result = true;
				} else {
					result = false;
				}
			} else {
				result = false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	public String getName(String id) {
		String sql = ("select name from clients where id = '" + id + "';");
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			resultStr = rs.getString("name");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return resultStr;
	}

	public String getSex(String id) {
		String sql = ("select sex from clients where id = '" + id + "';");
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			resultStr = rs.getString("sex");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return resultStr;
	}
}
