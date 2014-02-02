package it.polimi.travelDream.web.beans.user;

import java.io.IOException;

import it.polimi.travelDream.ejb.beans.UserBeanInterface;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="mainBean")
@SessionScoped
public class MainBean {
	
	@EJB
	private UserBeanInterface userBean;
	
	private UserDTO user;
	
	public UserDTO getUser() {
		if (getIsOffline())
			return null;
		return user;
	}
	
	public String logout() {
		/*
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		} catch (Exception e) { }
		*/
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect(DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true");
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		} catch (IOException e) { }
		
	    return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
	  }
	
	public boolean getIsOffline() {
		if (userBean.isUserOnline())
			user = userBean.getUserDTO();
		return (!userBean.isUserOnline() || user == null || user.getRole() == null);
	}
	
	public boolean hasRole(String role) {
		if (getIsOffline())
			return false;
		
		String r = user.getRole();
		return r.equalsIgnoreCase(role.trim());
	}
	
	public boolean getIsCustomer2() {
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("customer");
	}
	
	public boolean getIsEmployee2() {
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("employee");
	}
	
	public boolean getIsCustomer() {
		return hasRole("customer");
	}
	
	public boolean getIsEmployee() {
		return hasRole("employee");
	}

}
