package it.polimi.travelDream.ejb.entities;

import it.polimi.travelDream.ejb.dtos.RoleDTO;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity implementation class for Entity: RoleEntity
 *
 */
@Entity
@Table(name="Nomrole")
@NamedQueries({
	@NamedQuery(name=Role.FIND_ALL,
				query="SELECT u FROM Role u ORDER BY u.level ASC")
})
public class Role implements Serializable {
	
	public static final String FIND_ALL = "Role.findAll";

	@Id
	private String name;
	@NotEmpty
	private Long level;
	private static final long serialVersionUID = 1L;

	public Role() {
		super();
	}
	public Role(RoleDTO roleDTO) {
		this();
		this.name = roleDTO.getName();
		this.level = roleDTO.getLevel();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public Long getLevel() {
		return this.level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}
   
}
