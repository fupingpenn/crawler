package crawler.theKnot;

public class Coordinate {
	private final String mZipcode;
	private final String mLatitude;
	private final String mLongitude;
	
	public Coordinate(String zipcode, String latitude, String longitude) {
		mZipcode = zipcode;
		mLatitude = latitude;
		mLongitude = longitude;
	}
	
	public String getLatitude() {
		return mLatitude;
	}
	
	public String getLongitude() {
		return mLongitude;
	}
	
	public String getZipcode() {
		return mZipcode;
	}
}
