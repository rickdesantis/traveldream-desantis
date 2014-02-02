package it.polimi.travelDream.ejb.entities;

import it.polimi.travelDream.ejb.dtos.CustomPackageDTO;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: CustomPackageEntity
 *
 */
@Entity
@Table(name="CustomPackages")
@NamedQueries({
	@NamedQuery(name=CustomPackage.FIND_ALL,
				query="SELECT u FROM CustomPackage u ORDER BY u.id ASC"),
	@NamedQuery(name=CustomPackage.FIND_BY_USER,
				query="SELECT u FROM CustomPackage u WHERE u.user = :user ORDER BY u.id ASC")
})
public class CustomPackage implements Serializable {

	public static final String FIND_ALL = "CustomPackage.findAll";
	public static final String FIND_BY_USER = "CustomPackage.findByUser";
	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long days;
	@Lob
	@Column( length = 100000 )
	private String configuration;
	@NotNull
	@ManyToOne
	@JoinColumn(name="USER")
	private User user;
	@NotNull
	@ManyToOne
	@JoinColumn(name="PACKAGE")
	private Package origPackage;
	private static final long serialVersionUID = 1L;

	public CustomPackage() {
		super();
	}
	public CustomPackage(CustomPackageDTO customPackageDTO) {
		this();
		this.id = customPackageDTO.getId();
		this.days = customPackageDTO.getDays();
		this.configuration = customPackageDTO.getConfiguration();
		this.user = new User();
		this.user.setName(customPackageDTO.getUser());
		this.origPackage = new Package();
		this.origPackage.setName(customPackageDTO.getOrigPackage());
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public Long getDays() {
		return this.days;
	}

	public void setDays(Long days) {
		this.days = days;
	}   
	public String getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}   
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Package getOrigPackage() {
		return this.origPackage;
	}

	public void setOrigPackage(Package origPackage) {
		this.origPackage = origPackage;
	}
   
}
