package isep.webtechnologies.homekeep.controllers;

import java.security.Principal;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import isep.webtechnologies.homekeep.models.house.*;
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

import isep.webtechnologies.homekeep.models.user.User;
import isep.webtechnologies.homekeep.models.user.UserRepository;

@Controller
@RequestMapping(path = "/api/houses")
public class HouseRepositoryController {
	@Autowired
	private HouseRepository repository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HouseImageRepository houseImageRepository;

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
		Function<String, Date> convertDate = date -> {
			try {
				return date.length() == 10 ? new SimpleDateFormat("yyyy-MM-dd").parse(date) : null;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		};
		Set<House> resultsByDate = StreamSupport.stream(repository.searchByDate(startDate.map(convertDate), endDate.map(convertDate)).spliterator(), false).collect(Collectors.toSet());
		Set<House> resultsByPersonNumber = StreamSupport.stream(repository.searchByPersonNumber(persons, babies).spliterator(), false).collect(Collectors.toSet());
		model.addAttribute("search", resultsByTitle
			.filter(house -> resultsByDate.contains(house) && resultsByPersonNumber.contains(house))
			.iterator()
		);
		return "index";
	}

	@GetMapping (path = "/{id}")
	public String getHouseById(@PathVariable Integer id, Model model){
		House house = repository.findById(id).orElseThrow();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = (principal instanceof User) ? userRepository.findById(((User) principal).getId()).orElseThrow() : null;
		if (house.getOwner() == currentUser) return "redirect:edit/"+id;
		model.addAttribute("house", house);
		model.addAttribute("currentUser", currentUser);
		return "/housedetails";
	}

	@GetMapping("/edit/{id}")
	public String editHouse(@PathVariable Integer id, Model model) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentUser = userRepository.findById(currentUser.getId()).orElseThrow();
		House house = repository.findById(id).orElseThrow();
		if (house.getOwner()==currentUser) model.addAttribute("house", house);
		return "/houses/edit";
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
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("country") String country,
			@RequestParam("region") String region,
			@RequestParam("rules") Optional<HouseRules> rules,
			@RequestParam("beds") Integer beds,
			@RequestParam("doubleBeds") Integer doubleBeds,
			@RequestParam("babyBeds") Integer babyBeds,
			@RequestParam("amenities") Optional<HouseAmenities> amenities,
			@RequestParam("images") List<Blob> images
	) {
		User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		owner = userRepository.findById(owner.getId()).orElseThrow();
		HouseAmenities houseAmenities = amenities.orElse(new HouseAmenities());
		houseAmenities.setBeds(beds);
		houseAmenities.setDoubleBeds(doubleBeds);
		houseAmenities.setBabyBeds(babyBeds);
		House house = new House(owner, title, description, new HouseLocation(country, region),
				rules.orElse(new HouseRules()), houseAmenities);
		repository.save(house);
		for (Blob blob: images) houseImageRepository.save(new HouseImage(house, blob));
		return house;
	}

	@PatchMapping(path = "/{id}")
	public @ResponseBody House editHouse(
		@PathVariable Integer id,
		@RequestParam("title") Optional<String> title,
		@RequestParam("description") Optional<String> description,
		@RequestParam("country") Optional<String> country,
		@RequestParam("region") Optional<String> region,
		@RequestParam("rules") Optional<HouseRules> rules,
		@RequestParam("beds") Optional<Integer> beds,
		@RequestParam("doubleBeds") Optional<Integer> doubleBeds,
		@RequestParam("babyBeds") Optional<Integer> babyBeds,
		@RequestParam("amenities") Optional<HouseAmenities> amenities
	) {
		House house = repository.findById(id).orElseThrow();
		title.ifPresent(house::setTitle);
		description.ifPresent(house::setDescription);
		HouseLocation houseLocation = new HouseLocation();
		country.ifPresent(houseLocation::setCountry);
		region.ifPresent(houseLocation::setRegion);
		house.setLocation(houseLocation);
		house.setRules(rules.orElse(new HouseRules()));
		HouseAmenities houseAmenities = amenities.orElse(new HouseAmenities());
		beds.ifPresent(houseAmenities::setBeds);
		doubleBeds.ifPresent(houseAmenities::setDoubleBeds);
		babyBeds.ifPresent(houseAmenities::setBabyBeds);
		house.setAmenities(houseAmenities);
		return repository.save(house);
	}

	@DeleteMapping(path = "/{id}")
	public void deleteHouse(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
