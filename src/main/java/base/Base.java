package base;

import static io.restassured.RestAssured.*;
import java.io.IOException;
import java.util.Properties;

import endpoints.ListEndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.FileManager;

public class Base {

	public static Properties route_props =null;
	private static String API_KEY;
	private static String TOKEN;
	
	public static final int NOT_FOUND = 404;
    public static final int BAD_REQUEST = 400;
    public static final int SUCCESS = 200;
    
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
	
	// Get API routes or urIs
	public static Properties getRoutes() {
		
		return route_props;
	}
	
	// Get the url of a specific API
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
	
	// Get the response of the given url with the specified id
	public static Response getRequestById(String url, String id) {
		
		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", id)
				.when().get(url);

		return res;
	}
	
	public static String getBoardId(String id, String urlType) {
		
		String url =  ListEndPoints.getUrl("base_url", urlType);
		Response res = getRequestById(url, id);
		res.then().log().all();
		String idBoard = res.jsonPath().getString("id");
		
		return idBoard;
	}
}
