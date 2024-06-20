package tests;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.CardEndPoints;
import endpoints.ListEndPoints;
import io.restassured.response.Response;
import models.Card;
import utils.FileManager;

public class CardTests {

	private Card cardPayload;
	public static Properties test_data;
	public Logger log;
	@BeforeClass
	public void setup(){
		
		log = LogManager.getLogger(this.getClass());
		System.out.println("\n\n+====================== Card Tests =====================+\n"
							 + "+--------------------------------------------------------+\n");
		log.info("\n\n+============================================== Card Tests ================================================+\n"
				+ "+-----------------------------------------------------------------------------------------------------------+\n");
		CardEndPoints.setup();
		cardPayload =new Card();
		
		try {
			test_data = FileManager.loadProperties("test_data.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String boardID = test_data.getProperty("board_card_id").trim(); //used to get 14 char id
		String listName = test_data.getProperty("card_list_name");
		String url =  CardEndPoints.getUrl("base_url", "board_getlists_url");
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
		Assert.assertEquals(res.getStatusCode(), 200);
		System.out.println("=== testCardCreation() PASSED!!");
		log.info("	=== testCardCreation() PASSED!! ===\n");
	}
	
	@Test(priority = 2)
	public void testGetList() {
		
		System.out.println("+---------- testGetList() -> Testing card reading -----------+\n");
		log.info("+----------- testGetList() -> Testing card reading -----------+\n");
		
		// if the list id is not specified, use the list id of the previously created list
		String cardID = test_data.getProperty("card_list_id");
		if (cardID.isEmpty()) cardID = cardPayload.getCardId();
		
		Response res = CardEndPoints.getCard(cardID);
	
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
		System.out.println("=== testGetCard() PASSED!!");
		log.info("	=== testGetLCard() PASSED!! ===\n");
	}
}