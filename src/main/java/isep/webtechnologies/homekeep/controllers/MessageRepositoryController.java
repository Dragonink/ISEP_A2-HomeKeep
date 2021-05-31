package isep.webtechnologies.homekeep.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import isep.webtechnologies.homekeep.models.house.HouseBooking;
import isep.webtechnologies.homekeep.models.user.Message;
import isep.webtechnologies.homekeep.models.user.MessageRepository;
import isep.webtechnologies.homekeep.models.user.User;
import isep.webtechnologies.homekeep.models.user.UserRepository;

@Controller
@RequestMapping(path = "/api/messages")
public class MessageRepositoryController {
	@Autowired
	private MessageRepository repository;
	@Autowired
	private UserRepository repo;

	@RequestMapping(path = "/conversation/{id1}/{id2}")
	public @ResponseBody Iterable<Message> getConversation(
		@PathVariable Integer id1,
		@PathVariable Integer id2
	) {
		Optional<User> user1 = repo.findById(id1);
		Optional<User> user2 = repo.findById(id2);
		if (user1.isPresent() && user2.isPresent()) {
		return repository.findConversation(user1.get(), user2.get());
		}
		else return null;
	}

	@RequestMapping(path = "/inbox")
	public String getMessages(
			Model model
		) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Iterable<Message> msgs = repository.findMessages(user);
			model.addAttribute("msgs",msgs);
			return "/inbox";
	}
	
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody Message addMessage(
		@RequestParam("sender") User sender,
		@RequestParam("recipient") User recipient,
		@RequestParam("content") String content,
		@RequestParam("booking") HouseBooking booking
	) {
		Message message = new Message(sender, recipient, content, booking);
		return repository.save(message);
	}

	@DeleteMapping(path = "/{id}")
	public void deleteMessage(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
