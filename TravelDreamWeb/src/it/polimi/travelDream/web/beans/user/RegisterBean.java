package it.polimi.travelDream.web.beans.user;

import java.io.IOException;
import java.util.List;

import it.polimi.travelDream.ejb.beans.UserBeanInterface;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.ejb.dtos.RoleDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="registerBean")
@RequestScoped
public class RegisterBean {
	
	@EJB
	private UserBeanInterface userBean;
	
	private UserDTO user;
	
	public RegisterBean() {
		user = new UserDTO();
	}
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public String register() {
		userBean.save(user);
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect(DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true");
		} catch (IOException e) { }
		
		return DefaultsBean.BASE_PATH + "index.html?faces-redirect=true";
	}
	
	public List<RoleDTO> getRoles() {
		return userBean.getAllRolesDTO();
	}
}
