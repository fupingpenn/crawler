package crawler.theKnot;

public class Constants {
	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/weddingcost";
	public static final String DATABASE_NAME = "fuping";
	public static final String DATABASE_PASSWORD = "fuping";
	
	public static final String URL_TEMPLATE = "http://services.theknot.com/planner/v2/vendors.json?"
			+ "apikey=vkq9ckuqn9c7jprn92uwbsjkzmtbk6pdxh9&"
			+ "limit=120&servicesCode=%CODE%&"
			+ "offset=0&"
			+ "latitude=%LAT%&"
			+ "longitude=%LNG%";
	
	public static final String CODES[] = new String[] {"CAR", "PHO", "FAD", "BWP", "WCS", "DJS",
			"VID", "INV", "HBF", "CAT", "CCA", "JWL", "BDS", "OWS", "COF", "EQR", "PHB", "FAV", 
			"RDP", "TRA", "MFW", "SOL", "BRG", "HAT", "GAC", "BCH", "DNC", "LAD", "CAL", "GPR",
			"HFA", "YNH", "BEV"};
	
	public static final String CATEGORIES[] = new String[] { "Wedding Venues", "Photographers",
		"Flowers", "Bridal Fashions", "Wedding Planners", "Disc Jockeys", "Videographers",
		"Invitations", "Hair, Makeup and Wellness", "Caterers", "Wedding Cakes & Desserts",
		"Wedding Rings and Fine Jewerly", "Bands", "Unique Wedding Ideas",
		"Officiants and Premarital Counseling", "Party Rentals & Service Staff", "Photo Booths",
		"Favors", "Rehearsal Dinners & Bridal Showers", "Limousine Services/Valet Services",
		"Tuxedos and Formal Wear", "Ceremony Musicians", "Gifts & Registries", "Travel & Honeymoons",
		"Guest Accommodations", "Bachelor(ette) Parties", "Dance Lessons", "Lighting & Decor",
		"Calligraphy", "Gown Cleaning & Preservation", "Fashion Jewlery and Accessories",
		"House & Home", "Wine, Champagne & Liquor"
	};
}
