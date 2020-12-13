package ro.ubb.service;

import ro.ubb.model.Announcement;
import ro.ubb.model.Discussion;
import ro.ubb.model.User;

import java.util.List;

public interface DiscussionService {

    Discussion add(Discussion discussion);

    Discussion getByAnnouncementAndInterestedUser(User user, Announcement announcement);

    List<Discussion> getAllByAnnouncement(Announcement announcement);
}
