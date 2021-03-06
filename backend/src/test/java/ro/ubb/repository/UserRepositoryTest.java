package ro.ubb.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(classes = RepositoryTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"})
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;

    @Test
    public void existsByEmailAndPassword_userExists(){
        Assertions.assertTrue(userRepository.existsByEmailAndPassword("user_email1", "user_pass1"));
    }

    @Test
    public void existsByEmailAndPassword_userDoesNotExist(){
        Assertions.assertFalse(userRepository.existsByEmailAndPassword("NOT EXISTING", "NOT EXISTING"));
    }

    @Test
    public void getIdOfUserWithCredentials_userExists(){
        Assertions.assertEquals(1, userRepository.getUserWithCredentials("user_email1", "user_pass1").getId());
        Assertions.assertEquals("user_email1", userRepository.getUserWithCredentials("user_email1", "user_pass1").getEmail());

    }

    @Test
    public void getIdOfUserWithCredentials_userDoesNotExist(){
        Assertions.assertNull(userRepository.getUserWithCredentials("NOT EXISTING", "NOT EXISTING"));
    }


}
