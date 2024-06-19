/*package tests;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.BoardEndPoints;
import endpoints.CardEndPoints;
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
		System.out.println("\n\n+====================== Board Tests =====================+\n"
							 + "+--------------------------------------------------------+\n");
		log.info("\n\n+============================================== Board Tests ================================================+\n"
				+ "+-----------------------------------------------------------------------------------------------------------+\n");
		CardEndPoints.setup();
		cardPayload =new Card();
		
		try {
			test_data = FileManager.loadProperties("board_test_data.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String boardName = test_data.getProperty("board_name");
		String boardID = test_data.getProperty("board_id");
		String desc = test_data.getProperty("desc");
		
		cardPayload.setCardName(boardName);
		cardPayload.setId(boardID);
		cardPayload.setDescription(desc);
		
		System.out.println("+---------------BoardTests setup Done!!----------------+\n");
		log.info("+--------------------BoardTests setup Done!!-------------------+\n");
	}

	@Test(priority = 1)
	public void testBoardCreation() {
		
		System.out.println("+------ testBoardCreation() -> Testing new board creation -----+\n");
		log.info("+------ testBoardCreation() -> Testing new board creation -----+\n");
		Response res = BoardEndPoints.createBoard(boardPayload);
		
		String shortUrl= res.jsonPath().getString("shortUrl");
		String boardId = shortUrl.replaceAll("^.*b/", ""); 
		boardPayload.setId(boardId);
		
		try {
			test_data = FileManager.updateProperties("test_data.properties", "board_id", boardId);
			test_data = FileManager.updateProperties("test_data.properties", "board_get_id", boardId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(res.getStatusCode(), 200);
		System.out.println("=== testBoardCreation() PASSED!!");
		log.info("	=== testBoardCreation() PASSED!! ===\n");
	}
}*/