package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmailAndPassword(String email, String password);

    @Query("select u.id from User u where u.email = :email and u.password = :password")
    Integer getIdOfUserWithCredentials(@Param("email") String email, @Param("password") String password);
}
