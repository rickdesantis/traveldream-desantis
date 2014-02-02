package it.polimi.travelDream.ejb.entities;

import it.polimi.travelDream.ejb.dtos.DateComponentDTO;
import it.polimi.travelDream.ejb.entities.Component;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: DateComponent
 *
 */
@Entity
@Table(name="Datecomponent")
@NamedQueries({
	@NamedQuery(name=DateComponent.FIND_ALL,
				query="SELECT u FROM DateComponent u ORDER BY u.component ASC"),
	@NamedQuery(name=DateComponent.FIND_BY_COMPONENT,
				query="SELECT u FROM DateComponent u WHERE u.component = :component ORDER BY u.start ASC")
})
public class DateComponent implements Serializable {
	
	public static final String FIND_ALL = "DateComponent.findAll";
	public static final String FIND_BY_COMPONENT = "DateComponent.findByComponent";

	@NotNull
	@ManyToOne
	@JoinColumn(name="COMPONENT")
	private Component component;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private static final long serialVersionUID = 1L;

	public DateComponent() {
		super();
	}
	
	public DateComponent(DateComponentDTO dateComponentDTO) {
		this();
		this.setId(dateComponentDTO.getId());
		this.setStart(dateComponentDTO.getStart());
		this.setEnd(dateComponentDTO.getEnd());
		this.component = new Component();
		this.component.setName(dateComponentDTO.getComponent());
	}
	
	public Component getComponent() {
		return this.component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}
	
	public Date getStart() {
		return this.start;
	}

	public void setStart(Date start) {
		this.start = start;
	}   
	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
   
}
