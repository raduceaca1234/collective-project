package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.model.ClosedLoan;

public interface ClosedLoanRepository extends JpaRepository<ClosedLoan, Integer> {
}
