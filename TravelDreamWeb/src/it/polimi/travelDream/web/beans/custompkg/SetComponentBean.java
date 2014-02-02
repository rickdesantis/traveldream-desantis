package it.polimi.travelDream.web.beans.custompkg;

import it.polimi.travelDream.ejb.beans.CustomBeanInterface;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.CustomPackageDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="setComponentBean")
@ViewScoped
public class SetComponentBean {
	
	@EJB
	private CustomBeanInterface customBean;
	
	private CustomPackageDTO pack;
	
	private ComponentDTO component;
	
	private UserDTO user;
	
	private UserDTO owner;
	
	private List<Map.Entry<Date, Date>> dates;
	
	private List<Map.Entry<String, ?>> defaults;
	
	private Map<String, Object> configuration;
	
	private String time;
	
	private String date;
	
	private int days;

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<Map.Entry<String, ?>> getDefaults() {
		return defaults;
	}
	
	public List<Map.Entry<String, ?>> getFinalDefaults() {
		ArrayList<Map.Entry<String, ?>> map = new ArrayList<Map.Entry<String, ?>>();
		for (Map.Entry<String, ?> e : defaults) {
			if (!e.getKey().trim().equalsIgnoreCase("nights") && !e.getKey().trim().equalsIgnoreCase("days") && !e.getKey().trim().equalsIgnoreCase("duration"))
				map.add(e);
		}
		
		return map;
	}
	
	public List<Map.Entry<Date, Date>> getDates() {
		return dates;
	}
	
	public boolean getHasDates() {
		return (dates.size() > 0);
	}
	
	public boolean getHasDuration() {
		return days > -1;
	}
	
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
	
	private static final Logger log;
	
	static {
		log = Logger.getLogger("DEBUG");
	}

	@PostConstruct
	public void init() {
		try {
			String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("packageId");
			String comp = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("compName");
			pack = customBean.getCustomPackageDTO(id);
			component = customBean.getComponentDTO(comp);
			owner = customBean.getOwner(pack);
			date = formatDate(customBean.getDate(pack, component));
		} catch (Exception e) {
			log.log(Level.SEVERE, "L'errore e': ", e);
		}
		
		Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (p != null)
			user = customBean.getUserDTO(p.getName());
		
		dates = customBean.getDates(component);
		defaults = customBean.getDefaults(component);
		
		configuration = new HashMap<String, Object>();
		
		Map<String, ?> confs = customBean.getConfiguration(pack).get(component.getName());
		if (confs != null) {
			
			days = -1;
			
			for (String e : confs.keySet()) {
				if (e.trim().equalsIgnoreCase("nights") || e.trim().equalsIgnoreCase("days") || e.trim().equalsIgnoreCase("duration")) {
					days = ((Long) confs.get(e)).intValue();
					configuration.put(e, confs.get(e));
				}
				else if (e.trim().equalsIgnoreCase("time")) {
					time = formatFullDate((Date)confs.get(e));
					configuration.put("time", time);
				}

			}
			
		}
			
	}

	public ComponentDTO getComponent() {
		return component;
	}
	
	public CustomPackageDTO getPackage() {
		return pack;
	}
	
	public Long getPrice() {
		return component.getPrice();
	}
	
	public String formatDate(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(date);
	}
	
	public String formatFullDate(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	
	public Date parseDate(String s) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(s);
		} catch (Exception e) {
			try {
				return df2.parse(s);
			} catch (Exception e2) {
				return new Date();
			}
		}
	}
	
	public String formatTime(Date date) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(date);
	}
	
	public String update() {
		if (configuration.containsKey("time"))
			configuration.put("time", parseDate(time));
		if (configuration.containsKey("days"))
			configuration.put("days", new Long(days));
		else if (configuration.containsKey("duration"))
			configuration.put("duration", new Long(days));
		else if (configuration.containsKey("nights"))
			configuration.put("nights", new Long(days));
		
		configuration.put("day", parseDate(date));
		
		customBean.update(pack, component, configuration);
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect(DefaultsBean.CUSTOMERS + "viewcustompkg.xhtml?packageId=" + pack.getId() + "&email=" + user.getEmail() + "&faces-redirect=true");
			return "";
		} catch (IOException e) { }
		
		return DefaultsBean.CUSTOMERS + "viewcustompkg.xhtml?packageId=" + pack.getId() + "&amp;email=" + user.getEmail() + "&amp;faces-redirect=true";
	}
	
}
