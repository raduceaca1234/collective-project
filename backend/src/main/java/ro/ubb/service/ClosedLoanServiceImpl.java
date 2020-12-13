package ro.ubb.service;

import org.springframework.stereotype.Service;
import ro.ubb.model.ClosedLoan;
import ro.ubb.repository.ClosedLoanRepository;

@Service
public class ClosedLoanServiceImpl implements ClosedLoanService{

    ClosedLoanRepository closedLoanRepository;

    @Override
    public ClosedLoan add(ClosedLoan closedLoan) {
        return closedLoanRepository.save(closedLoan);
    }
}
