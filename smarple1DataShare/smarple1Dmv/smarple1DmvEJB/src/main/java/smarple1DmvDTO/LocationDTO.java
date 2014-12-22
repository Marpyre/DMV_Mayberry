package smarple1DmvDTO;

import java.io.Serializable;

public class LocationDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private int streetNo;
	private String streetName;
	private String city;
	private String state;
	private String zip;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public int getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(int streetNo) {
		this.streetNo = streetNo;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
