package it.polimi.travelDream.ejb.beans;

import java.util.List;

import it.polimi.travelDream.ejb.dtos.RoleDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;

import javax.ejb.Local;

@Local
public interface UserBeanInterface {
	
	public void save(UserDTO user);
	
	public void update(UserDTO user);
	
	public void unregister();
	
	public UserDTO getUserDTO();
	
	public UserDTO getUserDTO(String email);

	public List<RoleDTO> getAllRolesDTO();

	public boolean isUserOnline();
	
}
