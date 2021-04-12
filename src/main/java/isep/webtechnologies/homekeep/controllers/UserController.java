package isep.webtechnologies.homekeep.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import isep.webtechnologies.homekeep.models.User;
import isep.webtechnologies.homekeep.models.UserRepository;

@Controller
@RequestMapping(path = "/users")
public class UserController {
	@Autowired
	private UserRepository repository;

	@GetMapping(path = "/")
	public @ResponseBody Iterable<User> getAllUsers() {
		return repository.findAll();
	}

	@PostMapping(path ="/add")
	public @ResponseBody User addUser(@RequestParam String email) {
		User user = new User();
		user.setEmail(email);

		return repository.save(user);
	}
}
