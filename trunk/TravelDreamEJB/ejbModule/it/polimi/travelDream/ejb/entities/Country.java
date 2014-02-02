package it.polimi.travelDream.ejb.entities;

import it.polimi.travelDream.ejb.dtos.CountryDTO;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: CountryEntity
 *
 */
@Entity
@Table(name="Nomcountry")
@NamedQueries({
	@NamedQuery(name=Country.FIND_ALL,
				query="SELECT u FROM Country u ORDER BY u.name ASC")
})
public class Country implements Serializable {
	
	public static final String FIND_ALL = "Country.findAll";

	@Id
	private String name;
	private static final long serialVersionUID = 1L;

	public Country() {
		super();
	}
	public Country(CountryDTO countryDTO) {
		this();
		this.name = countryDTO.getName();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}
