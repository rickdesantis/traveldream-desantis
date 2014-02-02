package it.polimi.travelDream.ejb.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.PackageDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.travelDream.ejb.entities.Component;
import it.polimi.travelDream.ejb.entities.Package;
import it.polimi.travelDream.ejb.entities.User;

/**
 * Session Bean implementation class PackageBean
 */
@Stateless
@LocalBean
public class PackageBean implements PackageBeanInterface {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;

	@Override
	@RolesAllowed({"employee"})
	public void save(PackageDTO pkg) {
		Package pack = new Package(pkg);
		em.persist(pack);
	}

	@Override
	@RolesAllowed({"employee"})
	public void update(PackageDTO pkg) {
		Package pack = new Package(pkg);
		em.merge(pack);
	}
	
	/*
	private Package fix(Package pack) {
		List<Component> comps = pack.getComponents();
		List<Component> c = new ArrayList<Component>();
		
		for (Component tmp : comps) {
			tmp = em.find(Component.class, tmp.getName());
			c.add(tmp);
		}
		pack.setComponents(c);
		
		return pack;
	}
	*/
	
	public Package find(String name) {
		return em.find(Package.class, name);
	}
	
	public static PackageDTO convertToDTO(Package pack) {
		PackageDTO dto = new PackageDTO();
		dto.setDays(pack.getDays());
		dto.setDescription(pack.getDescription());
		dto.setName(pack.getName());
		
		ArrayList<String> dtos = new ArrayList<String>();
		List<Component> comps = pack.getComponents();
		for (Component c : comps) 
			dtos.add(c.getName());
		
		dto.setComponents(dtos);
		
		return dto;
	}

	@Override
	public PackageDTO getPackageDTO(String name) {
		return convertToDTO(find(name));
	}

	@Override
	@RolesAllowed({"employee"})
	public void remove(PackageDTO pkg) {
		remove(pkg.getName());
	}
	
	@Override
	@RolesAllowed({"employee"})
	public void remove(String pkg) {
		Package pack = find(pkg);
		
		em.remove(pack);
	}
	
	public List<Package> getAllPackages() {
		return em.createNamedQuery(Package.FIND_ALL, Package.class)
                .getResultList();
	}

	@Override
	public List<PackageDTO> getAllPackagesDTO() {
		ArrayList<PackageDTO> dtos = new ArrayList<PackageDTO>();
		List<Package> packs = getAllPackages();
		
		for (Package p : packs) {
			dtos.add(convertToDTO(p));
		}
		
		return dtos;
	}
	
	public List<Component> getAllComponents(PackageDTO pkg) {
		return find(pkg.getName()).getComponents();
	}

	@Override
	public List<ComponentDTO> getAllComponentsDTO(PackageDTO pkg) {
		ArrayList<ComponentDTO> dtos = new ArrayList<ComponentDTO>();
		
		List<Component> comps = getAllComponents(pkg);
		
		for (Component c : comps) {
			dtos.add(ComponentBean.convertToDTO(c));
		}
		
		return dtos;
	}
	
	@Override
	public List<ComponentDTO> getAllComponentsDTO() {
		ArrayList<ComponentDTO> dtos = new ArrayList<ComponentDTO>();
		List<Component> comps = em.createNamedQuery(Component.FIND_ALL, Component.class).getResultList();

		for (Component c : comps) {
			dtos.add(ComponentBean.convertToDTO(c));
		}

		return dtos;
	}

	@Override
	public Long getTotalPrice(PackageDTO pkg) {
		List<Component> comps = getAllComponents(pkg);
		
		Long sum = new Long(0);
		
		for (Component c : comps) {
			Long duration = new Long(1);
			
			Map<String, Object> defaults = ComponentBean.Json.parseComponent(c.getDefaults());
			for (String s : defaults.keySet()) {
				if (s.toLowerCase().indexOf("nights") > -1 || s.toLowerCase().indexOf("duration") > -1 || s.toLowerCase().indexOf("days") > -1) {
					duration = (Long) defaults.get(s);
				}
			}
			
			sum += c.getPrice() * duration;
		}
		
		return sum;
	}

	@Override
	public UserDTO getUserDTO(String email) {
		User u = em.find(User.class, email);
		return UserBean.convertToDTO(u);
	}

}
