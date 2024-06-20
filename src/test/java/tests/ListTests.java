package tests;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import endpoints.BoardEndPoints;
import endpoints.ListEndPoints;
import endpoints.ListEndPoints;
import io.restassured.path.json.JsonPath;
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
		
		String listId= res.jsonPath().getString("id");
		listPayload.setListID(listId);
		try {
			test_data = FileManager.updateProperties("test_data.properties", "list_id", listId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(res.getStatusCode(), 200);
		System.out.println("=== testListCreation() PASSED!!");
		log.info("	=== testListCreation() PASSED!! ===\n");
	}
	
	@Test(priority = 2)
	public void testGetList() {
		
		System.out.println("+---------- testGetList() -> Testing board reading -----------+\n");
		log.info("+----------- testGetList() -> Testing board reading -----------+\n");
		
		// if the list id is not specified, use the list id of the previously created list
		String listID = test_data.getProperty("list_id");
		if (listID.isEmpty()) listID = listPayload.getListID();
		
		Response res = ListEndPoints.getList(listID);
	
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
		System.out.println("=== testGetList() PASSED!!");
		log.info("	=== testGetList() PASSED!! ===\n");
	}
	
	@Test(priority = 3)
	public void testUpdateList() {
		
		System.out.println("+------- testUpdateList() -> Testing board updatability -------+\n");
		log.info("+------- testUpdateList() -> Testing board updatability -------+\n");
		
		String newListName = test_data.getProperty("new_list_name");
		String newListPos = test_data.getProperty("new_list_pos");
		listPayload.setListName(newListName);
		listPayload.setPos(newListPos);
		
		Response res = ListEndPoints.updateList(listPayload);
		String ListName= res.jsonPath().getString("name");
		
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(ListName, newListName);
		
		System.out.println("=== testUpdateList() PASSED!!");	
		log.info("	=== testUpdateList() PASSED!! ===\n");	
	}
	
	@Test(priority = 4)
	public void testDeleteList() {
		
		System.out.println("+------- testDeleteList() -> Testing list deletability -------+\n");
		log.info("+------- testDeleteList() -> Testing list deletability -------+\n");
		
		String boardID = test_data.getProperty("list_board_ID").trim(); //used to get 14 char id
		String listNameDel = test_data.getProperty("list_del_name");
		String url =  ListEndPoints.getUrl("base_url", "board_getlists_url");
		Response res = ListEndPoints.getRequestById(url, boardID);
		String id = res.jsonPath().getString("find { it.name == '" + listNameDel + "' }.id");		
		
		try {
			test_data = FileManager.updateProperties("test_data.properties", "list_del_id", id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Response resD = ListEndPoints.deleteList(id, listNameDel);
		
		String value= resD.jsonPath().getString("_value");
		resD.then().log().all();
		Assert.assertEquals(resD.getStatusCode(), 200);
		Assert.assertEquals(value, null);
		System.out.println("=== testDeleteList() PASSED!!");
		log.info("	=== testDeleteList() PASSED!! ===\n");
	}
	
}