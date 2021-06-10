package isep.webtechnologies.homekeep.models.user;

import java.util.Date;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import isep.webtechnologies.homekeep.models.house.HouseBooking;

@Entity
public class Message {
	@Id
	@GeneratedValue
	private Integer id;
	public Integer getId() {
		return id;
	}

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User sender;
	public User getOwner() {
		return sender;
	}

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User recipient;
	public User getRecipient() {
		return recipient;
	}

	private String content;
	public Optional<String> getContent() {
		return Optional.ofNullable(content);
	}

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private HouseBooking booking;
	public Optional<HouseBooking> getBooking() {
		return Optional.ofNullable(booking);
	}

	private Date date;
	public Date getDate() {
		return date;
	}

	Message() {}
	public Message(User sender, User recipient, String content, HouseBooking booking) {
		this.sender = sender;
		this.recipient = recipient;
		this.content = content;
		this.booking = booking;
		date = new Date(System.currentTimeMillis());
	}
}
