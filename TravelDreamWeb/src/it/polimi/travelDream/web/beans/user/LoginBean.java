package it.polimi.travelDream.web.beans.user;

import it.polimi.travelDream.ejb.beans.UserBeanInterface;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="loginBean")
@RequestScoped
public class LoginBean {
	
	@EJB
	private UserBeanInterface userBean;
	
	private UserDTO user;
	
	private String email;
	
	private String password;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
