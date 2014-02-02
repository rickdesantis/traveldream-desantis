package it.polimi.travelDream.ejb.entities;

import it.polimi.travelDream.ejb.dtos.PackageDTO;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: PackageEntity
 *
 */
@Entity
@Table(name="Packages")
@NamedQueries({
	@NamedQuery(name=Package.FIND_ALL,
				query="SELECT u FROM Package u ORDER BY u.name ASC")
})
public class Package implements Serializable {
	
	public static final String FIND_ALL = "Package.findAll";

	   
	@Id
	private String name;
	@NotNull
	private Long days;
	private String description;
	private static final long serialVersionUID = 1L;
	
	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="ComponentsInPackage"
		, joinColumns={
			@JoinColumn(name="PACKAGE", referencedColumnName="NAME")
			}
		, inverseJoinColumns={
			@JoinColumn(name="COMPONENT", referencedColumnName="NAME")
			}
		)
    private List<Component> components;

	public Package() {
		super();
	}
	public Package(PackageDTO packageDTO) {
		this();
		this.name = packageDTO.getName();
		this.days = packageDTO.getDays();
		this.description = packageDTO.getDescription();
		
		this.components = new ArrayList<Component>();
		List<String> dtos = packageDTO.getComponents();
		for (String c : dtos) {
			Component comp = new Component();
			comp.setName(c);
			this.components.add(comp);
		}
	}
	  
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public Long getDays() {
		return this.days;
	}

	public void setDays(Long days) {
		this.days = days;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public List<Component> getComponents() {
		return components;
	}
	public void setComponents(List<Component> components) {
		this.components = components;
	}
   
}
