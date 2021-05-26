package isep.webtechnologies.homekeep.models.house;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import isep.webtechnologies.homekeep.models.user.User;

@Entity
public class HouseRating {
	@Id
	@GeneratedValue
	private Integer id;
	public Integer getId() {
		return id;
	}

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private House house;
	@JsonIgnore
	public House getHouse() {
		return house;
	}

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User user;
	public User getUser() {
		return user;
	}

	private Double value;
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

	HouseRating() {}
	public HouseRating(House house, User user, Double value) {
		this.house = house;
		this.user = user;
		this.value = value;
	}
}
