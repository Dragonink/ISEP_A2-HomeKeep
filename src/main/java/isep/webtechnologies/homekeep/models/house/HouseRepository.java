package isep.webtechnologies.homekeep.models.house;

import java.sql.Date;
import java.util.stream.StreamSupport;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<House, Integer> {
	@Query(value = "SELECT h FROM House h WHERE h.title LIKE '%?1%'")
	Iterable<House> searchByTitle(String title);

	default Iterable<House> searchByDate(Date startDate, Date endDate) {
		return () -> StreamSupport.stream(this.findAll().spliterator(), false)
			.filter(house -> house.getBookings().stream()
				.filter(booking -> booking.isAvailable())
				.anyMatch(booking -> booking.getStartDate().compareTo(startDate) <= 0 && booking.getEndDate().compareTo(endDate) >= 0)
			)
			.iterator();
	}

	default Iterable<House> searchByPersonNumber(Integer persons, Integer babies) {
		return () -> StreamSupport.stream(this.findAll().spliterator(), false)
			.filter(house -> house.getAmenities().getBeds() + 2 * house.getAmenities().getDoubleBeds() >= persons && house.getAmenities().getBabyBeds() >= babies)
			.iterator();
	}
}
