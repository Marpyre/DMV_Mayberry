package smarple1dmv.bo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "NAME_SUFFIX")
	private String nameSuffix;

	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "smarple1dmv_vehicle_owner_link", joinColumns = @JoinColumn(name = "PERSON_ID"), inverseJoinColumns = @JoinColumn(name = "VEHICLE_ID"))
	private Set<VehicleRegistration> registrations;

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

	public Collection<Residence> getResidences() {
		if (residences == null) {
			return new HashSet<Residence>();
		}
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

	public Set<VehicleRegistration> getRegistrations() {
		if (registrations == null) {
			registrations = new HashSet<VehicleRegistration>();
		}
		return registrations;
	}

	public void addRegistration(VehicleRegistration vr) {
		if (registrations == null) {
			registrations = new HashSet<VehicleRegistration>();
		}
		registrations.add(vr);
	}

	@Override
	public String toString() {
		return "[" + this.id + "," + this.firstName + "," + this.middleName
				+ "," + this.lastName + "," + this.nameSuffix + "]";
	}
}