package ro.ubb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.model.ClosedLoan;
import ro.ubb.repository.ClosedLoanRepository;

@Service
public class ClosedLoanServiceImpl implements ClosedLoanService{

    private ClosedLoanRepository closedLoanRepository;

    @Override
    public ClosedLoan add(ClosedLoan closedLoan) {
        return closedLoanRepository.save(closedLoan);
    }

    @Autowired
    public void setClosedLoanRepository(ClosedLoanRepository closedLoanRepository) {
        this.closedLoanRepository = closedLoanRepository;
    }

}
