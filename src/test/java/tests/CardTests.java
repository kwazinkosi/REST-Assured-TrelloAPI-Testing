package tests;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.CardEndPoints;
import io.restassured.response.Response;
import models.Card;
import utils.FileManager;

public class CardTests {

	private Card cardPayload;
	public static Properties test_data;
	public Logger log;

	@BeforeClass
	public void setup() {

		log = LogManager.getLogger(this.getClass());
		System.out.println("\n\n+====================== Card Tests =====================+\n"
				+ "+--------------------------------------------------------+\n");
		log.info(
				"\n\n+============================================== Card Tests ================================================+\n"
						+ "+-----------------------------------------------------------------------------------------------------------+\n");
		CardEndPoints.setup();
		cardPayload = new Card();

		try {
			test_data = FileManager.loadProperties("test_data.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String boardID = test_data.getProperty("board_card_id").trim(); // used to get 14 char id
		String listName = test_data.getProperty("card_list_name");
		String url = CardEndPoints.getUrl("base_url", "board_getlists_url");
		Response res = CardEndPoints.getRequestById(url, boardID);
		String id = res.jsonPath().getString("find { it.name == '" + listName + "' }.id");
		String cardName = test_data.getProperty("card_name");
		String desc = test_data.getProperty("card_desc");

		cardPayload.setName(cardName);
		cardPayload.setIdList(id);
		cardPayload.setDesc(desc);
		System.out.println("+---------------CardTests setup Done!!----------------+\n");
		log.info("+--------------------CardTests setup Done!!-------------------+\n");
	}

	@Test(priority = 1)
	public void testBoardCreation() {

		System.out.println("+------ testCardCreation() -> Testing new card creation -----+\n");
		log.info("+------ testCardCreation() -> Testing new card creation -----+\n");
		Response res = CardEndPoints.createCard(cardPayload);

		res.then().log().all();
		String id = res.jsonPath().getString("id");
		cardPayload.setCardId(id);
		Assert.assertEquals(res.getStatusCode(), CardEndPoints.SUCCESS);
		System.out.println("=== testCardCreation() PASSED!!");
		log.info("	=== testCardCreation() PASSED!! ===\n");
	}

	@Test(priority = 2)
	public void testGetCard() {

		System.out.println("+---------- testGetCard() -> Testing card reading -----------+\n");
		log.info("+----------- testGetCard() -> Testing card reading -----------+\n");

		// if the card id is not specified, use the card id of the previously created
		// card
		String cardID = test_data.getProperty("card_id");
		if (cardID.isEmpty())
			cardID = cardPayload.getCardId();

		Response res = CardEndPoints.getCard(cardID);

		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), CardEndPoints.SUCCESS);
		Assert.assertTrue(test_data.getProperty("card_name").equals(res.jsonPath().getString("name")));
		
		System.out.println("=== testGetCard() PASSED!!");
		log.info("	=== testGetLCard() PASSED!! ===\n");
	}

	@Test(priority = 3)
	public void testUpdateCard() {

		System.out.println("+------- testUpdateCard() -> Testing card updatability -------+\n");
		log.info("+------- testUpdateCard() -> Testing board updatability -------+\n");

		String newCardName = test_data.getProperty("new_card_name").trim();
		cardPayload.setName(newCardName);

		Response res = CardEndPoints.updateCard(cardPayload);
		String CardName = res.jsonPath().getString("name");

		Assert.assertEquals(res.getStatusCode(), CardEndPoints.SUCCESS);
		Assert.assertEquals(CardName, newCardName);

		System.out.println("=== testUpdateCard() PASSED!!");
		log.info("	=== testUpdateCard() PASSED!! ===\n");
	}

	@Test(priority = 4)
	public void testDeleteCard() {

		System.out.println("+------- testDeleteCard() -> Testing card deletability -------+\n");
		log.info("+------- testDeleteCard() -> Testing card deletability -------+\n");

		String listID = cardPayload.getIdList();
		String cardNameDel = test_data.getProperty("card_del_name");
		String url = CardEndPoints.getUrl("base_url", "list_get_cards_url");
		Response res = CardEndPoints.getRequestById(url, listID); // list of cards in an array
		res.then().log().all();
		String id = res.jsonPath().getString("find { it.name == '" + cardNameDel + "' }.id");

		// Positive scenario
		Response resPos = CardEndPoints.deleteCard(id, cardNameDel);
		resPos.then().log().all();
		
		String value = resPos.jsonPath().getString("_value");
		Assert.assertEquals(resPos.getStatusCode(), CardEndPoints.SUCCESS);
		Assert.assertEquals(value, null);
		
		// Negative scenario: Attempt to delete the same card again (already deleted)
	    Response resNegative = CardEndPoints.deleteCard(id, cardNameDel);
	    resNegative.then().log().all();
	    Assert.assertEquals(resNegative.getStatusCode(), CardEndPoints.NOT_FOUND); // Expected status code for a non-existent resource

		System.out.println("=== testDeleteCard() PASSED!!");
		log.info("	=== testDeleteCard() PASSED!! ===\n");
	}

}