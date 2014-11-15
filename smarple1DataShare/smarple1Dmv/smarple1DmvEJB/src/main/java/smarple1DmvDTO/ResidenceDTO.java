package smarple1DmvDTO;

import java.io.Serializable;
import java.util.Date;

public class ResidenceDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
//	private Location location;
//	private Person person;
	private Date startDate;
	private Date endDate;
	
	public long getId() {
		return id;
	}

//	public Location getLocation() {
//		return location;
//	}
//
//	public void setLocation(Location location) {
//		this.location = location;
//	}
//
//	public Person getPerson() {
//		return person;
//	}
//
//	public void setPerson(Person person) {
//		this.person = person;
//	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
