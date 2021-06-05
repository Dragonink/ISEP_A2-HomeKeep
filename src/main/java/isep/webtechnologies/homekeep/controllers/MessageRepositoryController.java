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
	

	

	@RequestMapping(path = "/inbox")
	public String getMessages(
			Model model
		) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer currId = user.getId();
			Iterable<User> usrs = repository.findUsers(user);
			model.addAttribute("usrs",usrs);
			model.addAttribute("currentId",currId);
			return "/inbox";
	}
	
	@RequestMapping(path = "/inbox/{id}")
	public String getConversation(
		@PathVariable Integer id,
		Model model
	) {
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer currentId = user.getId();
		User recipient = UserRepo.findById(id).get();
		Iterable<Message> msgs = repository.findMessages(user);
		Iterable<User> usrs = repository.findUsers(user);
		model.addAttribute("usrs",usrs);
		model.addAttribute("msgs",msgs);
		model.addAttribute("secondId",id);
		model.addAttribute("currentId",currentId);
		model.addAttribute("recipient",recipient);
		return "/inbox";
		
	}
	
	
	@PostMapping(value = "inbox/{id}")
	public RedirectView addMessage(
			@PathVariable Integer id,
			@RequestParam ("content") String content,
			@RequestParam ("recipient") Integer recipient
			) {
		User sender = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer senderId = sender.getId();
		User recipientUser = UserRepo.findById(recipient).get();
		User senderUser = UserRepo.findById(senderId).get();
		Message message = new Message(senderUser, recipientUser, content,null);
		repository.save(message);
		return new RedirectView("/api/messages/inbox");
	
	}

	
	
	@DeleteMapping(path = "/{id}")
	public void deleteMessage(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
