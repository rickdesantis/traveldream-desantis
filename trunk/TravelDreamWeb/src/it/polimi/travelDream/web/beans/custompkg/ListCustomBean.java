package it.polimi.travelDream.web.beans.custompkg;

import it.polimi.travelDream.ejb.beans.CustomBeanInterface;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.CustomPackageDTO;
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

@ManagedBean(name="listCustomBean")
@RequestScoped
public class ListCustomBean {
	
	@EJB
	private CustomBeanInterface customBean;
	
	private List<CustomPackageDTO> packs;
	
	private String criteria;
	
	private UserDTO user;
	
	public UserDTO getUser() {
		return user;
	}

	@PostConstruct
	public void init() {
		criteria = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("criteria");
		if (criteria == null || criteria.length() == 0) {
			criteria = "";
		}
		
		Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (p != null)
			user = customBean.getUserDTO(p.getName());
		
		if (user != null) {
			packs = customBean.getAllPackagesDTO(user.getEmail());
		} else {
			packs = new ArrayList<CustomPackageDTO>();
		}
		
		
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
			   .getExternalContext().redirect(DefaultsBean.CUSTOMERS + "listcustompkg.xhtml?criteria=" + criteria + "&user=" + user + "&faces-redirect=true");
		} catch (IOException e) { }
		
		return DefaultsBean.CUSTOMERS + "listcustompkg.xhtml?criteria=" + criteria + "&user=" + user + "&faces-redirect=true";
	}

	public List<CustomPackageDTO> getAllPackages() {
		return packs;
	}
	
	public List<Map.Entry<String, List<String>>> getPrintablePackages() {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		
		for (CustomPackageDTO p : packs) {
			boolean found = false;
			
			found = (p.getOrigPackage().toLowerCase().indexOf(criteria.toLowerCase()) > -1);
			
			List<ComponentDTO> comps = customBean.getAllComponentsDTO(p);
			
			ArrayList<String> s = new ArrayList<String>();
			
			for (ComponentDTO c : comps) {
				s.add(c.getName());
				found = found || (c.getName().toLowerCase().indexOf(criteria.toLowerCase()) > -1);
			}
			
			if (found) {
				map.put(p.getOrigPackage(), s);
			}
			
		}
		
		return new ArrayList<Map.Entry<String, List<String>>>(map.entrySet());
	}
	
	public List<PrintablePackage> getPrintablePackagesBis() {
		ArrayList<PrintablePackage> list = new ArrayList<PrintablePackage>();
		
		for (CustomPackageDTO p : packs) {
			boolean found = false;
			
			found = (p.getOrigPackage().toLowerCase().indexOf(criteria.toLowerCase()) > -1);
			
			List<ComponentDTO> comps = customBean.getAllComponentsDTO(p);
			
			ArrayList<String> s = new ArrayList<String>();
			
			for (ComponentDTO c : comps) {
				s.add(c.getName());
				found = found || (c.getName().toLowerCase().indexOf(criteria.toLowerCase()) > -1);
			}
			
			if (found) {
				PrintablePackage tmp = new PrintablePackage(p.getId().toString(), p.getOrigPackage(), s);
				list.add(tmp);
			}
			
		}
		
		return list;
	}
	
	public CustomPackageDTO getCustomPackageDTO(String id) {
		
		
		return customBean.getCustomPackageDTO(id);
	}
	
	public class PrintablePackage {
		String id;
		String name;
		List<String> components;
		
		public PrintablePackage() {}
		
		public PrintablePackage(String id, String name, List<String> components) {
			this.id = id;
			this.name = name;
			this.components = components;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<String> getComponents() {
			return components;
		}
		public void setComponents(List<String> components) {
			this.components = components;
		}
		
	}
	
}
