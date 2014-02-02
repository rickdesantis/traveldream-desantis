package it.polimi.travelDream.web.beans.pkg;

import java.io.IOException;
import java.security.Principal;

import it.polimi.travelDream.ejb.beans.PackageBeanInterface;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="remPackageBean")
@RequestScoped
public class RemPackageBean {

	@EJB
	private PackageBeanInterface packageBean;
	
	private String packName;
	
	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
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
		String packName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("packageName");
		if (packName != null && packName.length() > 0)
			this.packName = packName;

		confirm = "false";
		
		Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (p != null)
			user = packageBean.getUserDTO(p.getName());
	}
	
	public String remove() {
		boolean c = Boolean.parseBoolean(confirm);
		if (c) {
			packageBean.remove(packName);
		}
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect(DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true");
			return "";
		} catch (IOException e) { }
		
		return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
	}
}
