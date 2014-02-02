package it.polimi.travelDream.ejb.beans;

import java.util.List;

import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.PackageDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import javax.ejb.Local;

@Local
public interface PackageBeanInterface {
	
	public void save(PackageDTO pkg);
	
	public void update(PackageDTO pkg);

	public void remove(PackageDTO pkg);
	
	public PackageDTO getPackageDTO(String name);
	
	public List<PackageDTO> getAllPackagesDTO();
	
	public List<ComponentDTO> getAllComponentsDTO(PackageDTO pkg);
	
	public Long getTotalPrice(PackageDTO pkg);

	public List<ComponentDTO> getAllComponentsDTO();

	public UserDTO getUserDTO(String email);

	public void remove(String packName);

}
