package isep.webtechnologies.homekeep.models.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
	@Query(value = "SELECT m FROM Message m WHERE (m.sender = ?1 AND m.recipient = ?2) OR (m.sender = ?2 AND m.recipient = ?1)")
	Iterable<Message> findConversation(User user1, User user2);
	
	@Query(value = "SELECT m FROM Message m WHERE m.sender = ?1 OR m.recipient = ?1")
	Iterable<Message> findMessages(User user);
	
	}
