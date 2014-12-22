package smarple1DmvDTO;

import java.io.Serializable;

public class PersonDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String nameSuffix;
	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNameSuffix() {
		return nameSuffix;
	}
	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
	
	public String toString(){
		return "[" + this.id + "," + this.firstName + "," + this.middleName + "," + this.lastName + "," + this.nameSuffix + "]";
	}
}
