package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    @Query("select l from Loan l where l.discussion.interestedUser = :interestedUserId and l.discussion.interestedUser = :announcement")
    Loan getByAnnouncementAndInterestedUser(@Param("interested_user_id") int interestedUserId, @Param("announcement_id") int announcement);
}
