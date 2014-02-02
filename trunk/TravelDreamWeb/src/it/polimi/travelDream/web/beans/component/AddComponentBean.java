package it.polimi.travelDream.web.beans.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.travelDream.ejb.beans.ComponentBeanInterface;
import it.polimi.travelDream.ejb.dtos.CityDTO;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.CountryDTO;
import it.polimi.travelDream.ejb.dtos.NomComponentDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="addComponentBean")
@RequestScoped
public class AddComponentBean {
	
	@EJB
	private ComponentBeanInterface componentBean;
	
	private ComponentDTO component;
	
	private String type;
	
	private String city;
	
	private String country;
	
	private String price;
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	private List<Map.Entry<String, Object>> defaults;
	
	@PostConstruct
	public void init() {
		List<NomComponentDTO> types = getTypes();
		type = types.get(0).getName();
		// type = "Car Rental";
		defaults = componentBean.getParameters(type);
	}
	
	public List<Map.Entry<String, Object>> getDefaults() {
		defaults = componentBean.getParameters(type);
		return defaults;
	}
	
	public boolean hasDefaults() {
		return defaults.size() > 0;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public List<NomComponentDTO> getTypes() {
		return componentBean.getAllTypesComponent();
	}
	
	public List<CountryDTO> getCountries() {
		return componentBean.getAllCountries();
	}
	
	public List<CityDTO> getCities() {
		return componentBean.getAllCities(country);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public AddComponentBean() {
		component = new ComponentDTO();
		List<String> packs = new ArrayList<String>();
		component.setPackages(packs);
	}

	public ComponentDTO getComponent() {
		return component;
	}
	
	public void setComponent(ComponentDTO component) {
		this.component = component;
	}
	
	public String register() {
		registerCity();
		
		component.setType(type);
		component.setCity(city);
		component.setPrice(Long.parseLong(price));
		
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		int i = 0;
		for (String s : params.keySet()) {
			if (s.indexOf("el") > -1) {
				String val = params.get(s);
				map.put(this.defaults.get(i++).getKey(), val);
			}
		}
		
		String defaults = componentBean.generateDefaults(map, this.defaults);
		
		component.setDefaults(defaults);
		// component.setDefaults("{}");
		
		componentBean.save(component);
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect(DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true");
		} catch (IOException e) { }
		
		return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
	}
	
	public String registerCountry() {
		List<CountryDTO> countries = getCountries();
		for (CountryDTO c : countries) {
			if (c.getName().equalsIgnoreCase(this.country.trim()))
				return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
		}
		
		CountryDTO country = new CountryDTO();
		country.setName(this.country);
		componentBean.save(country);
		
		return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
	}
	
	public String registerCity() {
		registerCountry();
		
		List<CityDTO> cities = getCities();
		for (CityDTO c : cities) {
			if (c.getName().equalsIgnoreCase(this.city.trim()))
				return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
		}
		
		CityDTO city = new CityDTO();
		city.setName(this.city);
		city.setCountry(this.country);
		
		componentBean.save(city);
		
		return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
	}

}
