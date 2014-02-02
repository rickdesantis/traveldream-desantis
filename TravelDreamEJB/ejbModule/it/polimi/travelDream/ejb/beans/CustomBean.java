package it.polimi.travelDream.ejb.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.travelDream.ejb.beans.ComponentBean.Json;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.PackageDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.ejb.dtos.CustomPackageDTO;
import it.polimi.travelDream.ejb.entities.Component;
import it.polimi.travelDream.ejb.entities.CustomPackage;
import it.polimi.travelDream.ejb.entities.DateComponent;
import it.polimi.travelDream.ejb.entities.Package;
import it.polimi.travelDream.ejb.entities.User;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class CustomBean
 */
@Stateless
@LocalBean
public class CustomBean implements CustomBeanInterface {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;

	@Override
	@RolesAllowed({"customer"})
	public void save(CustomPackageDTO pkg) {
		CustomPackage c = new CustomPackage(pkg);
		em.persist(c);
	}

	@Override
	@RolesAllowed({"customer"})
	public void update(CustomPackageDTO pkg, ComponentDTO component, Map<String, Object> configuration) {
		CustomPackage c = find(pkg.getId());
		
		Map<String, Map<String, Object>> map = getConfiguration(c);
		map.put(component.getName(), configuration);
		
		c.setConfiguration( ComponentBean.Json.generateForAllComponents(map) );
		
		em.merge(c);
	}
	
	public CustomPackage find(String id) {
		CustomPackage c = find(Long.parseLong(id));
		return c;
	}
	
	public CustomPackage find(Long id) {
		return em.find(CustomPackage.class, id);
	}

	@Override
	@RolesAllowed({"customer"})
	public void remove(CustomPackageDTO pkg) {
		remove(pkg.getId().toString());
	}
	
	@Override
	@RolesAllowed({"customer"})
	public void remove(String pkg) {
		CustomPackage c = find(pkg);
		em.remove(c);
	}
	
	public static CustomPackageDTO convertToDTO(CustomPackage c) {
		CustomPackageDTO dto = new CustomPackageDTO();
		dto.setConfiguration(c.getConfiguration());
		dto.setDays(c.getDays());
		dto.setId(c.getId());
		dto.setOrigPackage(c.getOrigPackage().getName());
		dto.setUser(c.getUser().getName());
		return dto;
	}

	@Override
	public CustomPackageDTO getCustomPackageDTO(String id) {
		return convertToDTO(find(id));
	}
	
	@RolesAllowed({"employee"})
	public List<CustomPackage> getAllCustomPackages() {
		return em.createNamedQuery(CustomPackage.FIND_ALL, CustomPackage.class).getResultList();
	}

	@Override
	@RolesAllowed({"employee"})
	public List<CustomPackageDTO> getAllCustomPackagesDTO() {
		ArrayList<CustomPackageDTO> dtos = new ArrayList<CustomPackageDTO>();
		List<CustomPackage> packs = getAllCustomPackages();
		
		for (CustomPackage p : packs) {
			dtos.add(convertToDTO(p));
		}
		
		return dtos;
	}
	
	public List<Component> getAllComponents(CustomPackageDTO pkg) {
		if (pkg == null || pkg.getOrigPackage() == null)
			return new ArrayList<Component>();
		Package p = em.find(Package.class, pkg.getOrigPackage());
		return p.getComponents();
	}

	@Override
	public List<ComponentDTO> getAllComponentsDTO(CustomPackageDTO pkg) {
		List<ComponentDTO> dtos = new ArrayList<ComponentDTO>();
		
		List<Component> comps = getAllComponents(pkg);
		
		for (Component c : comps) {
			dtos.add(ComponentBean.convertToDTO(c));
		}
		
		return dtos;
	}

