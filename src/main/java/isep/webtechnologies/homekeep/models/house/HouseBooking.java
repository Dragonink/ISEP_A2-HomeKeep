package isep.webtechnologies.homekeep.models.house;

import java.util.Date;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import isep.webtechnologies.homekeep.models.user.User;

@Entity
public class HouseBooking {
	public enum Status {
		PENDING,
		ACCEPTED,
		DENIED;
	}

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

	private Status status;
	public Status getStatus() {
		return Optional.ofNullable(status).orElse(Status.PENDING);
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	HouseBooking() {}
	public HouseBooking(House house, User booker, Boolean isAvailable, Date startDate, Date endDate) {
		this.house = house;
		this.booker = booker;
		this.isAvailable = isAvailable;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = booker == house.getOwner() ? Status.ACCEPTED : Status.PENDING;
	}
}
