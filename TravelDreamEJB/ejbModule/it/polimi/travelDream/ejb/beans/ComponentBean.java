package it.polimi.travelDream.ejb.beans;

import it.polimi.travelDream.ejb.dtos.CityDTO;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.CountryDTO;
import it.polimi.travelDream.ejb.dtos.DateComponentDTO;
import it.polimi.travelDream.ejb.dtos.NomComponentDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.ejb.entities.City;
import it.polimi.travelDream.ejb.entities.Component;
import it.polimi.travelDream.ejb.entities.Country;
import it.polimi.travelDream.ejb.entities.DateComponent;
import it.polimi.travelDream.ejb.entities.NomComponent;
import it.polimi.travelDream.ejb.entities.User;
import it.polimi.travelDream.ejb.entities.Package;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class ComponentBean
 */
@Stateless
@LocalBean
public class ComponentBean implements ComponentBeanInterface {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	@Override
	@RolesAllowed({"employee"})
	public void save(ComponentDTO component) {
		Component comp = new Component(component);
		em.persist(comp);
	}
	
	@Override
	@RolesAllowed({"employee"})
	public void save(CountryDTO country) {
		Country c = new Country(country);
		em.persist(c);
	}
	
	@Override
	@RolesAllowed({"employee"})
	public void save(CityDTO city) {
		City c = new City(city);
		em.persist(c);
	}
	
	@Override
	@RolesAllowed({"employee"})
	public void save(DateComponentDTO date) {
		DateComponent d = new DateComponent(date);
		em.persist(d);
	}

	@Override
	@RolesAllowed({"employee"})
	public void update(ComponentDTO component) {
		Component comp = new Component(component);
		em.merge(comp);
	}

	public Component find(String name) {
		return em.find(Component.class, name);
	}

	public List<Component> getAllComponents() {
		return em.createNamedQuery(Component.FIND_ALL, Component.class)
				.getResultList();
	}

	@Override
	@RolesAllowed({"employee"})
	public void remove(String name) {
		Component component = find(name);
		remove(component);
	}

	@RolesAllowed({"employee"})
	public void remove(Component component) {
		List<DateComponent> dates = getDatesComponent(component);
		for (DateComponent d : dates)
			em.remove(d);

		em.remove(component);
	}

	@Override
	@RolesAllowed({"employee"})
	public void remove(ComponentDTO component) {
		remove(component.getName());
	}
	
	@Override
	@RolesAllowed({"employee"})
	public void remove(DateComponentDTO date) {
		DateComponent d = em.find(DateComponent.class, date.getId());
		em.remove(d);
	}

	public static ComponentDTO convertToDTO(Component component) {
		ComponentDTO dto = new ComponentDTO();
		dto.setCity(component.getCity().getName());
		dto.setDefaults(component.getDefaults());
		dto.setDescription(component.getDescription());
		dto.setName(component.getName());
		dto.setPrice(component.getPrice());
		dto.setType(component.getType().getName());
		
		ArrayList<String> dtos = new ArrayList<String>();
		List<Package> packs = component.getPackages();
		for (Package p : packs) 
			dtos.add(p.getName());
		
		dto.setPackages(dtos);
		
		return dto;
	}

	@Override
	public ComponentDTO getComponentDTO(String name) {
		return convertToDTO(find(name));
	}

	@Override
	@RolesAllowed({"employee"})
	public List<ComponentDTO> getAllComponentsDTO() {
		ArrayList<ComponentDTO> dtos = new ArrayList<ComponentDTO>();
		List<Component> comps = getAllComponents();

		for (Component c : comps) {
			dtos.add(convertToDTO(c));
		}

		return dtos;
	}

	@Override
	public List<Map.Entry<String, ?>> getDefaults(ComponentDTO component) {
		String defs = component.getDefaults();
		Map<String, Object> map = Json.parseComponent(defs);
		return new ArrayList<Map.Entry<String, ?>>(map.entrySet());
	}

	@Override
	public List<Map.Entry<Date, Date>> getDates(ComponentDTO component) {
		HashMap<Date, Date> map = new HashMap<Date, Date>();
		
		Component comp = find(component.getName());
		
		List<DateComponent> dates = getDatesComponent(comp);
		
		for (DateComponent d : dates) {
				map.put(d.getStart(), d.getEnd());
		}
		
		return new ArrayList<Map.Entry<Date, Date>>(map.entrySet());
	}
	
