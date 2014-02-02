package it.polimi.travelDream.ejb.entities;

import it.polimi.travelDream.ejb.dtos.CityDTO;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: CityEntity
 *
 */
@Entity
@Table(name="Nomcity")
@NamedQueries({
	@NamedQuery(name=City.FIND_ALL,
				query="SELECT u FROM City u ORDER BY u.country ASC"),
	@NamedQuery(name=City.FIND_BY_COUNTRY,
				query="SELECT u FROM City u WHERE u.country = :country ORDER BY u.name ASC")
})
public class City implements Serializable {
	
	public static final String FIND_ALL = "City.findAll";
	public static final String FIND_BY_COUNTRY = "City.findByCountry";

	@Id
	private String name;
	@NotNull
	@ManyToOne
	@JoinColumn(name="COUNTRY")
	private Country country;
	private static final long serialVersionUID = 1L;

	public City() {
		super();
	}
	public City(CityDTO cityDTO) {
		this();
		this.name = cityDTO.getName();
		this.country = new Country();
		this.country.setName(cityDTO.getCountry());
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Country getCountry() {
		return this.country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
   
}
