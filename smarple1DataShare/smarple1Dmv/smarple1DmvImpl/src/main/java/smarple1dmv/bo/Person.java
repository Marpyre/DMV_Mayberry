package smarple1dmv.bo;

import java.util.Collection;

public class Person {
	private long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String nameSuffix;
	private Residence currentResidence;
	private Collection<Residence> residences;
	private PhysicalDetails physicalDetails;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public Residence getCurrentResidence() {
		return currentResidence;
	}
	public void setCurrentResidence(Residence currentResidence) {
		this.currentResidence = currentResidence;
	}
	public Collection<Residence> getResidences() {
		return residences;
	}
	public void setResidences(Collection<Residence> residences) {
		this.residences = residences;
	}
	public PhysicalDetails getPhysicalDetails() {
		return physicalDetails;
	}
	public void setPhysicalDetails(PhysicalDetails physicalDetails) {
		this.physicalDetails = physicalDetails;
	}
}