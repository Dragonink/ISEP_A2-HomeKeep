package isep.webtechnologies.homekeep.models.user;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	public String getContent() {
		return content;
	}

	private Date date;
	public Date getDate() {
		return date;
	}

	Message() {}
	public Message(User sender, User recipient, String content) {
		this.sender = sender;
		this.recipient = recipient;
		this.content = content;
		date = new Date(System.currentTimeMillis());
	}
}
