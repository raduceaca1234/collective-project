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
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void getAllTest() {
        Assertions.assertEquals(2, adminRepository.findAll().size());
    }

}
