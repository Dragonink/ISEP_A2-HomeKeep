package isep.webtechnologies.homekeep.controllers;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import isep.webtechnologies.homekeep.models.house.HouseImage;
import isep.webtechnologies.homekeep.models.house.HouseImageRepository;

@Controller
@RequestMapping(path = "/api/houseimages")
public class HouseImageRepositoryController {
	@Autowired
	private HouseImageRepository repository;

	@GetMapping
	public @ResponseBody Iterable<HouseImage> getAllImages() {
		return repository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody Optional<HouseImage> getImageById(
		@PathVariable Integer id
	) {
		return repository.findById(id);
	}

	@RequestMapping(value = "/image/{houseImageId}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("houseImageId") int houseImageId) throws SQLException {
		Blob imageBlob = getImageById(houseImageId).orElseThrow().getImage();
		byte[] imageContent = imageBlob.getBytes(1, (int) imageBlob.length());
		imageBlob.free();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody HouseImage addImage(
		@RequestParam("house") House house,
		@RequestParam("image") Blob image
	) {
		HouseImage houseImage = new HouseImage(house, image);
		return repository.save(houseImage);
	}

	@DeleteMapping(path = "/{id}")
	public void deleteImage(
		@PathVariable Integer id
	) {
		repository.deleteById(id);
	}
}
