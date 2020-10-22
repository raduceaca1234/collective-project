package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

}
