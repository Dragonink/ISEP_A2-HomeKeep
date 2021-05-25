package isep.webtechnologies.homekeep.models.house;

import javax.persistence.Embeddable;

@Embeddable
public class HouseAmenities {
	private Integer beds;
	public Integer getBeds() {
		return beds;
	}
	public void setBeds(Integer beds) {
		this.beds = beds;
	}

	private Integer doubleBeds;
	public Integer getDoubleBeds() {
		return doubleBeds;
	}
	public void setDoubleBeds(Integer doubleBeds) {
		this.doubleBeds = doubleBeds;
	}

	private Integer babyBeds;
	public Integer getBabyBeds() {
		return babyBeds;
	}
	public void setBabyBeds(Integer babyBeds) {
		this.babyBeds = babyBeds;
	}

	private Boolean parking;
	public Boolean getParking() {
		return parking;
	}
	public void setParking(Boolean parking) {
		this.parking = parking;
	}

	private Boolean heatingSystem;
	public Boolean getHeatingSystem() {
		return heatingSystem;
	}
	public void setHeatingSystem(Boolean heatingSystem) {
		this.heatingSystem = heatingSystem;
	}

	private Boolean coolingSystem;
	public Boolean getCoolingSystem() {
		return coolingSystem;
	}
	public void setCoolingSystem(Boolean coolingSystem) {
		this.coolingSystem = coolingSystem;
	}

	private Boolean shower;
	public Boolean getShower() {
		return shower;
	}
	public void setShower(Boolean shower) {
		this.shower = shower;
	}

	private Boolean freezer;
	public Boolean getFreezer() {
		return freezer;
	}
	public void setFreezer(Boolean freezer) {
		this.freezer = freezer;
	}

	private Boolean microwave;
	public Boolean getMicrowave() {
		return microwave;
	}
	public void setMicrowave(Boolean microwave) {
		this.microwave = microwave;
	}

	private Boolean oven;
	public Boolean getOven() {
		return oven;
	}
	public void setOven(Boolean oven) {
		this.oven = oven;
	}

	private Boolean barbecue;
	public Boolean getBarbecue() {
		return barbecue;
	}
	public void setBarbecue(Boolean barbecue) {
		this.barbecue = barbecue;
	}

	private Boolean dishwasher;
	public Boolean getDishwasher() {
		return dishwasher;
	}
	public void setDishwasher(Boolean dishwasher) {
		this.dishwasher = dishwasher;
	}

	private Boolean washingMachine;
	public Boolean getWashingMachine() {
		return washingMachine;
	}
	public void setWashingMachine(Boolean washingMachine) {
		this.washingMachine = washingMachine;
	}

	private Boolean swimmingPool;
	public Boolean getSwimmingPool() {
		return swimmingPool;
	}
	public void setSwimmingPool(Boolean swimmingPool) {
		this.swimmingPool = swimmingPool;
	}

	private Boolean disabledAccessible;
	public Boolean getDisabledAccessible() {
		return disabledAccessible;
	}
	public void setDisabledAccessible(Boolean disabledAccessible) {
		this.disabledAccessible = disabledAccessible;
	}

	HouseAmenities() {}
	public HouseAmenities(Integer beds, Integer doubleBeds, Integer babyBeds, Boolean parking, Boolean heatingSystem, Boolean coolingSystem, Boolean shower, Boolean freezer, Boolean microwave, Boolean oven, Boolean barbecue, Boolean dishwasher, Boolean washingMachine, Boolean swimmingPool, Boolean disabledAccessible) {
		this.beds = beds;
		this.doubleBeds = doubleBeds;
		this.babyBeds = babyBeds;
		this.parking = parking;
		this.heatingSystem = heatingSystem;
		this.coolingSystem = coolingSystem;
		this.shower = shower;
		this.freezer = freezer;
		this.microwave = microwave;
		this.oven = oven;
		this.barbecue = barbecue;
		this.dishwasher = dishwasher;
		this.washingMachine = washingMachine;
		this.swimmingPool = swimmingPool;
		this.disabledAccessible = disabledAccessible;
	}
}
