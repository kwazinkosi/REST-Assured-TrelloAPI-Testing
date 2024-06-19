package endpoints;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import base.Base;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Card;

public class CardEndPoints extends Base {
	
	public CardEndPoints() {
		super();
	}

	// Create Board
	public static Response createCard(Card payload) {

		String cardName = payload.getCardName();
		String url = getUrl("base_url", "card_post_url");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("name", cardName).body(payload)
						.when().post(url);
		return res;
	}

	/*// Get Board
	public static Response getBoard(String listID) {

		String url = getUrl("base_url", "list_get_url");
		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", listID)
						.when().get(url);

		return res;
	}

	// Update Board
	public static Response updateBoard(List payload) {

		String listID = payload.getId();
		String boardName = payload.getBoardName();
		String desc = payload.getDescription();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", boardName);
		parameters.put("desc", desc);

		String url = getUrl("base_url", "list_get_url");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", listID).queryParams(parameters).body(payload)
						.when().put(url);

		return res;
	}

	// Delete Board
	public static Response deleteBoard(String boardID) {

		String url = getUrl("base_url", "board_delete_url");
		Response res = given().pathParam("id", boardID)
						.when().delete(url); 
		return res;
	}*/
}
