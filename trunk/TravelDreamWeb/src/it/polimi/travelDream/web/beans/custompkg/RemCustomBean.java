package it.polimi.travelDream.web.beans.custompkg;

import java.io.IOException;
import java.security.Principal;

import it.polimi.travelDream.ejb.beans.CustomBeanInterface;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="remCustomBean")
@RequestScoped
public class RemCustomBean {

	@EJB
	private CustomBeanInterface packageBean;
	
	private String packageId;
	
	private String packageName;
	
	private UserDTO user;
	
	public UserDTO getUser() {
		return user;
	}
	
	public boolean userLogged() {
		return (user != null);
	}
	
	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packId) {
		this.packageId = packId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packName) {
		this.packageName = packName;
	}

	private String confirm;
	
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	@PostConstruct
	public void init() {
		String packageId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("packageId");
		if (packageId != null && packageId.length() > 0) {
			this.packageId = packageId;
			String packageName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("packageName");
			if (packageName != null && packageId.length() > 0) {
				this.packageName = packageName;
			} else {
				this.packageName = packageBean.getCustomPackageDTO(this.packageId).getOrigPackage();
			}
		}
		
		confirm = "false";
		
		Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (p != null)
			user = packageBean.getUserDTO(p.getName());
	}
	
	public String remove() {
		
		boolean c = Boolean.parseBoolean(confirm);
		if (c) {
			packageBean.remove(packageId);
		}
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect(DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true");
			return "";
		} catch (IOException e) { }
		
		return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
	}
	
}
