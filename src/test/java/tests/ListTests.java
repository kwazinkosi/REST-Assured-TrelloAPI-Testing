package tests;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.ListEndPoints;
import io.restassured.response.Response;
import models.List;
import utils.FileManager;

public class  ListTests {

	private List listPayload;
	public static Properties test_data;
	public Logger log;
	
	@BeforeClass
	public void setup(){
		
		log = LogManager.getLogger(this.getClass());
		System.out.println("\n\n+====================== List Tests =====================+\n"
							 + "+--------------------------------------------------------+\n");
		log.info("\n\n+============================================== List Tests ================================================+\n"
				+ "+-----------------------------------------------------------------------------------------------------------+\n");
		ListEndPoints.setup();
		listPayload =new List();
		
		try {
			test_data = FileManager.loadProperties("test_data.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String listName = test_data.getProperty("list_name");

		String boardID = test_data.getProperty("list_board_ID").trim(); //used to get 14 char id
		String id = ListEndPoints.getBoardId(boardID, "board_get_url"); // 14 char id
		listPayload.setIdBoard(id);
		listPayload.setListName(listName);
		
		System.out.println("+---------------ListTests setup Done!!----------------+\n");
		log.info("+--------------------ListTests setup Done!!-------------------+\n");
	}

	@Test(priority = 1)
	public void testListCreation() {
		
		System.out.println("+------ testListCreation() -> Testing new  creation -----+\n");
		log.info("+------ testListCreation() -> Testing new  creation -----+\n");
		
		Response res = ListEndPoints.createList(listPayload);
		
		String shortUrl= res.jsonPath().getString("shortUrl");
		String listId = shortUrl.replaceAll("^.*b/", ""); 
		listPayload.setIdBoard(listId);
		
		try {
			test_data = FileManager.updateProperties("test_data.properties", "list_id", listId);
			test_data = FileManager.updateProperties("test_data.properties", "list_get_id", listId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(res.getStatusCode(), 200);
		System.out.println("=== testListCreation() PASSED!!");
		log.info("	=== testListCreation() PASSED!! ===\n");
	}
}