package it.polimi.travelDream.ejb.beans;

import it.polimi.travelDream.ejb.dtos.RoleDTO;
import it.polimi.travelDream.ejb.dtos.UserDTO;
import it.polimi.travelDream.ejb.entities.Role;
import it.polimi.travelDream.ejb.entities.User;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class UserManagerBean
 */
@Stateful
@LocalBean
public class UserBean implements UserBeanInterface {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	

	@Override
	public void save(UserDTO userDTO) {
		User newUser = new User(userDTO);
		
		em.persist(newUser);
	}

	@Override
	public void update(UserDTO userDTO) {
		em.merge(new User(userDTO));
	}


	@Override
	public UserDTO getUserDTO() {
		UserDTO userDTO = convertToDTO(getPrincipalUser());
		return userDTO;
	}
	
	@Override
	public boolean isUserOnline() {
		return !(getPrincipalEmail().equalsIgnoreCase("ANONYMOUS")); 
	}


	@Override
	@RolesAllowed({"employee", "customer"})
	public void unregister() {
		remove(getPrincipalUser());
	}


	public User find(String email) {
    	return em.find(User.class, email);
    }
    
    public List<User> getAllUsers() {
    	return em.createNamedQuery(User.FIND_ALL, User.class)
                .getResultList();
    }

    @RolesAllowed({"employee"})
    public void remove(String email) {
		User user = find(email);
		remove(user);
	}
    
    @RolesAllowed({"employee"})
    public void remove(User user) {
    	em.remove(user);
	}
    
    
    public User getPrincipalUser() {
    	return find(getPrincipalEmail());
    }
    
    
    public String getPrincipalEmail() {
    	return context.getCallerPrincipal().getName();
    }

    public static UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());
		userDTO.setSurname(user.getSurname());
		userDTO.setAddress(user.getAddress());
		userDTO.setPhone(user.getPhone());
		userDTO.setPassword(user.getPassword());
		userDTO.setRole(user.getRole().getName());
		return userDTO;
	}
    
    @Override
    public List<RoleDTO> getAllRolesDTO() {
    	List<Role> roles = em.createNamedQuery(Role.FIND_ALL, Role.class).getResultList();
    	
    	List<RoleDTO> dtos = new ArrayList<RoleDTO>();
    	
    	for (Role r : roles) {
			dtos.add(NomenBean.convertToDTO(r));
		}

		return dtos;
    }


	@Override
	public UserDTO getUserDTO(String email) {
		if (email == null || email.length() == 0) {
			return getUserDTO();
		}
		
		User user = em.find(User.class, email);
		
		return convertToDTO(user);
	}

}
