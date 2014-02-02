package it.polimi.travelDream.web.beans.profile;

import java.security.Principal;

import it.polimi.travelDream.ejb.beans.UserBeanInterface;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="profileBean")
@RequestScoped
public class ProfileBean {
	
	@EJB
	private UserBeanInterface userBean;
	
	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}
	
	private Principal p;
	
	@PostConstruct
	public void init() {
		String email = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("email");
		
		if (email == null || email.length() == 0) {
			p =  FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
			if (p != null)
				email = p.getName();
		}
		
		if (email != null && email.length() > 0)
			user = userBean.getUserDTO(email);
	}

	public String getSignature() {
		if (user == null)
			return "You're offline";
		
		return user.getName() + " " + user.getSurname().charAt(0) + ". (" + user.getRole().toUpperCase().charAt(0) + ")";
	}
	
	public String getMySignature() {
		if (p == null)
			return "You're offline";
		
		UserDTO user = userBean.getUserDTO(p.getName());
		
		return user.getName() + " " + user.getSurname().charAt(0) + ". (" + user.getRole().toUpperCase().charAt(0) + ")";
	}

}
