package ro.ubb.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;

@ContextConfiguration(classes = ServiceTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"})
public class AnnouncementServiceTest {

    @Autowired
    private AnnouncementServiceImpl announcementService;

    @Test
    public void testAddAnnouncement(){
        Announcement added = Announcement.builder()
                .user(User.builder().id(1).build())
                .name("TestName1")
                .description("TestDescription1")
                .location("TestLocation1")
                .duration(30)
                .category(Category.AGRICULTURE)
                .status(Status.OPEN)
                .build();
        Announcement afterAdding = announcementService.add(added);
        Assert.assertEquals(added.getName(), announcementService.getAll().get(2).getName());
        Assert.assertEquals(afterAdding.getCategory(), Category.AGRICULTURE);
    }

    @Test()
    public void testAddAnnouncementNonExistingUser(){
        Announcement added = Announcement.builder()
                .user(User.builder().id(5).build())
                .name("TestName1")
                .description("TestDescription1")
                .location("TestLocation1")
                .duration(30)
                .category(Category.AGRICULTURE)
                .status(Status.OPEN)
                .build();
        Assertions.assertThrows(RuntimeException.class,
                ()->announcementService.add(added));
    }
}
