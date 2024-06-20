package endpoints;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

import base.Base;
import io.restassured.response.Response;
import models.Board;
import io.restassured.http.ContentType;

public class BoardEndPoints extends Base {

	public BoardEndPoints() {
		super();
	}

	// Create a new Board
	public static Response createBoard(Board payload) {

		String boardName = payload.getBoardName();
		String url = getUrl("base_url", "board_post_url");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("name", boardName).body(payload)
						.when().post(url);
		return res;
	}

	// Get Board
	public static Response getBoard(String boardID) {

		String url = getUrl("base_url", "board_get_url");
		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", boardID)
						.when().get(url);

		return res;
	}

	// Update Board
	public static Response updateBoard(Board payload) {

		String boardID = payload.getId();
		String boardName = payload.getBoardName();
		String desc = payload.getDescription();
		// create query parameters
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", boardName);
		parameters.put("desc", desc);

		String url = getUrl("base_url", "board_get_url");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", boardID).queryParams(parameters).body(payload)
						.when().put(url);

		return res;
	}

	// Delete Board
	public static Response deleteBoard(String boardID) {

		String url = getUrl("base_url", "board_delete_url");
		Response res = given().pathParam("id", boardID)
						.when().delete(url); 
		return res;
	}
}
