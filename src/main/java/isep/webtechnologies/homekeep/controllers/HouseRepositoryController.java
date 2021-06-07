package isep.webtechnologies.homekeep.controllers;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import isep.webtechnologies.homekeep.models.house.House;
import isep.webtechnologies.homekeep.models.house.HouseAmenities;
import isep.webtechnologies.homekeep.models.house.HouseRepository;
import isep.webtechnologies.homekeep.models.house.HouseRules;
import isep.webtechnologies.homekeep.models.user.User;
import isep.webtechnologies.homekeep.models.user.UserRepository;

@Controller
@RequestMapping(path = "/api/houses")
public class HouseRepositoryController {
	@Autowired
	private HouseRepository repository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public @ResponseBody Iterable<House> getAllHouses() {
		return repository.findAll();
	}

	@GetMapping(path = "/search")
	public String searchHouses(
		@RequestParam("title") Optional<String> title,
		@RequestParam("startDate") Optional<String> startDate,
		@RequestParam("endDate") Optional<String> endDate,
		@RequestParam("persons") Optional<Integer> persons,
		@RequestParam("babies") Optional<Integer> babies,
		Model model
	) {
		Stream<House> resultsByTitle = StreamSupport.stream(repository.searchByTitle(title).spliterator(), false);
		Function<String, Date> convertDate = date -> date.length() == 10 ? Date.valueOf(date) : null;
		Set<House> resultsByDate = StreamSupport.stream(repository.searchByDate(startDate.map(convertDate), endDate.map(convertDate)).spliterator(), false).collect(Collectors.toSet());
		Set<House> resultsByPersonNumber = StreamSupport.stream(repository.searchByPersonNumber(persons, babies).spliterator(), false).collect(Collectors.toSet());
		model.addAttribute("search", resultsByTitle
			.filter(house -> resultsByDate.contains(house) && resultsByPersonNumber.contains(house))
			.iterator()
		);
		return "index";
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody Optional<House> getHouseById(
		@PathVariable Integer id
	) {
		return repository.findById(id);
	}

	@GetMapping(path = "/user")
	public String userHouses(Model model) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Iterable<House> userHouses = repository.searchByOwner(Optional.of(currentUser.getId()));
		model.addAttribute("userHouses", userHouses);
		return "/houses";
	}

	@PostMapping(consumes = {"multipart/form-data"})
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody House addHouse(
		@RequestParam("title") String title
	) {
		User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		owner = userRepository.findById(owner.getId()).orElseThrow();
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
