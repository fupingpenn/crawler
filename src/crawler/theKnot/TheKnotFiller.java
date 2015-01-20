package crawler.theKnot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import crawler.framework.Filler;
import crawler.framework.SynchronizedQueue;

public class TheKnotFiller extends Filler {
	@Override
	public void fillQueue(SynchronizedQueue queue) {
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
		
		try {
			Statement statement = connect.createStatement();
			String sql = "select zipcode, latitude, longitude from geocode where zipcode not in "
					+ "(select zipcode from vendors)";
			ResultSet results = statement.executeQuery(sql);
			int count = 0;
			while(results.next()) {
				queue.add(new Coordinate(
						results.getString(1), results.getString(2), results.getString(3)));
				++count;
			}
			statement.close();
			Statistics.getInstance().setNumber(count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
