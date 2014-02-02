package it.polimi.travelDream.ejb.entities;

import it.polimi.travelDream.ejb.dtos.NomComponentDTO;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: NomComponentEntity
 *
 */
@Entity
@Table(name="Nomcomponent")
@NamedQueries({
	@NamedQuery(name=NomComponent.FIND_ALL,
				query="SELECT u FROM NomComponent u ORDER BY u.name ASC")
})
public class NomComponent implements Serializable {

	public static final String FIND_ALL = "NomComponent.findAll";
	   
	@Id
	private String name;
	private String parameters;
	private static final long serialVersionUID = 1L;

	public NomComponent() {
		super();
	}
	public NomComponent(NomComponentDTO nomComponentDTO) {
		this();
		this.name = nomComponentDTO.getName();
		this.parameters = nomComponentDTO.getParameters();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getParameters() {
		return this.parameters;
	}
	
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
   
}
