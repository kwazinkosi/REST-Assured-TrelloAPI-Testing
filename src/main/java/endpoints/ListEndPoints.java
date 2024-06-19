package endpoints;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import base.Base;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.List;

public class ListEndPoints extends Base {
	
	public ListEndPoints() {
		super();
	}

	// Create List
	public static Response createList(List payload) {

		String listName = payload.getListName();
		String boardID = payload.getIdBoard();
		String url = getUrl("base_url", "list_post_url");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).queryParam("name", listName).queryParam("idBoard", boardID).body(payload)
						.when().post(url);
		return res;
	}
	
	
	
	
}