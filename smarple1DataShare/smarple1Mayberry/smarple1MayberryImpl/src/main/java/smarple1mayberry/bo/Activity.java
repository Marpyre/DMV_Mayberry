package smarple1mayberry.bo;

import java.util.Date;

public class Activity {
	private long id;
	private Date Date;
	//private enum Type{};
	private String Description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
}
