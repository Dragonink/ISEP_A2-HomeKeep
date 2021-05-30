package isep.webtechnologies.homekeep.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@Controller
@RequestMapping(path = "/api/messages")
public class MessageRepositoryController {
	@Autowired
	private MessageRepository repository;

	@PostMapping(path = "/conversation")
	public @ResponseBody Iterable<Message> getConversation(
		@RequestParam("user1") User user1,
		@RequestParam("user2") User user2
	) {
		return repository.findConversation(user1, user2);
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
