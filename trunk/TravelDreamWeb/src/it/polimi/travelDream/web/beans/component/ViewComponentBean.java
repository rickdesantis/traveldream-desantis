package it.polimi.travelDream.web.beans.component;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import it.polimi.travelDream.ejb.beans.ComponentBeanInterface;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="viewComponentBean")
@RequestScoped
public class ViewComponentBean {
	
	@EJB
	private ComponentBeanInterface componentBean;
	
	private ComponentDTO component;
	
	private List<Map.Entry<Date, Date>> dates;
	
	private List<Map.Entry<String, ?>> defaults;
	
	private UserDTO user;
	
	public UserDTO getUser() {
		return user;
	}
	
	@PostConstruct
	public void init() {
		try {
			String name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("compName");
			component = componentBean.getComponentDTO(name);
		} catch (Exception e) { component = new ComponentDTO(); }
		
		Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (p != null)
			user = componentBean.getUserDTO(p.getName());
		
		dates = componentBean.getDates(component);
		defaults = componentBean.getDefaults(component);
	}

	public ComponentDTO getComponent() {
		return component;
	}
	
	public List<Map.Entry<String, ?>> getDefaults() {
		return defaults;
	}
	
	public List<Map.Entry<Date, Date>> getDates() {
		return dates;
	}
	
	public boolean getHasDates() {
		return (dates.size() > 0);
	}
	
	public String formatTime(Date date) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(date);
	}

}
