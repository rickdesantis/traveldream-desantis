package it.polimi.travelDream.web.beans.pkg;

import it.polimi.travelDream.ejb.beans.PackageBeanInterface;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.PackageDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="viewPackageBean")
@RequestScoped
public class ViewPackageBean {

	@EJB
	private PackageBeanInterface packageBean;
	
	private PackageDTO pack;
	
	private UserDTO user;
	
	public UserDTO getUser() {
		return user;
	}
	
	@PostConstruct
	public void init() {
		try {
			String name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("packageName");
			pack = packageBean.getPackageDTO(name);
		} catch (Exception e) { pack = new PackageDTO(); }
		
		Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (p != null)
			user = packageBean.getUserDTO(p.getName());
	}

	public PackageDTO getPackage() {
		return pack;
	}
	
	public List<ComponentDTO> getAllComponents() {
		return packageBean.getAllComponentsDTO(pack);
	}
	
	public Long getTotalPrice() {
		return packageBean.getTotalPrice(pack);
	}
	
}
