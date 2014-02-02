package it.polimi.travelDream.ejb.dtos;

public class UserDTO {
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private String address;
	private String phone;
	private String role;

	public UserDTO() {
		super();
	}   
	 
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}   
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}   
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}   
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