	@Override
	public List<DateComponentDTO> getDatesDTO(ComponentDTO component) {
		List<DateComponentDTO> list = new ArrayList<DateComponentDTO>();
		
		Component comp = find(component.getName());
		
		List<DateComponent> dates = getDatesComponent(comp);
		for (DateComponent d : dates) {
				list.add(convertToDTO(d));
		}
		
		return list;
	}
	
	public List<DateComponent> getDatesComponent(Component component) {
		return em.createNamedQuery(DateComponent.FIND_BY_COMPONENT, DateComponent.class)
				.setParameter("component", component)
				.getResultList();
	}
	
	public static DateComponentDTO convertToDTO(DateComponent d) {
		DateComponentDTO dto = new DateComponentDTO();
		dto.setComponent(d.getComponent().getName());
		dto.setEnd(d.getEnd());
		dto.setStart(d.getStart());
		dto.setId(d.getId());
		return dto;
	}

	@Override
	public List<NomComponentDTO> getAllTypesComponent() {
		List<NomComponent> types = em.createNamedQuery(NomComponent.FIND_ALL, NomComponent.class).getResultList();
		
		List<NomComponentDTO> dtos = new ArrayList<NomComponentDTO>();
		
		for (NomComponent t : types) {
			dtos.add(NomenBean.convertToDTO(t));
		}
		
		return dtos;
	}
	
	@Override
	public List<CountryDTO> getAllCountries() {
		List<Country> countries = em.createNamedQuery(Country.FIND_ALL, Country.class).getResultList();

		List<CountryDTO> dtos = new ArrayList<CountryDTO>();

		for (Country c : countries) {
			dtos.add(NomenBean.convertToDTO(c));
		}

		return dtos;
	}
	
	@Override
	public List<CityDTO> getAllCities(String country) {
		List<CityDTO> dtos = new ArrayList<CityDTO>();
		
		if (country == null || country.length() == 0) {
			return dtos;
		}
		
		List<City> cities = em.createNamedQuery(City.FIND_BY_COUNTRY, City.class)
				.setParameter("country", em.find(Country.class, country))
				.getResultList();

		for (City c : cities) {
			dtos.add(NomenBean.convertToDTO(c));
		}

		return dtos;
	}
	
	@Override
	public List<Map.Entry<String, Object>> getParameters(String name) {
		if (name == null || name.length() == 0) {
			return new ArrayList<Map.Entry<String, Object>>();
		}
		
		NomComponent type = em.find(NomComponent.class, name);
		
		Map<String, Object> map = ComponentBean.Json.parseComponent(type.getParameters());
		
		if (map == null)
			map = new HashMap<String, Object>();
		
		return new ArrayList<Map.Entry<String, Object>>(map.entrySet());
	}

	@Override
	public UserDTO getUserDTO(String email) {
		User u = em.find(User.class, email);
		return UserBean.convertToDTO(u);
	}
	
	@Override
	public String generateDefaults(Map<String, String> map, List<Map.Entry<String, Object>> defaults) {
        Map<String, Object> vars = new HashMap<String, Object>();
        Map<String, Object> defs = new HashMap<String, Object>();
		
		for (Map.Entry<String, Object> s : defaults) {
			defs.put(s.getKey(), s.getValue());
		}

        for (String el: map.keySet()) {
            if (defs.containsKey(el)) {
                Object o = defs.get(el), val = null;
                Class<?> c = o.getClass();
                
                try {
                
	                if (c.getName().equals("java.util.Date"))
	                    val = Json.parseDate(map.get(el));
	                else
	            		val = c.getConstructor(String.class).newInstance(map.get(el));
	                
	                vars.put(el, val);
                
                } catch (Exception e) { }
            }
        }

        return Json.generateForComponent(vars);
    }
	
	
	//////////////////////////
	
	public static class Json {
		
