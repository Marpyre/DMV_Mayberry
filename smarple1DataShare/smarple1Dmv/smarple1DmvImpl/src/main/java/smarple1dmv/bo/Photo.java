package smarple1dmv.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "smarple1dmv_photo")
public class Photo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private long personId;
	
	@OneToOne(optional=false,fetch=FetchType.LAZY)
    @MapsId
    @JoinColumn(name="PERSON_ID", referencedColumnName="PERSON_ID")
	private PhysicalDetails physicalDetails;

	@Lob
	@Column(name="IMAGE")
	private byte[] image;
	
	public PhysicalDetails getId() {
		return physicalDetails;
	}
	
	public void setId(PhysicalDetails id) {
		this.physicalDetails = id;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
}
