package ro.ubb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(classes = ServiceTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUserExistsById_exists(){
        Assertions.assertTrue(userService.existsById(1));
    }

    @Test
    public void testUserExistsById_nonExists(){
        Assertions.assertFalse(userService.existsById(-1));
    }
}
