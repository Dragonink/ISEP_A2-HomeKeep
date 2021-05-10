package isep.webtechnologies.homekeep.models.house;

import javax.persistence.Embeddable;

@Embeddable
public class HouseRules {
	private Boolean smokersWelcome;
	public Boolean areSmokersWelcome() {
		return smokersWelcome;
	}

	private Boolean childrenWelcome;
	public Boolean areChildrenWelcome() {
		return childrenWelcome;
	}

	private Boolean petsWelcome;
	public Boolean arePetsWelcome() {
		return petsWelcome;
	}

	private Boolean petsToFeed;
	public Boolean arePetsToFeed() {
		return petsToFeed;
	}

	private Boolean plantsToWater;
	public Boolean arePlantsToWater() {
		return plantsToWater;
	}

	HouseRules() {}
	public HouseRules(Boolean smokersWelcome, Boolean childrenWelcome, Boolean petsWelcome, Boolean petsToFeed, Boolean plantsToWater) {
		this.smokersWelcome = smokersWelcome;
		this.childrenWelcome = childrenWelcome;
		this.petsWelcome = petsWelcome;
		this.petsToFeed = petsToFeed;
		this.plantsToWater = plantsToWater;
	}
}
