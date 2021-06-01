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
	private UserRepository UserRepo;
	

	

	@RequestMapping(path = "/inbox")
	public String getMessages(
			Model model
		) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Iterable<Message> msgs = repository.findMessages(user);
			model.addAttribute("msgs",msgs);
			return "/inbox";
	}
	
	@RequestMapping(path = "/inbox/{id}")
	public String getConversation(
		@PathVariable Integer id,
		Model model
	) {
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer currentId = user.getId();
		Iterable<Message> msgs = repository.findConversation(currentId, id);
		model.addAttribute("msgs",msgs);
		model.addAttribute("secondId",id);
		model.addAttribute("currentId",currentId);
		return "/inbox";
		
	}
	
	
	@PostMapping(path = "/api/messages/inbox/{id}",consumes = {"multipart/form-data"}	)
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody Message addMessage(
		@PathVariable Integer id,
		@RequestParam("content") String content
	) {
		User sender = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User recipient = UserRepo.findById(id).get();
		Message message = new Message(sender, recipient, content,null);
		return repository.save(message);
	}

	
	
	@DeleteMapping(path = "/{id}")
	public void deleteMessage(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
