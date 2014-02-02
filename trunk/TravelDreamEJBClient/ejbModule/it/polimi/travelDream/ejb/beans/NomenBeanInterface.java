package it.polimi.travelDream.ejb.beans;

import java.util.List;

import it.polimi.travelDream.ejb.dtos.CityDTO;
import it.polimi.travelDream.ejb.dtos.CountryDTO;
import it.polimi.travelDream.ejb.dtos.NomComponentDTO;
import it.polimi.travelDream.ejb.dtos.RoleDTO;

import javax.ejb.Local;

@Local
public interface NomenBeanInterface {
	
	public CityDTO getCity(String name);
	
	public List<CityDTO> getAllCities();
	
	public void save(CityDTO city);
	
	public CountryDTO getCountry(String name);
	
	public List<CountryDTO> getAllCountries();
	
	public void save(CountryDTO country);
	
	public NomComponentDTO getTypeComponent(String name);
	
	public List<NomComponentDTO> getAllTypesComponent();
	
	public RoleDTO getRole(String name);
	
	public List<RoleDTO> getAllRoles();

}
