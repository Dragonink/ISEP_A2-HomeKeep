package isep.webtechnologies.homekeep.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // Hibernate will create a table from this class
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	public Integer getId() {
		return id;
	}

	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
