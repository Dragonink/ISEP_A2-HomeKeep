package isep.webtechnologies.homekeep.models.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
	@Query(value = "SELECT m FROM Message m WHERE (m.sender.id = ?1 AND m.recipient.id = ?2) OR (m.sender.id = ?2 AND m.recipient.id = ?1)")
	Iterable<Message> findConversation(Integer id1, Integer id2);
	
	@Query(value = "SELECT m FROM Message m WHERE m.sender = ?1 OR m.recipient = ?1")
	Iterable<Message> findMessages(User user);
	
	//@Query(nativeQuery = true,value = "SELECT m1 FROM message AS m1 INNER JOIN SELECT LEAST(sender_id, recipient_id)  AS sender_id  GREATEST(sender_id, recipient_id) AS receiver_id, MAX(id) AS max_id FROM message GROUP BY LEAST(sender_id, receiver_id) GREATEST(sender_id, receiver_id)) AS m2 ON LEAST(m1.sender_id, m1.recipient_id) = m2.sender_id AND GREATEST(m1.sender_id, m1.recipient_id) = m2.receiver_id AND m1.id = m2.max_id WHERE m1.sender_id = ?1 OR m1.recipient_id = ?1")
	//Iterable<Message> groupMessages(User user);
	
	@Query(value = "SELECT DISTINCT u FROM User u WHERE u IN (SELECT m.recipient FROM Message m WHERE m.sender = ?1) OR u IN (SELECT DISTINCT m1.sender FROM Message m1 WHERE m1.recipient = ?1)")
	Iterable<User> findUsers(User id);
}
