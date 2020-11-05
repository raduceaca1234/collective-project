package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.ubb.model.Announcement;
import ro.ubb.repository.AnnouncementRepository;

import java.util.List;

@Service
@Slf4j
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }


    @Override
    public Announcement add(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public List<Announcement> getAll() {
        return announcementRepository.findAll();
    }
}
