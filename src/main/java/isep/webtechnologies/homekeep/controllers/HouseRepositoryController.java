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
import isep.webtechnologies.homekeep.models.house.HouseAmenities;
import isep.webtechnologies.homekeep.models.house.HouseRepository;
import isep.webtechnologies.homekeep.models.house.HouseRules;
import isep.webtechnologies.homekeep.models.user.User;

@Controller
@RequestMapping(path = "/api/houses")
public class HouseRepositoryController {
	@Autowired
	private HouseRepository repository;

	@GetMapping
	public @ResponseBody Iterable<House> getAllHouses() {
		return repository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody Optional<House> getHouseById(
		@PathVariable Integer id
	) {
		return repository.findById(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody House addHouse(
		@RequestParam("owner") User owner,
		@RequestParam("title") String title
	) {
		House house = new House(owner, title);
		return repository.save(house);
	}

	@PatchMapping(path = "/{id}")
	public @ResponseBody Optional<House> editHouse(
		@PathVariable Integer id,
		@RequestParam("title") Optional<String> title,
		@RequestParam("description") Optional<String> description,
		@RequestParam("rules") Optional<HouseRules> rules,
		@RequestParam("amenities") Optional<HouseAmenities> amenities
	) {
		return repository.findById(id).map(house -> {
			title.ifPresent(value -> house.setTitle(value));
			description.ifPresent(value -> house.setDescription(value));
			rules.ifPresent(value -> house.setRules(value));
			amenities.ifPresent(value -> house.setAmenities(value));
			return repository.save(house);
		});
	}

	@DeleteMapping(path = "/{id}")
	public void deleteHouse(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
