package crawler.theKnot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import crawler.framework.Crawler;
import crawler.framework.SynchronizedQueue;

public class TheKnotCrawler extends Crawler {
	
	public TheKnotCrawler(SynchronizedQueue queue) {
		super(queue);
	}

	@Override
	public String[] getUrl(Object object) {
		Coordinate coordinate = (Coordinate) object;
		String res[] = new String[Constants.CODES.length];
		for (int i = 0; i < Constants.CODES.length; ++i) {
			res[i] = Constants.URL_TEMPLATE.replace("%CODE%", Constants.CODES[i])
					.replace("%LAT%", coordinate.getLatitude())
					.replace("%LNG%", coordinate.getLongitude());
		}
		return res;
	}

	@Override
	public void processResponse(Object item, String[] response) {
		try {
			Connection connect = null;
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(
					Constants.DATABASE_URL, Constants.DATABASE_NAME, Constants.DATABASE_PASSWORD);
			for (int i = 0; i < response.length; ++i) {
				JSONParser parser = new JSONParser();
				JSONObject object;
				object = (JSONObject)parser.parse(response[i].toString());
				JSONArray vendors = (JSONArray)object.get("vendors");
				for (int j = 0; j < vendors.size(); ++j) {
					JSONObject vendor = (JSONObject)vendors.get(j);
					JSONObject output = new JSONObject();
					output.put("description", vendor.get("description"));
					output.put("photoSummary", vendor.get("photoSummary"));
					output.put("reviewSummary", vendor.get("reviewSummary"));
					output.put("facebookUrl", vendor.get("facebookUrl"));
					output.put("logo", vendor.get("logo"));
					output.put("remoteUrl", vendor.get("remoteUrl"));
					output.put("categories", vendor.get("categories"));
					output.put("headline", vendor.get("headline"));
					output.put("email", vendor.get("email"));
					output.put("designers", vendor.get("designers"));
					output.put("twitterName", vendor.get("twitterName"));
					output.put("phone", vendor.get("phone"));
					output.put("name", vendor.get("name"));
					output.put("location", vendor.get("location"));
					output.put("accountId", vendor.get("accountId"));
					output.put("vendorId", vendor.get("vendorId"));
					String sql = "insert into vendors(zipcode, category, category_code, vendor) "
							+ "values (?, ?, ?, ?)";
					PreparedStatement statement = connect.prepareStatement(sql);
					Coordinate coordinate = (Coordinate) item;
					statement.setString(1, String.valueOf(coordinate.getZipcode()));
					statement.setString(2, Constants.CATEGORIES[i]);
					statement.setString(3, Constants.CODES[i]);
					statement.setString(4, output.toJSONString());
					statement.execute();
					statement.close();
				}
			}
			connect.close();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCrawlSucceed(Object object) {
		Statistics.getInstance().done();
	}

}
