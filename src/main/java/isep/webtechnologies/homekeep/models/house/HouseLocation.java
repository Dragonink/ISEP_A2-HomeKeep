package isep.webtechnologies.homekeep.models.house;

import javax.persistence.Embeddable;

@Embeddable
public class HouseLocation {
	private String country;
	public String getCountry() {
		return country;
	}

	private String region;
	public String getRegion() {
		return region;
	}

	HouseLocation() {}
	public HouseLocation(String country, String region) {
		this.country = country;
		this.region = region;
	}
}
