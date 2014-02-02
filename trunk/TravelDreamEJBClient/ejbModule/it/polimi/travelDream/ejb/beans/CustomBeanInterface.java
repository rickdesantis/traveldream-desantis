package it.polimi.travelDream.ejb.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.CustomPackageDTO;
import it.polimi.travelDream.ejb.dtos.PackageDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import javax.ejb.Local;

@Local
public interface CustomBeanInterface {
	
	public void save(CustomPackageDTO pkg);
	
	public void update(CustomPackageDTO pkg, ComponentDTO component, Map<String, Object> configuration);
	
	public void remove(CustomPackageDTO pkg);
	
	public CustomPackageDTO getCustomPackageDTO(String id);
	
	public List<CustomPackageDTO> getAllCustomPackagesDTO();
	
	public List<ComponentDTO> getAllComponentsDTO(CustomPackageDTO pkg);
	
	public Long getTotalPrice(CustomPackageDTO pkg);

	public List<CustomPackageDTO> getAllPackagesDTO(String user);

	public UserDTO getUserDTO(String email);

	public PackageDTO getOriginalPackage(CustomPackageDTO pack);

	public UserDTO getOwner(CustomPackageDTO pack);

	public Date getDepartureDate(CustomPackageDTO pack);

	public Date getReturnDate(CustomPackageDTO pack);

	public Map<String, Map<String, Object>> getConfiguration(CustomPackageDTO pack);

	public ComponentDTO getComponentDTO(String comp);

	public List<Entry<Date, Date>> getDates(ComponentDTO component);

	public List<Entry<String, ?>> getDefaults(ComponentDTO component);

	public CustomPackageDTO createCustomPackage(PackageDTO orig, UserDTO user);

	public PackageDTO getPackageDTO(String orig);

	public Date getDate(CustomPackageDTO pack, ComponentDTO comp);

	public void remove(String packageId);

	public int getActualDays(CustomPackageDTO pack);

}
