package ro.ubb.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ro.ubb.model.Announcement;

import java.util.List;

public interface AnnouncementRepository extends PagingAndSortingRepository<Announcement, Integer> {
}
