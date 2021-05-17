package isep.webtechnologies.homekeep.models.user;

import java.sql.Blob;
import java.sql.Date;
import java.util.Collection;
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

import isep.webtechnologies.homekeep.models.house.House;
import isep.webtechnologies.homekeep.models.house.HouseBooking;
import isep.webtechnologies.homekeep.models.house.HouseRating;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		//required by UserDetails for authentication but email is used instead
		return email;
	}

	public Boolean comparePassword(String password) {
		return this.password.equals(password);
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
	public Map<House,HouseRating> getRatings() {
		return ratings;
	}

	@OneToMany(mappedBy = "booker", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HouseBooking> bookings;
	public Set<HouseBooking> getBookings() {
		return bookings;
	}

	User() {}
	public User(String email, String password, String firstname, String lastname, String address, String country) {
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.country = country;
	}
}
