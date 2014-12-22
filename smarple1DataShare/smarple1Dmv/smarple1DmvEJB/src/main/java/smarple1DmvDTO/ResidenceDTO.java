package smarple1DmvDTO;

import java.io.Serializable;
import java.util.Date;

public class ResidenceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private LocationDTO location;
	private PersonDTO person;
	private Date startDate;
	private Date endDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

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
