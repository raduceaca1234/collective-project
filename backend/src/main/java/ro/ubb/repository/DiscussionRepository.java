package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.model.Discussion;

public interface DiscussionRepository extends JpaRepository<Discussion, Integer> {
}
