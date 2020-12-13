package ro.ubb.service;

import org.springframework.stereotype.Service;
import ro.ubb.model.Discussion;
import ro.ubb.repository.DiscussionRepository;

@Service
public class DiscussionServiceImpl implements DiscussionService{

    DiscussionRepository discussionRepository;

    @Override
    public Discussion add(Discussion discussion) {
        return discussionRepository.save(discussion);
    }
}
