package ro.ubb.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.AnnotationConfigContextLoader;



@ContextConfiguration(classes = ValidatorTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"})
class UserValidatorTest {
    @Autowired UserValidator userValidator;

    @Test
    public void goodPassword(){
        Assertions.assertTrue(userValidator.validatePassword("Gloria!123"));
    }
    @Test
    public void badPassword(){
        Assertions.assertFalse(userValidator.validatePassword("pass"));
    }

}
