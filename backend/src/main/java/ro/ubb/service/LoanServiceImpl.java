package ro.ubb.service;

import org.springframework.stereotype.Service;
import ro.ubb.model.Announcement;
import ro.ubb.model.Loan;
import ro.ubb.model.User;
import ro.ubb.repository.LoanRepository;

@Service
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;

    @Override
    public Loan add(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Loan getByAnnouncementAndInterestedUser(User user, Announcement announcement) {
        return loanRepository.getByAnnouncementAndInterestedUser(user.getId(), announcement.getId());
    }
}
