package smarple1DmvDTO;

import java.io.Serializable;
import java.util.Date;

public class PhysicalDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//private Person person;
	private String sex;
	private Date dob;
	private int height;
	private int weight;
	private String eyeColor;
	private String hairColor;
	private byte[] photo;
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
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

	public String getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

	public String getHairColor() {
		return hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
}
