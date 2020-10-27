package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}
