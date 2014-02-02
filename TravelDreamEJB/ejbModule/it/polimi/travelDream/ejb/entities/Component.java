package it.polimi.travelDream.ejb.entities;

import it.polimi.travelDream.ejb.dtos.ComponentDTO;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: ComponentEntity
 *
 */
@Entity
@Table(name="Components")
@NamedQueries({
	@NamedQuery(name=Component.FIND_ALL,
				query="SELECT u FROM Component u ORDER BY u.name ASC")
})
public class Component implements Serializable {
	
	public static final String FIND_ALL = "Component.findAll";

	@Id
	private String name;
	private String description;
	private String defaults;
	@NotNull
	@ManyToOne
	@JoinColumn(name="TYPECOMPONENT")
	private NomComponent typeComponent;
	@NotNull
	@ManyToOne
	@JoinColumn(name="CITY")
	private City city;
	@NotNull
	private Long price;
	private static final long serialVersionUID = 1L;
	
	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="components")
	private List<Package> packages;

	public Component() {
		super();
	}
	public Component(ComponentDTO componentDTO) {
		this();
		this.name = componentDTO.getName();
		this.description = componentDTO.getDescription();
		this.defaults = componentDTO.getDefaults();
		this.typeComponent = new NomComponent();
		this.typeComponent.setName(componentDTO.getType());
		this.city = new City();
		this.city.setName(componentDTO.getCity());
		this.price = componentDTO.getPrice();
		
		this.packages = new ArrayList<Package>();
		List<String> dtos = componentDTO.getPackages();
		for (String p : dtos) {
			Package pack = new Package();
			pack.setName(p);
			this.packages.add(pack);
		}
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}   
	public String getDefaults() {
		return this.defaults;
	}

	public void setDefaults(String defaults) {
		this.defaults = defaults;
	}   
	public NomComponent getType() {
		return this.typeComponent;
	}

	public void setType(NomComponent type) {
		this.typeComponent = type;
	}
	
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	
	public List<Package> getPackages() {
		return packages;
	}
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
   
}
