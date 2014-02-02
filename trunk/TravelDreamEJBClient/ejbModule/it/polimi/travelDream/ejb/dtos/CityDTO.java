package it.polimi.travelDream.ejb.dtos;

public class CityDTO {
	
	private String name;
	private String country;

	public CityDTO() {
		super();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
