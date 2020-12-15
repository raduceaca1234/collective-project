package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.model.Announcement;
import ro.ubb.model.Discussion;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Integer> {

    @Query("select d from Discussion d where d.interestedUser.id = :interested_user_id and d.discussedAnnouncement.id = :announcement_id")
    Discussion getByAnnouncementAndInterestedUser(@Param("interested_user_id") int interestedUserId, @Param("announcement_id") int announcement);

    @Query("select d from Discussion d where d.discussedAnnouncement = :announcement")
    List<Discussion> getAllByAnnouncement(Announcement announcement);

    @Query("select d from Discussion d where d.discussedAnnouncement.id = :announcement_id")
    List<Discussion> getAllByAnnouncementId(@Param("announcement_id") int announcementId);
}
