package isep.webtechnologies.homekeep.models.house;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import isep.webtechnologies.homekeep.models.user.User;

@Entity
public class HouseBooking {
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

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User booker;
	public User getBooker() {
		return booker;
	}

	private Boolean isAvailable;
	public Boolean isAvailable() {
		return isAvailable;
	}

	private Date startDate;
	public Date getStartDate() {
		return startDate;
	}

	private Date endDate;
	public Date getEndDate() {
		return endDate;
	}

	HouseBooking() {}
	public HouseBooking(House house, User booker, Boolean isAvailable, Date startDate, Date endDate) {
		this.house = house;
		this.booker = booker;
		this.isAvailable = isAvailable;
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
