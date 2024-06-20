package models;

public class List {

	public String idBoard; //ID of the List to copy into the new List
	public String name; //Name for the list
	public String idListSource;
	public String pos;
	
	public String getListID() {
		return idListSource;
	}
	public void setListID(String listID) {
		this.idListSource = listID;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getIdBoard() {
		return idBoard;
	}
	public void setIdBoard(String idBoard) {
		this.idBoard = idBoard;
	}
	public String getListName() {
		return name;
	}
	public void setListName(String listName) {
		this.name = listName;
	}
	
}
