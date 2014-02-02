package it.polimi.travelDream.ejb.dtos;

public class NomComponentDTO {
	
	private String name;
	private String parameters;

	public NomComponentDTO() {
		super();
	}   
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getParameters() {
		return this.parameters;
	}
	
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
}
