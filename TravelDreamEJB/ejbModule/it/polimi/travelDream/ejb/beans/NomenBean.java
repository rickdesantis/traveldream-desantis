package it.polimi.travelDream.ejb.beans;

import it.polimi.travelDream.ejb.dtos.CityDTO;
import it.polimi.travelDream.ejb.dtos.CountryDTO;
import it.polimi.travelDream.ejb.dtos.NomComponentDTO;
import it.polimi.travelDream.ejb.dtos.RoleDTO;
import it.polimi.travelDream.ejb.entities.City;
import it.polimi.travelDream.ejb.entities.Country;
import it.polimi.travelDream.ejb.entities.NomComponent;
import it.polimi.travelDream.ejb.entities.Role;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class NomenBean
 */
@Stateless
@LocalBean
public class NomenBean implements NomenBeanInterface {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	@Override
	public CityDTO getCity(String name) {
		return convertToDTO(findCity(name));
	}
	
	public City findCity(String name) {
		return em.find(City.class, name);
	}
	
	@Override
	@RolesAllowed({"employee"})
	public void save(CityDTO city) {
		City c = new City(city);
		em.persist(c);
	}

	@Override
	public List<CityDTO> getAllCities() {
		List<City> cities = em.createNamedQuery(City.FIND_ALL, City.class).getResultList();

		List<CityDTO> dtos = new ArrayList<CityDTO>();

		for (City c : cities) {
			dtos.add(convertToDTO(c));
		}

		return dtos;
	}

	@Override
	public CountryDTO getCountry(String name) {
		return convertToDTO(findCountry(name));
	}
	
	public Country findCountry(String name) {
		return em.find(Country.class, name);
	}

	@Override
	public List<CountryDTO> getAllCountries() {
		List<Country> countries = em.createNamedQuery(Country.FIND_ALL, Country.class).getResultList();

		List<CountryDTO> dtos = new ArrayList<CountryDTO>();

		for (Country c : countries) {
			dtos.add(convertToDTO(c));
		}

		return dtos;
	}

	@Override
	@RolesAllowed({"employee"})
	public void save(CountryDTO country) {
		Country c = new Country(country);
		em.persist(c);
	}

	@Override
	public NomComponentDTO getTypeComponent(String name) {
		return convertToDTO(findNomComponent(name));
	}
	
	public NomComponent findNomComponent(String name) {
		return em.find(NomComponent.class, name);
	}

	@Override
	public List<NomComponentDTO> getAllTypesComponent() {
		List<NomComponent> types = em.createNamedQuery(NomComponent.FIND_ALL, NomComponent.class).getResultList();

		List<NomComponentDTO> dtos = new ArrayList<NomComponentDTO>();

		for (NomComponent t : types) {
			dtos.add(convertToDTO(t));
		}

		return dtos;
	}

	@Override
	public RoleDTO getRole(String name) {
		return convertToDTO(findRole(name));
	}
	
	public Role findRole(String name) {
		return em.find(Role.class, name);
	}

	@Override
	public List<RoleDTO> getAllRoles() {
		List<Role> roles = em.createNamedQuery(Role.FIND_ALL, Role.class).getResultList();

		List<RoleDTO> dtos = new ArrayList<RoleDTO>();

		for (Role r : roles) {
			dtos.add(convertToDTO(r));
		}

		return dtos;
	}

	public static NomComponentDTO convertToDTO(NomComponent type) {
		NomComponentDTO dto = new NomComponentDTO();
		dto.setName(type.getName());
		dto.setParameters(type.getParameters());
		return dto;
	}

	public static CityDTO convertToDTO(City city) {
		CityDTO dto = new CityDTO();
		dto.setCountry(city.getCountry().getName());
		dto.setName(city.getName());
		return dto;
	}

	public static CountryDTO convertToDTO(Country country) {
		CountryDTO dto = new CountryDTO();
		dto.setName(country.getName());
		return dto;
	}
	
	public static RoleDTO convertToDTO(Role role) {
		RoleDTO dto = new RoleDTO();
		dto.setName(role.getName());
		dto.setLevel(role.getLevel());
		return dto;
	}

}
