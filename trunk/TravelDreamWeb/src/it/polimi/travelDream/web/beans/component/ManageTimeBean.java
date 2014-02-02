package it.polimi.travelDream.web.beans.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polimi.travelDream.ejb.beans.ComponentBeanInterface;
import it.polimi.travelDream.ejb.dtos.ComponentDTO;
import it.polimi.travelDream.ejb.dtos.DateComponentDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="manageTimeBean")
@ViewScoped
public class ManageTimeBean {
	
	@EJB
	private ComponentBeanInterface componentBean;
	
	private ComponentDTO component;
	
	@PostConstruct
	public void init() {
		String compName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("compName");
		
		component = componentBean.getComponentDTO(compName);
		
		List<DateComponentDTO> ds = componentBean.getDatesDTO(component);
		for (DateComponentDTO d : ds)
			dates.add(new CoupledTimes(d.getStart(), d.getEnd()));
	}
	
	public static class CoupledTimes {
		private String start, end;
		
		public CoupledTimes(String start, String end) {
			this.start = start;
			this.end = end;
		}
		
		public CoupledTimes(Date start, Date end) {
			this.start = formatTime(start);
			this.end = formatTime(end);
		}
		
		public Date getDateStart() {
			return parseDate(start);
		}
		
		public Date getDateEnd() {
			return parseDate(end);
		}
		
		public CoupledTimes() { }

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = end;
		}
		
		public static String formatFullDate(Date date) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		}
		
		public static String formatTime(Date date) {
			DateFormat df = new SimpleDateFormat("HH:mm");
			return df.format(date);
		}
		
		public static Date parseDate(String s) {
			DateFormat df3 = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat df = new SimpleDateFormat("HH:mm");
			try {
				return df.parse(s);
			} catch (Exception e) {
				try {
					return df2.parse(s);
				} catch (Exception e2) {
					try {
						return df3.parse(s);
					} catch (Exception e3) {
						return new Date();
					}
				}
			}
		}
		
	}
	

	public ManageTimeBean() {
		dates = new ArrayList<CoupledTimes>();
		date = new CoupledTimes();
	}

	public ComponentDTO getComponent() {
		return component;
	}
	
	private CoupledTimes date;
	
	public CoupledTimes getDate() {
		return date;
	}

	public void setDate(CoupledTimes date) {
		this.date = date;
	}

	private List<CoupledTimes> dates;
	
	public String reinit() {
		DateComponentDTO d = new DateComponentDTO();
		d.setStart(date.getDateStart());
		d.setEnd(date.getDateEnd());
		d.setComponent(component.getName());
		
		componentBean.save(d);
		
		update();
		
		date = new CoupledTimes();
		return null;
	}
	
	public String update() {
		List<DateComponentDTO> ds = componentBean.getDatesDTO(component);
		boolean found = false;
		for (DateComponentDTO date : ds) {
			found = false;
			for (int i = 0; i < dates.size() && !found; i++)
				if (dates.get(i).getStart().equals(CoupledTimes.formatTime(date.getStart())) && dates.get(i).getEnd().equals(CoupledTimes.formatTime(date.getEnd())))
					found = true;
			
			if (!found)
				componentBean.remove(date);
		}
		return null;
	}

	public List<CoupledTimes> getDates() {
		return dates;
	}

	public void setDates(List<CoupledTimes> dates) {
		this.dates = dates;
	}

}
