package ro.ubb.service;

import org.springframework.stereotype.Service;
import ro.ubb.model.Announcement;
import ro.ubb.model.Discussion;
import ro.ubb.model.User;
import ro.ubb.repository.DiscussionRepository;

import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService{

    DiscussionRepository discussionRepository;

    @Override
    public Discussion add(Discussion discussion) {
        return discussionRepository.save(discussion);
    }

    @Override
    public Discussion getByAnnouncementAndInterestedUser(User user, Announcement announcement) {
        return discussionRepository.getByAnnouncementAndInterestedUser(user.getId(), announcement.getId());
    }

    @Override
    public List<Discussion> getAllByAnnouncement(Announcement announcement) {
        return discussionRepository.getAllByAnnouncement(announcement);
    }
}
