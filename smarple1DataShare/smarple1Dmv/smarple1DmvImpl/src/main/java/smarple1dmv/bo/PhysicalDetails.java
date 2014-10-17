package smarple1dmv.bo;

import java.util.Date;

import smarple1dmv.bo.Enums.COLOR;
import smarple1dmv.bo.Enums.SEX;

public class PhysicalDetails {
	private long id;
	private SEX sex;
	private Date dob;
	private int height;
	private int weight;
	private COLOR eyeColor;
	private COLOR hairColor;
	private byte[] photo;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
