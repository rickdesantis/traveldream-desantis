package it.polimi.travelDream.web.beans.pkg;

import it.polimi.travelDream.ejb.beans.PackageBeanInterface;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.PackageDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="listPackagesBean")
@RequestScoped
public class ListPackagesBean {
	
	@EJB
	private PackageBeanInterface packageBean;
	
	private List<PackageDTO> packs;
	
	private String criteria;
	
	private UserDTO user;
	
	public UserDTO getUser() {
		return user;
	}
	
	public boolean userLogged() {
		return (user != null);
	}
	
	@PostConstruct
	public void init() {
		criteria = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("criteria");
		if (criteria == null || criteria.length() == 0) {
			criteria = "";
		}
		
		Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (p != null)
			user = packageBean.getUserDTO(p.getName());
		
		packs = packageBean.getAllPackagesDTO();
	}
	
	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	
	public String getUpdate() {
		if (criteria == null || criteria.length() == 0) {
			criteria = "";
		}
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect(DefaultsBean.BASE_PATH + "listpackage.xhtml?criteria=" + criteria + "&faces-redirect=true");
		} catch (IOException e) { }
		
		return DefaultsBean.BASE_PATH + "listpackage.xhtml?criteria=" + criteria + "&faces-redirect=true";
	}

	public List<PackageDTO> getAllPackages() {
		return packs;
	}
	
	
	public List<Map.Entry<String, List<String>>> getPrintablePackages() {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		
		for (PackageDTO p : packs) {
			boolean found = false;
			
			found = (p.getName().toLowerCase().indexOf(criteria.toLowerCase()) > -1);
			
			List<ComponentDTO> comps = packageBean.getAllComponentsDTO(p);
			
			ArrayList<String> s = new ArrayList<String>();
			
			for (ComponentDTO c : comps) {
				s.add(c.getName());
				found = found || (c.getName().toLowerCase().indexOf(criteria.toLowerCase()) > -1);
			}
			
			if (found) {
				map.put(p.getName(), s);
			}
			
		}
		
		return new ArrayList<Map.Entry<String, List<String>>>(map.entrySet());
	}
	
}
