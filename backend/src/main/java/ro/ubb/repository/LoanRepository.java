package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
