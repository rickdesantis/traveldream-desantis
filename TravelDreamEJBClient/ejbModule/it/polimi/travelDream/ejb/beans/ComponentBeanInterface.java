package it.polimi.travelDream.ejb.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.polimi.travelDream.ejb.dtos.CityDTO;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.CountryDTO;
import it.polimi.travelDream.ejb.dtos.DateComponentDTO;
import it.polimi.travelDream.ejb.dtos.NomComponentDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import javax.ejb.Local;

@Local
public interface ComponentBeanInterface {
	
	public void save(ComponentDTO component);
	
	public void save(CountryDTO country);
	
	public void save(CityDTO country);
	
	public void update(ComponentDTO component);
	
	public void remove(ComponentDTO component);
	
	public ComponentDTO getComponentDTO(String name);
	
	public List<ComponentDTO> getAllComponentsDTO();
	
	public List<Map.Entry<String, ?>> getDefaults(ComponentDTO component);
	
	public List<Map.Entry<Date, Date>> getDates(ComponentDTO component);
	
	public List<NomComponentDTO> getAllTypesComponent();

	public List<CityDTO> getAllCities(String country);
	
	public List<CountryDTO> getAllCountries();

	public List<Entry<String, Object>> getParameters(String name);

	public UserDTO getUserDTO(String email);

	public String generateDefaults(Map<String, String> map, List<Map.Entry<String, Object>> defaults);

	public void remove(String name);

	public List<DateComponentDTO> getDatesDTO(ComponentDTO component);

	public void save(DateComponentDTO d);

	public void remove(DateComponentDTO date);

	
}
