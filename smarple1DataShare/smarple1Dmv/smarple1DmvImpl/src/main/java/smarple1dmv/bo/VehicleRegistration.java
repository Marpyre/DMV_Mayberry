package smarple1dmv.bo;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smarple1dmv_vehicle_registration")
public class VehicleRegistration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "TAG_NO")
	private String tagNo;
	
	@Column(name = "VIN")
	private String vin;
	
	@Column(name = "MAKE")
	private String make;
	
	@Column(name = "MODEL")
	private String model;
	
	@Column(name = "COLOR")
	private COLOR color;
	
	@Column(name = "YEAR")
	private Date year;
	
	@Column(name = "EXPIRATION")
	private Date expiration;
	
	/*@Column(name = "STREET_NO")
	private Collection<Person> owners;*/
	
	public enum COLOR {red, blue, yellow, green, black};
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public COLOR getColor() {
		return color;
	}
	public void setColor(COLOR color) {
		this.color = color;
	}
	public Date getYear() {
		return year;
	}
	public void setYear(Date year) {
		this.year = year;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	/*public Collection<Person> getOwners() {
		return owners;
	}
	public void setOwners(Collection<Person> owners) {
		this.owners = owners;
	}*/
}
