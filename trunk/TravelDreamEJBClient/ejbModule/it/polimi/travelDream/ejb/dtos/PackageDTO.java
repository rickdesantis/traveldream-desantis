package it.polimi.travelDream.ejb.dtos;

import java.util.List;

public class PackageDTO {
	
	private String name;
	private Long days;
	private String description;
	private List<String> components;

	public PackageDTO() {
		super();
	}   
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public Long getDays() {
		return this.days;
	}

	public void setDays(Long days) {
		this.days = days;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getComponents() {
		return components;
	}

	public void setComponents(List<String> components) {
		this.components = components;
	}
	
	
}
