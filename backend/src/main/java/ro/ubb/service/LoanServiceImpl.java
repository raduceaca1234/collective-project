package ro.ubb.service;

import org.springframework.stereotype.Service;
import ro.ubb.model.Loan;
import ro.ubb.repository.LoanRepository;

@Service
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;

    @Override
    public Loan add(Loan loan) {
        return loanRepository.save(loan);
    }
}
