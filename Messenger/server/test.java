package server;

import java.sql.SQLException;
import java.sql.Statement;

public class test{
	public static void main(String[] args) {
		Database db = null;
		db = new Database();
		db.insert_client("LEE2", "3456", "seul", "female");
		db.clients();
		db.delete_client("LEE2");
		db.clients();
	}
}