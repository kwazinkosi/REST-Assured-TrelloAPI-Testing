package models;

public class Board {
	
	String id;
	String name;
	String desc;
	
	
	public String getDescription() {
		return desc;
	}
	public void setDescription(String description) {
		this.desc = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoardName() {
		return name;
	}
	public void setBoardName(String boardName) {
		this.name = boardName;
	}
	
}
