package isep.webtechnologies.homekeep.models.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import isep.webtechnologies.homekeep.models.house.House;
import isep.webtechnologies.homekeep.models.house.HouseBooking;
import isep.webtechnologies.homekeep.models.house.HouseRating;

@Entity
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"email"})
})
public class User implements UserDetails {
	@Id
	@GeneratedValue
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

	private String password;
	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	public static String encryptPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(password.getBytes());
			return new String(digest.digest());
		} catch (NoSuchAlgorithmException error) {
			return null;
		}
	}
	public boolean checkPassword(String password) {
		return this.password.equals(User.encryptPassword(password));
	}
	public void setPassword(String password) {
		this.password = User.encryptPassword(password);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		//required by UserDetails for authentication but email is used instead
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	private String firstname;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	private String lastname;
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	private Date birthdate;
	public Optional<Date> getBirthdate() {
		return Optional.ofNullable(birthdate);
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	private String country;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	private String telephone;
	public Optional<String> getTelephone() {
		return Optional.ofNullable(telephone);
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Lob
	private Blob picture;
	public Optional<Blob> getPicture() {
		return Optional.ofNullable(picture);
	}
	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@MapKey(name = "house")
	private Map<House,HouseRating> ratings;
	@JsonIgnore
	public Map<House,HouseRating> getRatings() {
		return ratings;
	}

	@OneToMany(mappedBy = "booker", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HouseBooking> bookings;
	@JsonIgnore
	public Set<HouseBooking> getBookings() {
		return bookings;
	}

	User() {}
	public User(String email, String password, String firstname, String lastname, String address, String country) {
		this.email = email;
		setPassword(password);
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.country = country;
	}
}
