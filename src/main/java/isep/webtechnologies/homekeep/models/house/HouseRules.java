package isep.webtechnologies.homekeep.models.house;

import javax.persistence.Embeddable;

@Embeddable
public class HouseRules {
	private Boolean smokersWelcome = false;
	public Boolean getSmokersWelcome() {
		return smokersWelcome;
	}
	public void setSmokersWelcome(Boolean smokersWelcome) {
		this.smokersWelcome = smokersWelcome;
	}

	private Boolean childrenWelcome = false;
	public Boolean getChildrenWelcome() {
		return childrenWelcome;
	}
	public void setChildrenWelcome(Boolean childrenWelcome) {
		this.childrenWelcome = childrenWelcome;
	}

	private Boolean petsWelcome = false;
	public Boolean getPetsWelcome() {
		return petsWelcome;
	}
	public void setPetsWelcome(Boolean petsWelcome) {
		this.petsWelcome = petsWelcome;
	}

	private Boolean petsToFeed = false;
	public Boolean getPetsToFeed() {
		return petsToFeed;
	}
	public void setPetsToFeed(Boolean petsToFeed) {
		this.petsToFeed = petsToFeed;
	}

	private Boolean plantsToWater = false;
	public Boolean getPlantsToWater() {
		return plantsToWater;
	}
	public void setPlantsToWater(Boolean plantsToWater) {
		this.plantsToWater = plantsToWater;
	}

	public HouseRules() {}
	public HouseRules(Boolean smokersWelcome, Boolean childrenWelcome, Boolean petsWelcome, Boolean petsToFeed, Boolean plantsToWater) {
		this.smokersWelcome = smokersWelcome;
		this.childrenWelcome = childrenWelcome;
		this.petsWelcome = petsWelcome;
		this.petsToFeed = petsToFeed;
		this.plantsToWater = plantsToWater;
	}
}
