package it.polimi.travelDream.web.beans.pkg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.travelDream.ejb.beans.PackageBeanInterface;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.PackageDTO;
import it.polimi.travelDream.web.beans.profile.DefaultsBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

@ManagedBean(name="addPackageBean")
@ViewScoped
public class AddPackageBean {
	
	@EJB
	private PackageBeanInterface packageBean;
	
	private PackageDTO pack;
	
	private String name;
	
	private Long days;
	
	private String description;
	
	private DualListModel<String> components;
	
	public AddPackageBean() {
		pack = new PackageDTO();
		
		List<String> source = new ArrayList<String>();
		List<String> target = new ArrayList<String>();
		components = new DualListModel<String>(source, target);
	}
	
	@PostConstruct
	public void init() {
		List<ComponentDTO> comps = packageBean.getAllComponentsDTO();
		List<String> source = new ArrayList<String>();
		for (ComponentDTO c : comps)
			source.add(c.getName());
		
		components.setSource(source);
		days = new Long(1);
	}

	public PackageDTO getPack() {
		return pack;
	}

	public void setPack(PackageDTO pack) {
		this.pack = pack;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DualListModel<String> getComponents() {
		return components;
	}

	public void setComponents(DualListModel<String> components) {
		this.components = components;
	}
	
	public String register() {
		List<String> comps = components.getTarget();
		pack.setComponents(comps);
		pack.setDays(days);
		
		packageBean.save(pack);
		
		
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect(DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true");
		} catch (IOException e) { }
		
		return DefaultsBean.BASE_PATH + "index.xhtml?faces-redirect=true";
	}

}
