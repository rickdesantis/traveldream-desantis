package it.polimi.travelDream.web.beans.profile;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="defaultsBean")
@ApplicationScoped
public class DefaultsBean {
	
	public static final String BASE_PATH = "/TravelDreamWeb/";
	
	public static final String EMPLOYEES = BASE_PATH + "employees/";
	
	public static final String CUSTOMERS = BASE_PATH + "customers/";
	
	public String getBASE_PATH() {
		return BASE_PATH;
	}
	
	public String getEMPLOYEES() {
		return EMPLOYEES;
	}
	
	public String getCUSTOMERS() {
		return CUSTOMERS;
	}
	
}
