package it.polimi.travelDream.web.beans.component;

import java.io.IOException;
import java.security.Principal;

import it.polimi.travelDream.ejb.beans.ComponentBeanInterface;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="remComponentBean")
@ViewScoped
public class RemComponentBean {
	
	@EJB
	private ComponentBeanInterface componentBean;
	
	private String compName;
	
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	private String confirm;
	
	private UserDTO user;
	
	public UserDTO getUser() {
		return user;
	}
	
	public boolean userLogged() {
		return (user != null);
	}
	
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	@PostConstruct
	public void init() {
		String compName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("compName");
		if (compName != null && compName.length() > 0)
			this.compName = compName;
		
		confirm = "false";
		
		Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (p != null)
			user = componentBean.getUserDTO(p.getName());
	}
	
	public String remove() {
		boolean c = Boolean.parseBoolean(confirm);
		if (c) {
			componentBean.remove(compName);
		}
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect(DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true");
		} catch (IOException e) { }
		
		return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
	}
	
}
