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

		String cardListID = payload.getIdList();
		String url = getUrl("base_url", "card_post_url");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("idList", cardListID).body(payload)
						.when().post(url);
		return res;
	}

	// Get Board
	public static Response getCard(String cardID) {

		String url = getUrl("base_url", "card_get_url");
		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", cardID)
						.when().get(url);

		return res;
	}

	// Update Board
	public static Response updateCard(Card payload) {

		String cardID = payload.getCardId();
		String cardName = payload.getName();
		String desc = payload.getDesc();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", cardName);
		parameters.put("desc", desc);

		String url = getUrl("base_url", "card_get_url");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", cardID).queryParams(parameters).body(payload)
						.when().put(url);

		return res;
	}

	// Delete Card
	public static Response deleteCard(String id, String cardNameDel) {

		String url = getUrl("base_url", "card_delete_url");
		
		Response res = given().pathParam("id", id)
						.when().delete(url); 
		return res;
	}
}
