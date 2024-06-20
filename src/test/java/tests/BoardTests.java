package tests;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.BoardEndPoints;
import io.restassured.response.Response;
import models.Board;
import utils.FileManager;

public class BoardTests {

	private Board boardPayload;
	public static Properties test_data;
	public Logger log;
	@BeforeClass
	public void setup(){
		
		log = LogManager.getLogger(this.getClass());
		System.out.println("\n\n+====================== Board Tests =====================+\n"
							 + "+--------------------------------------------------------+\n");
		log.info("\n\n+============================================== Board Tests ================================================+\n"
				+ "+-----------------------------------------------------------------------------------------------------------+\n");
		BoardEndPoints.setup();
		boardPayload =new Board();
		
		try {
			test_data = FileManager.loadProperties("test_data.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String boardName = test_data.getProperty("board_name");
		String boardID = test_data.getProperty("board_id");
		String desc = test_data.getProperty("desc");
		
		boardPayload.setBoardName(boardName);
		boardPayload.setId(boardID);
		boardPayload.setDescription(desc);
		
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
	
	@Test(priority = 2)
	public void testGetBoard() {
		
		System.out.println("+---------- testGetBoard() -> Testing board reading -----------+\n");
		log.info("+----------- testGetBoard() -> Testing board reading -----------+\n");
		
		String boardID = test_data.getProperty("board_get_id");
		if (boardID.isEmpty()) boardID = boardPayload.getId();
		
		Response res = BoardEndPoints.getBoard(boardID);
	
		Assert.assertEquals(res.getStatusCode(), 200);
		
		System.out.println("=== testGetBoard() PASSED!!");
		log.info("	=== testGetBoard() PASSED!! ===\n");
	}
	
	@Test(priority = 3)
	public void testUpdateBoard() {
		
		System.out.println("+------- testUpdateBoard() -> Testing board updatability -------+\n");
		log.info("+------- testUpdateBoard() -> Testing board updatability -------+\n");
		
		String newBoardName = test_data.getProperty("new_board_name").trim();
		String desc = test_data.getProperty("desc_update");
		
		boardPayload.setBoardName(newBoardName);
		boardPayload.setDescription(desc);
		Response res = BoardEndPoints.updateBoard(boardPayload);
		
		Assert.assertEquals(res.getStatusCode(), 200);
		
		String boardName= res.jsonPath().getString("name");
		String description= res.jsonPath().getString("desc");
		
		Assert.assertEquals(boardName, newBoardName);
		Assert.assertTrue(description.contentEquals(desc));
		
		System.out.println("=== testUpdateBoard() PASSED!!");	
		log.info("	=== testUpdateBoard() PASSED!! ===\n");	
	}
	
	@Test(priority = 4)
	public void testDeleteBoard() {
		
		System.out.println("+------- testDeleteBoard() -> Testing board deletability -------+\n");
		log.info("+------- testDeleteBoard() -> Testing board deletability -------+\n");
		
		String boardID =test_data.getProperty("board_del_id");
		Response res = BoardEndPoints.deleteBoard(boardID);

		String value= res.jsonPath().getString("_value");
		
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(value, null);
		System.out.println("=== testDeleteBoard() PASSED!!");
		log.info("	=== testDeleteBoard() PASSED!! ===\n");
	}
	
	// ======== Negative tests =============
	
	
}
