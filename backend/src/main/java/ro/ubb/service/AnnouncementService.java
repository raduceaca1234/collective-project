package ro.ubb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.ubb.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    Announcement add(Announcement announcement);
    List<Announcement> getAll();
    Page<Announcement> getAllPaged(Pageable pageable);
}
