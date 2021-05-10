package isep.webtechnologies.homekeep.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import isep.webtechnologies.homekeep.models.house.House;
import isep.webtechnologies.homekeep.models.house.HouseRating;
import isep.webtechnologies.homekeep.models.house.HouseRatingRepository;
import isep.webtechnologies.homekeep.models.user.User;

@Controller
@RequestMapping(path = "/api/ratings")
public class HouseRatingRepositoryController {
	@Autowired
	private HouseRatingRepository repository;

	@GetMapping
	public @ResponseBody Iterable<HouseRating> getAllRatings() {
		return repository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody Optional<HouseRating> getRatingById(
		@PathVariable Integer id
	) {
		return repository.findById(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody HouseRating addRating(
		@RequestParam("house") House house,
		@RequestParam("user") User user,
		@RequestParam("value") Double value
	) {
		HouseRating rating = new HouseRating(house, user, value);
		return repository.save(rating);
	}

	@PatchMapping(path = "/{id}")
	public @ResponseBody Optional<HouseRating> editRating(
		@PathVariable Integer id,
		@RequestParam("value") Double value
	) {
		return repository.findById(id).map(rating -> {
			rating.setValue(value);
			return repository.save(rating);
		});
	}

	@DeleteMapping(path = "/{id}")
	public void deleteRating(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
