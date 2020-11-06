package ro.ubb.service;

import ro.ubb.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    Announcement add(Announcement announcement);
    List<Announcement> getAll();
}
