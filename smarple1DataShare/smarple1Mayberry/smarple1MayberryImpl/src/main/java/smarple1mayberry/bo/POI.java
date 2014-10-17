package smarple1mayberry.bo;

import java.util.Collection;

public class POI {

	private long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private Collection<POI> aliases;

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

	public Collection<POI> getAliases() {
		return aliases;
	}

	public void setAliases(Collection<POI> aliases) {
		this.aliases = aliases;
	}
}