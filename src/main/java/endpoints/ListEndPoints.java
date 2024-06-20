package endpoints;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import base.Base;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Board;
import models.List;

public class ListEndPoints extends Base {
	
	public ListEndPoints() {
		super();
	}

	// Create List
	public static Response createList(List payload) {

		String listName = payload.getListName().trim();
		String boardID = payload.getIdBoard().trim();
		String url = getUrl("base_url", "list_post_url");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("name", listName).pathParam("idBoard", boardID).body(payload)
						.when().post(url);
		
		return res;
	}
	
	// Get List
	public static Response getList(String listID) {

		String url = getUrl("base_url", "list_get_url");
		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", listID)
						.when().get(url);
		
		return res;
	}
	
	// Update List
	public static Response updateList(List payload) {

		String listID = payload.getListID();
		String listName = payload.getListName();
		String listPos = payload.getPos();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", listName);
		parameters.put("pos", listPos);
		String url = getUrl("base_url", "list_get_url");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", listID).queryParams(parameters).body(payload)
						.when().put(url);

		return res;
	}
	
	// Delete List
	public static Response deleteList(String listID, String listName) {
		
		String url = getUrl("base_url", "list_delete_url");
		Response res = given().pathParam("id", listID).queryParam("closed", "true" )
						.when().put(url); 
		return res;
	}
}