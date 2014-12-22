package smarple1MayberryBO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "smarple1may_activity")
public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//Uni-directional Many to One Association to POI
	@ManyToOne(optional=false)
	@JoinColumn(name = "POI_ID")
	private POI poi;
	
	@Column(name="ACTIVITY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date Date;
	
	@Column(name="CODE")
	@Enumerated(EnumType.ORDINAL)
	private Code type;
	
	@Column(name="DESCRIPTION")
	private String Description;

	public enum Code {Gang, Speeding, ParkingViolation};
	
	public Activity(){}
	
	public Activity(POI poi){
		this.poi = poi;
	}
	
	public long getId() {
		return id;
	}

	public POI getPoi() {
		return poi;
	}

	public void setPoi(POI poi) {
		this.poi = poi;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
}
