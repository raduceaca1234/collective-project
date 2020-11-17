package ro.ubb.repository;

import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ro.ubb.model.Announcement;
import ro.ubb.model.Image;

import java.util.List;

@ContextConfiguration(classes = RepositoryTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"})
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        imageRepository.save(Image.builder()
                .announcement(Announcement.builder().id(1).build())
                .imageBytes(ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY)
                .build());
        imageRepository.save(Image.builder()
                .announcement(Announcement.builder().id(2).build())
                .imageBytes(ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY)
                .build());
    }

    @Test
    public void testGetImagesOfAnnouncement(){
        List<Byte[]> bytes = imageRepository.getImageBytesForAnnouncement(1);
        Assertions.assertEquals(bytes.size(),1);
    }
}
