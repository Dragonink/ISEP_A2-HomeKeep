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
import org.springframework.web.servlet.view.RedirectView;

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
	private UserRepository UserRepo;




	@GetMapping(path = "/inbox")
	public String getMessages(
			Model model
		) {
			User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer currentId = currentUser.getId();
			Iterable<User> interlocutors = repository.findUsers(currentUser);
			model.addAttribute("interlocutors",interlocutors);
			model.addAttribute("currentId",currentId);
			return "/inbox";
	}

	@GetMapping(path = "/inbox/{id}")
	public String getConversation(
		@PathVariable Integer id,
		Model model
	) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User interlocutor = UserRepo.findById(id).orElseThrow();
		Iterable<Message> conversation = repository.findConversation(currentUser, interlocutor);
		Iterable<User> interlocutors = repository.findUsers(currentUser);
		model.addAttribute("interlocutors", interlocutors);
		model.addAttribute("conversation", conversation);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("interlocutor", interlocutor);
		return "/inbox";
	}


	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Message addMessage(
			@RequestParam ("content") String content,
			@RequestParam ("recipient") Integer recipient
			) {
		User sender = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer senderId = sender.getId();
		User recipientUser = UserRepo.findById(recipient).orElseThrow();
		User senderUser = UserRepo.findById(senderId).orElseThrow();
		Message message = new Message(senderUser, recipientUser, content,null);
		return repository.save(message);
	}



	@DeleteMapping(path = "/{id}")
	public void deleteMessage(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
