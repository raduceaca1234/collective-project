package ro.ubb.service;

import ro.ubb.model.Announcement;
import ro.ubb.model.Loan;
import ro.ubb.model.User;

import java.util.List;

public interface LoanService {

    Loan add(Loan loan);

    Loan getByAnnouncementAndInterestedUser(User user, Announcement announcement);

    Loan getLoansByAnnouncementId(Integer id);
}
