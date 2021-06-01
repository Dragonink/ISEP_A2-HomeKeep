package isep.webtechnologies.homekeep.models.house;

import java.sql.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

import isep.webtechnologies.homekeep.models.user.User;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<House, Integer> {
	default Iterable<House> searchByTitle(Optional<String> title) {
		return () -> StreamSupport.stream(this.findAll().spliterator(), false)
			.filter(house -> title
				.map(str -> house.getTitle().contains(str))
				.orElse(true)
			)
			.iterator();
	}

	default Iterable<House> searchByDate(Optional<Date> startDate, Optional<Date> endDate) {
		return () -> StreamSupport.stream(this.findAll().spliterator(), false)
			.filter(house -> house.getBookings().size() == 0 || house.getBookings().stream().anyMatch(booking ->
				booking.isAvailable() &&
				startDate
					.map(date -> booking.getStartDate().compareTo(date) <= 0)
					.orElse(true) &&
				endDate
					.map(date -> booking.getEndDate().compareTo(date) >= 0)
					.orElse(true)
			))
			.iterator();
	}

	default Iterable<House> searchByPersonNumber(Optional<Integer> persons, Optional<Integer> babies) {
		return () -> StreamSupport.stream(this.findAll().spliterator(), false)
			.filter(house -> Optional.ofNullable(house.getAmenities()).map(amenities ->
				persons
					.map(number -> Optional.ofNullable(amenities.getBeds()).orElse(0) + 2 * Optional.ofNullable(amenities.getDoubleBeds()).orElse(0) >= number)
					.orElse(true) &&
				babies
					.map(number -> Optional.ofNullable(amenities.getBabyBeds()).orElse(0) >= number)
					.orElse(true)
			).orElse(true))
			.iterator();
	}

	default Iterable<House> searchByOwner(Optional<Integer> ownerId) {
		Spliterator<House> houses = this.findAll().spliterator();
		return () -> StreamSupport.stream(houses, false)
				.filter(house -> ownerId
						.map(id -> house.getOwner().getId().equals(id))
						.orElse(true)
				)
				.iterator();
	}
}
