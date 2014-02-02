package it.polimi.travelDream.ejb.dtos;

public class CustomPackageDTO {
	
	private Long id;
	private Long days;
	private String configuration;
	private String user;
	private String origPackage;

	public CustomPackageDTO() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public Long getDays() {
		return this.days;
	}

	public void setDays(Long days) {
		this.days = days;
	}   
	public String getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}   
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}   
	public String getOrigPackage() {
		return this.origPackage;
	}

	public void setOrigPackage(String origPackage) {
		this.origPackage = origPackage;
	}
}
