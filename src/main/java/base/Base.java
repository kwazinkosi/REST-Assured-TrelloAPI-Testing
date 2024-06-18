package base;

import java.io.IOException;
import java.util.Properties;

import utils.FileManager;

public class Base {

	public static Properties route_props =null;
	private static String API_KEY;
	private static String TOKEN;
	
	
	public Base() {
		
	}
	
	public static void setup() {
		
		try {
			route_props = FileManager.loadProperties("routes.properties");
			Properties auth_props = FileManager.loadProperties("trello.properties");
			System.out.println("\n\n+---------------Routes and API auth loaded----------------+\n");
			
			
			API_KEY = auth_props.getProperty("api-key");
			TOKEN = auth_props.getProperty("api-token");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Properties getRoutes() {
		
		return route_props;
	}
	
	public static String getUrl(String baseUrl, String urlType) {
	    Properties routes = getRoutes(); // Store routes in a variable

	    String base = routes.getProperty(baseUrl);
	    String urlSuffix = routes.getProperty(urlType);

	    if (base == null || urlSuffix == null) {
	        throw new IllegalArgumentException("Missing property in routes.properties: " + baseUrl + " or " + urlType);
	    }

	    String url = String.format("%s%skey=%s&token=%s", base, urlSuffix, API_KEY, TOKEN);
	    return url;
	}

}
