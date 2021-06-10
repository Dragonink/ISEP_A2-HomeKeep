package isep.webtechnologies.homekeep.controllers;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import isep.webtechnologies.homekeep.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import isep.webtechnologies.homekeep.models.house.House;
import isep.webtechnologies.homekeep.models.house.HouseBooking;
import isep.webtechnologies.homekeep.models.house.HouseBooking.Status;
import isep.webtechnologies.homekeep.models.house.HouseBookingRepository;
import isep.webtechnologies.homekeep.models.user.Message;
import isep.webtechnologies.homekeep.models.user.MessageRepository;
import isep.webtechnologies.homekeep.models.user.User;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path = "/api/bookings")
public class HouseBookingRepositoryController {
	@Autowired
	private HouseBookingRepository repository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageRepository messageRepository;

	@GetMapping
	public @ResponseBody Iterable<HouseBooking> getAllBookings() {
		return repository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody Optional<HouseBooking> getBookingById(
		@PathVariable Integer id
	) {
		return repository.findById(id);
	}

	@PostMapping
	public RedirectView addBooking(
		@RequestParam("house") House house,
		@RequestParam("booker") User booker,
		@RequestParam("isAvailable") Boolean isAvailable,
		@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
		@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate
	) {
		HouseBooking booking = new HouseBooking(house, booker, isAvailable, startDate, endDate);
		booking = repository.save(booking);
		if (booker != house.getOwner()) {
			Message message = new Message(booker, house.getOwner(), null, booking);
			messageRepository.save(message);
		}
		return new RedirectView("/api/houses/"+house.getId());
	}

	@PatchMapping(path = "/{id}")
	public @ResponseBody Optional<HouseBooking> changeBookingStatus(
		@PathVariable Integer id,
		@RequestParam("status") String status
	) {
		//TODO update the bookings on the same dates
		return repository.findById(id)
			.map(booking -> {
				booking.setStatus(Status.valueOf(status.toUpperCase(Locale.ROOT)));
				return repository.save(booking);
			});
	}

	@GetMapping(path="/user")
	public String getUserBookings(Model model) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentUser = userRepository.findById(currentUser.getId()).orElseThrow();
		model.addAttribute("bookings", currentUser.getBookings());
		model.addAttribute("statuses", Status.values());
		return "/bookings";
	}

	@DeleteMapping(path = "/{id}")
	public void deleteBooking(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
