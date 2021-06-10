package isep.webtechnologies.homekeep.controllers;

import java.sql.Blob;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import isep.webtechnologies.homekeep.models.user.User;
import isep.webtechnologies.homekeep.models.user.UserRepository;

@Controller
@RequestMapping(path = "/api/users")
public class UserRepositoryController {
	@Autowired
	private UserRepository repository;

	@PostMapping(consumes = {"multipart/form-data"})
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody User addUser(
		@RequestParam("email") String email,
		@RequestParam("password") String password,
		@RequestParam("firstname") String firstname,
		@RequestParam("lastname") String lastname,
		@RequestParam("address") String address,
		@RequestParam("country") String country
	) {
		User user = new User(email, password, firstname, lastname, address, country);
		return repository.save(user);
	}

	@PostMapping(path = "/login")
	public @ResponseBody Optional<User> testUserLogin(
		@RequestParam("email") String email,
		@RequestParam("password") String password
	) {
		return repository.findByEmail(email)
			.map(user -> {
				if (user.comparePassword(password)) {
					return user;
				} else {
					return null;
				}
			});
	}

	@PatchMapping(path = "/{id}", consumes = {"multipart/form-data"})
	public @ResponseBody Optional<User> editUser(
		@PathVariable Integer id,
		@RequestParam("email") Optional<String> email,
		@RequestParam("password") Optional<String> password,
		@RequestParam("firstname") Optional<String> firstname,
		@RequestParam("lastname") Optional<String> lastname,
		@RequestParam("birthdate") Optional<Date> birthdate,
		@RequestParam("address") Optional<String> address,
		@RequestParam("country") Optional<String> country,
		@RequestParam("telephone") Optional<String> telephone,
		@RequestParam("picture") Optional<Blob> picture
	) {
		return repository.findById(id).map(user -> {
			email.ifPresent(value -> user.setEmail(value));
			password.ifPresent(value -> user.setPassword(value));
			firstname.ifPresent(value -> user.setFirstname(value));
			lastname.ifPresent(value -> user.setLastname(value));
			birthdate.ifPresent(value -> user.setBirthdate(value));
			address.ifPresent(value -> user.setAddress(value));
			country.ifPresent(value -> user.setCountry(value));
			telephone.ifPresent(value -> user.setTelephone(value));
			picture.ifPresent(value -> user.setPicture(value));
			return repository.save(user);
		});
	}
}
