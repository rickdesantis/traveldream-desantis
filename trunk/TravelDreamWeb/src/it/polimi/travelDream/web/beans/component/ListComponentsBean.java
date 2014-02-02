package it.polimi.travelDream.web.beans.component;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import it.polimi.travelDream.ejb.beans.ComponentBeanInterface;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="listComponentsBean")
@RequestScoped
public class ListComponentsBean {
	
	@EJB
	private ComponentBeanInterface componentBean;
	
	private List<ComponentDTO> components;

	public List<ComponentDTO> getComponents() {
		return components;
	}
	
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
			user = componentBean.getUserDTO(p.getName());
		
		components = componentBean.getAllComponentsDTO();
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
			   .getExternalContext().redirect(DefaultsBean.EMPLOYEES + "listcomponents.xhtml?criteria=" + criteria + "&faces-redirect=true");
		} catch (IOException e) { }
		
		return DefaultsBean.EMPLOYEES + "listcomponents.xhtml?criteria=" + criteria + "&faces-redirect=true";
	}
	
	public List<String> getPrintableComponents() {
		ArrayList<String> list = new ArrayList<String>();
		
		for (ComponentDTO comp : components) {
			if (comp.getName().toLowerCase().indexOf(criteria.toLowerCase()) > -1) {
				list.add(comp.getName());
			}
			
		}
		
		return list;
	}

}
