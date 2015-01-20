package crawler.theKnot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class analyser {
	public static void main(String args[]) {
		HashSet<String> accounts = new HashSet<String>();
		Connection connect = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(
					Constants.DATABASE_URL, Constants.DATABASE_NAME, Constants.DATABASE_PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		if (connect == null) {
			System.out.println("Fail to connect to database");
			return;
		}
		int emptyCount = 0;
		int totalItems = 0;
		try {
			Statement statement = connect.createStatement();
			String sql = "select vendor from vendors";
			ResultSet results = statement.executeQuery(sql);
			JSONParser parser = new JSONParser();
			while (results.next()) {
				JSONObject object = (JSONObject)parser.parse(results.getString(1));
				String accountId = (String) object.get("accountId");
				if (accountId == null || accountId.isEmpty()) {
					emptyCount++;
				} else {
					accounts.add(accountId);
				}
				totalItems++;
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Total entry account is " + totalItems + " Empty count is " + emptyCount + " Unique count is " + accounts.size());
	}
}
