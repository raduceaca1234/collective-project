package ro.ubb.service;

import ro.ubb.model.Announcement;
import ro.ubb.model.Loan;
import ro.ubb.model.User;

public interface LoanService {

    Loan add(Loan loan);

    Loan getByAnnouncementAndInterestedUser(User user, Announcement announcement);
}
