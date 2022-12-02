package server;

import java.sql.SQLException;
import java.sql.Statement;

public class test {
	public static void main(String[] args) {
		Database db = null;
		db = new Database();
		db.insert_client("LEE2", "3456", "seul", "female");
		db.clients();
		db.delete_client("LEE2");
		db.clients();
		System.out.println("\n\n");
		System.out.println(db.SignUp("LEE"));
		System.out.println(db.SignUp("LEE2"));
		System.out.println(db.LogIn("LEE", "1234"));
		System.out.println(db.LogIn("LEE", "3456"));
		System.out.println(db.LogIn("LEE2", "1234"));
		System.out.println(db.getName("LEE"));
		System.out.println(db.getSex("LEE"));
	}
}