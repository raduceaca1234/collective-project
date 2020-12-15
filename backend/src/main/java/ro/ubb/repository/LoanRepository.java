package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.model.Loan;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    @Query("select l from Loan l where l.discussion.interestedUser.id = :interested_user_id and l.discussion.discussedAnnouncement.id = :announcement_id")
    Loan getByAnnouncementAndInterestedUser(@Param("interested_user_id") int interestedUserId, @Param("announcement_id") int announcement);

    @Query("SELECT l FROM Loan l LEFT JOIN ClosedLoan cl ON l.id = cl.loan.id WHERE l.discussion.discussedAnnouncement.id = :id AND cl.loan.id is null")
    Loan findByAnnouncement(@Param("id") Integer id);
}
