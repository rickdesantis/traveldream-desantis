package it.polimi.travelDream.web.beans.custompkg;

import it.polimi.travelDream.ejb.beans.CustomBeanInterface;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.CustomPackageDTO;
import it.polimi.travelDream.ejb.dtos.PackageDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="viewCustomBean")
@RequestScoped
public class ViewCustomBean {
	
	@EJB
	private CustomBeanInterface customBean;
	
	private CustomPackageDTO pack;
	
	private PackageDTO orig;
	
	private UserDTO user;
	
	private UserDTO owner;
	
	public UserDTO getUser() {
		return user;
	}
	
	public UserDTO getOwner() {
		return owner;
	}
	
	public boolean userIsOwner() {
		if (user == null)
			return false;
		return (user.getEmail().equals(owner.getEmail()));
	}

	@PostConstruct
	public void init() {
		Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (p != null)
			user = customBean.getUserDTO(p.getName());
		
		String c = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
		if (c != null && c.length() > 0 && c.trim().equalsIgnoreCase("true")) {
			String orig = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("packageName");
			this.orig = customBean.getPackageDTO(orig);
			pack = customBean.createCustomPackage(this.orig, user);
			owner = user;
		}
		else {
			try {
				String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("packageId");
				pack = customBean.getCustomPackageDTO(id);
				orig = customBean.getOriginalPackage(pack);
				owner = customBean.getOwner(pack);
			} catch (Exception e) {
				pack = new CustomPackageDTO();
			}
		}
		
	}

	public CustomPackageDTO getPack() {
		return pack;
	}
	
	public PackageDTO getOrig() {
		return orig;
	}
	
	public List<ComponentDTO> getAllComponents() {
		return customBean.getAllComponentsDTO(pack);
	}
	
	public Long getTotalPrice() {
		return customBean.getTotalPrice(pack);
	}
	
	public String formatDate(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(date);
	}
	
	public String getDepartureDate() {
		return formatDate(customBean.getDepartureDate(pack));
	}
	
	public String getReturnDate() {
		return formatDate(customBean.getReturnDate(pack));
	}
	
	public int getActualDays() {
		return customBean.getActualDays(pack);
	}

}
