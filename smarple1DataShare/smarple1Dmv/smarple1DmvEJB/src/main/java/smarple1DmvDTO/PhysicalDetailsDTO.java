package smarple1DmvDTO;

import java.io.Serializable;
import java.util.Date;

import smarple1dmv.bo.PhysicalDetails.COLOR;
import smarple1dmv.bo.PhysicalDetails.SEX;

public class PhysicalDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//private Person person;
	private SEX sex;
	private Date dob;
	private int height;
	private int weight;
	private COLOR eyeColor;
	private COLOR hairColor;
	private byte[] photo;
	
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
	}
	
}
