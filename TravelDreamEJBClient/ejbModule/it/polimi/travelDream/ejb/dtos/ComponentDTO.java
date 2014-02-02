package it.polimi.travelDream.ejb.dtos;

import java.util.List;

public class ComponentDTO {
	
	private String name;
	private String description;
	private String defaults;
	private String type;
	private String city;
	private Long price;
	private List<String> packages;

	public ComponentDTO() {
		super();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}   
	public String getDefaults() {
		return this.defaults;
	}

	public void setDefaults(String defaults) {
		this.defaults = defaults;
	}   
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}   
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public List<String> getPackages() {
		return packages;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}
	
}
