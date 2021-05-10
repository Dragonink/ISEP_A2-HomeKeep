package isep.webtechnologies.homekeep.controllers;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import isep.webtechnologies.homekeep.models.house.House;
import isep.webtechnologies.homekeep.models.house.HouseBooking;
import isep.webtechnologies.homekeep.models.house.HouseBookingRepository;
import isep.webtechnologies.homekeep.models.user.User;

@Controller
@RequestMapping(path = "/api/bookings")
public class HouseBookingRepositoryController {
	@Autowired
	private HouseBookingRepository repository;

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
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody HouseBooking addBooking(
		@RequestParam("house") House house,
		@RequestParam("booker") User booker,
		@RequestParam("isAvailable") Boolean isAvailable,
		@RequestParam("startDate") Date startDate,
		@RequestParam("endDate") Date endDate
	) {
		HouseBooking booking = new HouseBooking(house, booker, isAvailable, startDate, endDate);
		return repository.save(booking);
	}

	@DeleteMapping(path = "/{id}")
	public void deleteBooking(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
