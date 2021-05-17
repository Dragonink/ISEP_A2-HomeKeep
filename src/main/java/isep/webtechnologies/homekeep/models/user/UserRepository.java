package isep.webtechnologies.homekeep.models.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query(value = "SELECT u FROM User u WHERE u.email = ?1")
	Optional<User> findByEmail(String email);
}
