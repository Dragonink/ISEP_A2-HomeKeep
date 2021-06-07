package isep.webtechnologies.homekeep.models.house;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import isep.webtechnologies.homekeep.models.user.User;

@Entity
public class House {
	@Id
	@GeneratedValue
	private Integer id;
	public Integer getId() {
		return id;
	}

	@Column(name = "owner_id")
	private Integer ownerId;
	@ManyToOne(optional = false)
	@JoinColumn(name = "owner_id", insertable = false, updatable = false)
	private User owner;
	public User getOwner() {
		return owner;
	}

	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	private String description;
	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Embedded
	private HouseLocation location;
	public HouseLocation getLocation() {
		return location;
	}

	@Embedded
	private HouseRules rules;
	public HouseRules getRules() {
		return rules;
	}
	public void setRules(HouseRules rules) {
		this.rules = rules;
	}

	@Embedded
	private HouseAmenities amenities;
	public HouseAmenities getAmenities() {
		return amenities;
	}
	public void setAmenities(HouseAmenities amenities) {
		this.amenities = amenities;
	}

	@OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HouseImage> images;
	public List<HouseImage> getImages() {
		return images;
	}
	public void setImages(List<HouseImage> images) {
		this.images = images;
	}

	@OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
	@MapKey(name = "user")
	private Map<User, HouseRating> ratings;
	public Map<User, HouseRating> getRatings() {
		return ratings;
	}
	@JsonIgnore
	public Optional<Double> getAverageRating() {
		double averageRating = 0;
		for (HouseRating houseRating : ratings.values()) averageRating += houseRating.getValue();
		averageRating /= ratings.size();
		return Optional.ofNullable((ratings.size() == 0) ? null : averageRating);
	}

	@OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HouseBooking> bookings;
	public Set<HouseBooking> getBookings() {
		return bookings;
	}

	House() {}
	public House(User owner, String title) {
		this.owner = owner;
		this.ownerId = owner.getId();
		this.title = title;
	}
}
