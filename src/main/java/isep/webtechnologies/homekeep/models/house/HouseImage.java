package isep.webtechnologies.homekeep.models.house;

import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class HouseImage {
	@Id
	@GeneratedValue
	private Integer id;
	public Integer getId() {
		return id;
	}

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private House house;
	public House getHouse() {
		return house;
	}

	@Lob
	private Blob image;
	public Blob getImage() {
		return image;
	}

	HouseImage() {}
	public HouseImage(House house, Blob image) {
		this.house = house;
		this.image = image;
	}
}
