package it.polimi.travelDream.ejb.entities;

import it.polimi.travelDream.ejb.dtos.UserDTO;

import java.io.Serializable;
import java.lang.String;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity implementation class for Entity: UserEntity
 *
 */
@Entity
@Table(name="Users")
@NamedQueries({
	@NamedQuery(name=User.FIND_ALL,
				query="SELECT u FROM User u ORDER BY u.surname ASC")
})
public class User implements Serializable {

	public static final String FIND_ALL = "User.findAll";
	   
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
	@Id
	private String email;
	@NotEmpty
	private String password;
	private String address;
	@NotEmpty
	private String phone;
	@NotNull
	@ManyToOne
	@JoinColumn(name="ROLE")
	private Role role;
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}
	public User(UserDTO userDTO) {
		this();
		this.name = userDTO.getName();
		this.surname = userDTO.getSurname();
		this.email = userDTO.getEmail();
		this.password = getSha512Hex(userDTO.getPassword());
		this.address = userDTO.getAddress();
		this.phone = userDTO.getPhone();
		this.role = new Role();
		this.role.setName(userDTO.getRole());
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}   
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}   
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public static String getSha512Hex(String message) {
		return getHash(message, "SHA-512");
	}
	
	public static String getHash(String message, String algorithm) {
        try {
            byte[] buffer = message.getBytes();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(buffer);
            byte[] digest = md.digest();
            String hex = "";
            for(int i = 0 ; i < digest.length ; i++) {
                int b = digest[i] & 0xff;
                if (Integer.toHexString(b).length() == 1) hex = hex + "0";
                hex  = hex + Integer.toHexString(b);
            }
            return hex;
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
   
}
