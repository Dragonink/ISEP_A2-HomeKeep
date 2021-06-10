package isep.webtechnologies.homekeep.models.house;

import javax.persistence.Embeddable;

@Embeddable
public class HouseLocation {
	private String country;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	private String region;
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	public HouseLocation() {}
	public HouseLocation(String country, String region) {
		this.country = country;
		this.region = region;
	}
}
