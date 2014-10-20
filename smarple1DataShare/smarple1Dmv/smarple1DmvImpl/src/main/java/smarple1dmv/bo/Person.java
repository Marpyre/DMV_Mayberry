package smarple1dmv.bo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import smarple1dmv.bo.Residence;

@Entity
@Table(name = "smarple1dmv_person")
public class Person {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="MIDDLE_NAME")
	private String middleName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="NAME_SUFFIX")
	private String nameSuffix;
	
	@Transient
	private Residence currentResidence;
	
	@Transient
	private Collection<Residence> residences;
	
	@Transient
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