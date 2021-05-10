package isep.webtechnologies.homekeep.models.house;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import isep.webtechnologies.homekeep.models.user.User;

@Entity
public class House {
	@Id
	@GeneratedValue
	private Integer id;
	public Integer getId() {
		return id;
	}

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
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

	@OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HouseBooking> bookings;
	public Set<HouseBooking> getBookings() {
		return bookings;
	}

	House() {}
	public House(User owner, String title) {
		this.owner = owner;
		this.title = title;
	}
}
