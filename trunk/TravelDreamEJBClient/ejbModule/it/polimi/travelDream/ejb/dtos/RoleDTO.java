package it.polimi.travelDream.ejb.dtos;

public class RoleDTO {

	private String name;
	private Long level;

	public RoleDTO() {
		super();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public Long getLevel() {
		return this.level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}
}
