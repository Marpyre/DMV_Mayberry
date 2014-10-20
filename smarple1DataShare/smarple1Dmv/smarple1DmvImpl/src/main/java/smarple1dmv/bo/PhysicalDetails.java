package smarple1dmv.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "smarple1dmv_physical_details")
public class PhysicalDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PERSON_ID")
	private long personId;
	
	@OneToOne(optional=false,fetch=FetchType.EAGER)
    @MapsId
	private Person person;
	
	@Column(name="SEX")
	@Enumerated(EnumType.STRING)
	private SEX sex;
	
	@Column(name="DOB")
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(name="HEIGHT")
	private int height;
	
	@Column(name="WEIGHT")
	private int weight;
	
	@Column(name="EYE_COLOR")
	@Enumerated(EnumType.STRING)
	private COLOR eyeColor;
	
	@Column(name="HAIR_COLOR")
	@Enumerated(EnumType.STRING)
	private COLOR hairColor;
	
	@Transient
	private byte[] photo;
	
	public enum COLOR {red, blue, yellow, green, black};
	
	public enum SEX {M, F};
	
	public Person getId() {
		return person;
	}
	public void setId(Person person) {
		this.person = person;
	}
	public SEX getSex() {
		return sex;
	}
	public void setSex(SEX sex) {
		this.sex = sex;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public COLOR getEyeColor() {
		return eyeColor;
	}
	public void setEyeColor(COLOR eyeColor) {
		this.eyeColor = eyeColor;
	}
	public COLOR getHairColor() {
		return hairColor;
	}
	public void setHairColor(COLOR hairColor) {
		this.hairColor = hairColor;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	};
}
