package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.ubb.model.Announcement;
import ro.ubb.repository.AnnouncementRepository;


import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AnnouncementServiceImpl implements AnnouncementService {

    private AnnouncementRepository announcementRepository;


    @Override
    public Announcement add(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public List<Announcement> getAll() {
        List<Announcement> announcements = new ArrayList<>();
        announcementRepository.findAll().forEach(announcements::add);
        return announcements;
    }

    @Override
    public Page<Announcement> getAllPaged(Pageable pageable) {
        return announcementRepository.findAll(pageable);
    }

    @Override
    public Announcement getById(int id) {
        return announcementRepository.findById(id).orElse(Announcement.builder().id(-1).build());
    }

    @Override
    public boolean existsById(int id) {
        return announcementRepository.existsById(id);
    }

    @Autowired
    public void setAnnouncementRepository(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }
}