	@Override
	public Long getTotalPrice(CustomPackageDTO pkg) {
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
	public List<CustomPackageDTO> getAllPackagesDTO(String user) {
		List<CustomPackageDTO> dtos = new ArrayList<CustomPackageDTO>();
		
		if (user == null || user.length() == 0) {
			return dtos;
		}
		
		List<CustomPackage> packs = em.createNamedQuery(CustomPackage.FIND_BY_USER, CustomPackage.class)
				.setParameter("user", em.find(User.class, user))
				.getResultList();

		for (CustomPackage c : packs) {
			dtos.add(convertToDTO(c));
		}

		return dtos;
	}

	@Override
	public UserDTO getUserDTO(String email) {
		User u = em.find(User.class, email);
		return UserBean.convertToDTO(u);
	}

	@Override
	public PackageDTO getOriginalPackage(CustomPackageDTO pack) {
		CustomPackage c = find(pack.getId());
		return PackageBean.convertToDTO(c.getOrigPackage());
	}

	@Override
	public UserDTO getOwner(CustomPackageDTO pack) {
		CustomPackage c = find(pack.getId());
		return UserBean.convertToDTO(c.getUser());
	}
	
	@Override
	public Map<String, Map<String, Object>> getConfiguration(CustomPackageDTO pack) {
		CustomPackage c = find(pack.getId());
		
		return getConfiguration(c);
	}
	
	public Map<String, Map<String, Object>> getConfiguration(CustomPackage pack) {
		return ComponentBean.Json.parseAllComponents(pack.getConfiguration());
	}

	@Override
	public Date getDepartureDate(CustomPackageDTO pack) {
		Map<String, Map<String, Object>> conf = getConfiguration(pack);
		
		Date d = null;
		
		for (String s : conf.keySet()) {
			if (conf.get(s).containsKey("day")) {
				
				Object o = conf.get(s).get("day");
				if (o.getClass().equals(Date.class)) {
					if (d == null || ((Date)o).before(d) )
						d = (Date) o;
				}
				
			}
		}
		
		if (d == null)
			return new Date();
		return d;
	}
	
	@Override
	public Date getReturnDate(CustomPackageDTO pack) {
		Map<String, Map<String, Object>> conf = getConfiguration(pack);
		
		Date d = null;
		
		for (String s : conf.keySet()) {
			if (conf.get(s).containsKey("day")) {
				
				Object o = conf.get(s).get("day");
				if (o.getClass().equals(Date.class)) {
					if (d == null || ((Date)o).after(d) )
						d = (Date) o;
				}
				
			}
		}
		
		if (d == null)
			return new Date();
		return d;
	}
	
	@Override
	public Date getDate(CustomPackageDTO pack, ComponentDTO comp) {
		Map<String, Map<String, Object>> conf = getConfiguration(pack);
		
		Date d = null;
		
		String s = comp.getName();
		
		if (conf.containsKey(s)) {
			if (conf.get(s).containsKey("day")) {
				
				Object o = conf.get(s).get("day");
				if (o.getClass().equals(Date.class)) {
					d = (Date) o;
				}
				
			}
		}
		
		if (d == null)
			return getDepartureDate(pack);
		return d;
	}

	@Override
	public int getActualDays(CustomPackageDTO pack) {
		Calendar d = new GregorianCalendar();
		d.setTime(getDepartureDate(pack));
		Calendar r = new GregorianCalendar();
		r.setTime(getReturnDate(pack));
		
		if (d == null || r == null)
			return -1;
		
		int days = 0;
		
		while (d.before(r)) {
			d.add(Calendar.DAY_OF_MONTH, 1);
			days++;
		}
		
		return days;
	}

	@Override
	public ComponentDTO getComponentDTO(String comp) {
		Component c = em.find(Component.class, comp);
		return ComponentBean.convertToDTO(c);
	}

	@Override
	public List<Map.Entry<String, ?>> getDefaults(ComponentDTO component) {
		return getDefaults(em.find(Component.class, component.getName()));
	}
	
	public List<Map.Entry<String, ?>> getDefaults(Component component) {
		String defs = component.getDefaults();
		Map<String, Object> map = Json.parseComponent(defs);
		return new ArrayList<Map.Entry<String, ?>>(map.entrySet());
	}

	@Override
	public List<Map.Entry<Date, Date>> getDates(ComponentDTO component) {
		return getDates(em.find(Component.class, component.getName()));
	}
	
	public List<Map.Entry<Date, Date>> getDates(Component comp) {
		HashMap<Date, Date> map = new HashMap<Date, Date>();
		
		List<DateComponent> dates = em.createNamedQuery(DateComponent.FIND_BY_COMPONENT, DateComponent.class)
				.setParameter("component", comp)
				.getResultList();
		
		for (DateComponent d : dates) {
				map.put(d.getStart(), d.getEnd());
		}
		
		return new ArrayList<Map.Entry<Date, Date>>(map.entrySet());
	}

	@Override
	@RolesAllowed({"customer"})
	public CustomPackageDTO createCustomPackage(PackageDTO orig, UserDTO user) {
		CustomPackage c = new CustomPackage();
		
		Package p = em.find(Package.class, orig.getName());
		c.setDays(p.getDays());
		c.setOrigPackage(p);
		c.setUser(new User(user));
		
		List<Component> comps = p.getComponents();
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		
		int i = 0;
		
		for (Component comp : comps) {
			Map<String, Object> params = new HashMap<String, Object>();
			
			Date d = new Date();
	        Calendar g = new GregorianCalendar();
	        g.setTime(d);
	        g.add(Calendar.DAY_OF_MONTH, 10 + i++);
	        
	        params.put("day", g.getTime());
	        
	        List<Map.Entry<Date, Date>> dates = getDates(comp);
	        if (dates.size() > 0)
	        	params.put("time", dates.get(0).getKey());
	        
	        List<Map.Entry<String, ?>> defaults = getDefaults(comp);
	        for (Map.Entry<String, ?> el : defaults) {
	        	if (el.getKey().equalsIgnoreCase("nights"))
	        		params.put("nights", el.getValue());
	        	else if (el.getKey().equalsIgnoreCase("days"))
	        		params.put("days", el.getValue());
	        	else if (el.getKey().equalsIgnoreCase("duration"))
	        		params.put("duration", el.getValue());
	        }
	        
	        map.put(comp.getName(), params);
		}
		
		
		c.setConfiguration(ComponentBean.Json.generateForAllComponents(map));
		
		em.persist(c);
		em.flush();
		
		return convertToDTO(c);
	}

	@Override
	public PackageDTO getPackageDTO(String orig) {
		return PackageBean.convertToDTO(em.find(Package.class, orig));
	}

}