		public static List<List<String>> getElementsFromJson(String s) {
	        ArrayList<String> bodies = new ArrayList<String>();
	        ArrayList<String> names = new ArrayList<String>();

	        int el = 0;

	        while (s.length() > 0 && s.indexOf('}') > -1) {
	            int end = s.indexOf('}');
	            int begin = s.lastIndexOf('{', end);

	            String body = s.substring(begin, end + 1);

	            String name = "ROOT";

	            try {
	                int nameEnd = s.lastIndexOf('"', begin);
	                int nameBegin = s.lastIndexOf('"', nameEnd - 1);

	                name = s.substring(nameBegin + 1, nameEnd);
	            } catch (Exception e) { }

	            bodies.add(body);
	            names.add(name);

	            s = s.substring(0, begin) + "#EL" + el++ + "#"+  s.substring(end + 1);
	        }

	        ArrayList<List<String>> res = new ArrayList<List<String>>();
	        res.add(names);
	        res.add(bodies);
	        return res;
	    }

	    public static Map<String, Map<String, Object>> parseAllComponents(String s) {
	        List<List<String>> res = getElementsFromJson(s);

	        HashMap<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();

	        List<String> names = res.get(0);
	        List<String> bodies = res.get(1);

	        int i = bodies.size() - 1;
	        String tmp = bodies.get(i);

	        int x;
	        while ((x = tmp.indexOf("#EL")) > -1) {
	            int y = tmp.indexOf('#', x + 1);

	            int e = Integer.parseInt(tmp.substring(x + 3, y));
	            String name = names.get(e);
	            String body = bodies.get(e);

	            tmp = tmp.substring(0, x) + tmp.substring(y + 1);

	            map.put(name, resolveComponent(body, res));
	        }

	        return map;

	    }

	    public static Map<String, Object> parseComponent(String s) {
	        List<List<String>> res = getElementsFromJson(s);
	        List<String> bodies = res.get(1);

	        int i = bodies.size() - 1;
	        String tmp = bodies.get(i);

	        return resolveComponent(tmp, res);
	    }

	    private static Map<String, Object> resolveComponent(String tmp, List<List<String>> res) {
	        List<String> names = res.get(0);
	        List<String> bodies = res.get(1);

	        HashMap<String, Object> map = new HashMap<String, Object>();

	        int x;
	        while ((x = tmp.indexOf("#EL")) > -1) {
	            int y = tmp.indexOf('#', x + 1);

	            int e = Integer.parseInt(tmp.substring(x + 3, y));
	            String name = names.get(e);
	            String body = bodies.get(e);

	            Object o = resolveObject(body);
	            map.put(name, o);

	            tmp = tmp.substring(0, x) + tmp.substring(y + 1);

	        }

	        return map;

	    }

	    private static Object resolveObject(String s) {
	        String tmp[] = s.split("\"");

	        try {
	            Class<?> c = Class.forName(tmp[3]);
	            Object o = null;

	            if (tmp[7].trim().length() > 0) {
	                if (c.getName().equals("java.util.Date"))
	                    o = parseDate(tmp[7]);
	                else
	                    o = c.getConstructor(String.class).newInstance(tmp[7]);
	            }
	            return o;
	        } catch (Exception e) { e.printStackTrace(); }

	        return null;
	    }

	    public static String generateForAllComponents(Map<String, Map<String, Object>> map) {
	        StringBuffer sb = new StringBuffer("{ ");
	        boolean start = true;
	        for (String name : map.keySet()) {
	            if (!start)
	                sb.append(", ");
	            else
	                start = false;
	            sb.append("\"" + name + "\": " + generateForComponent(map.get(name)));
	        }
	        sb.append(" }");
	        return sb.toString();
	    }

	    public static String generateForComponent(Map<String, Object> map) {
	        StringBuffer sb = new StringBuffer("{ ");
	        boolean start = true;
	        for (String name : map.keySet()) {
	            if (!start)
	                sb.append(", ");
	            else
	                start = false;
	            sb.append("\"" + name + "\": " + generateForObject(map.get(name)));
	        }
	        sb.append(" }");
	        return sb.toString();
	    }

	    public static String generateForObject(Object o) {
	    	if (o.getClass().getName().equals("java.util.Date")) {
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		return "{ \"type\": \"" + o.getClass().getName() + "\", \"value\": \"" + sdf.format((Date)o) + "\" }";
	    	}
	    	
	        return "{ \"type\": \"" + o.getClass().getName() + "\", \"value\": \"" + o + "\" }";
	    }

	    public static Date parseDate(String s) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

	        try {
	            return sdf.parse(s);
	        } catch (Exception e) {
	            try {
	                return sdf2.parse(s);
	            } catch (Exception ex) {
	                return new Date();
	            }
	        }
	    }
	}

}
