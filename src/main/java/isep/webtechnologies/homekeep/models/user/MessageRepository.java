package isep.webtechnologies.homekeep.models.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
	@Query(value = "SELECT m FROM Message m WHERE (m.sender.id = ?1 AND m.recipient.id = ?2) OR (m.sender.id = ?2 AND m.recipient.id = ?1)")
	Iterable<Message> findConversation(Integer id1, Integer id2);
	
	@Query(value = "SELECT m FROM Message m WHERE m.sender = ?1 OR m.recipient = ?1")
	Iterable<Message> findMessages(User user);
	
	}
