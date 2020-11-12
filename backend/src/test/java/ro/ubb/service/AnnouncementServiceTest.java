package ro.ubb.service;

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

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@ContextConfiguration(classes = ServiceTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"})
public class AnnouncementServiceTest {

    @Autowired
    private AnnouncementService announcementService;

    @Test
    public void testGetAnnouncements() {
        List<Announcement> announcements = Arrays.asList(
                new Announcement(1, User.builder().id(1).build(), "announcement1", "description1", "location1", new Date(System.currentTimeMillis() - 3600000), Category.TECH, 120, Status.OPEN, 25, new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new Announcement(2, User.builder().id(1).build(), "announcement2", "description2", "location2", new Date(System.currentTimeMillis() - 7200000), Category.SPORT, 240, Status.IN_DISCUSSION, 50, new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new Announcement(3, User.builder().id(1).build(), "announcement3", "description3", "location3", new Date(System.currentTimeMillis() - 9600000), Category.TECH, 300, Status.OPEN, 22, new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new Announcement(4, User.builder().id(1).build(), "announcement4", "description4", "location4", new Date(System.currentTimeMillis() - 2400000), Category.SPORT, 600, Status.IN_DISCUSSION, 66, new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new Announcement(5, User.builder().id(1).build(), "announcement5", "description5", "location5", new Date(System.currentTimeMillis() - 1800000), Category.TECH, 480, Status.OPEN, 55, new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new Announcement(6, User.builder().id(1).build(), "announcement6", "description6", "location6", new Date(System.currentTimeMillis() - 6000000), Category.SPORT, 720, Status.IN_DISCUSSION, 43, new HashSet<>(), new HashSet<>(), new HashSet<>())
        );
        announcements.forEach(a -> announcementService.add(a));
        List<Announcement> result = announcementService.getAll();
        Assertions.assertEquals(6, result.size());
        Assertions.assertEquals(result.get(0).getId(), announcements.get(0).getId());
        Assertions.assertEquals(result.get(0).getName(), announcements.get(0).getName());
        Assertions.assertEquals(result.get(5).getId(), announcements.get(5).getId());
    }

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
        Assertions.assertEquals(added.getName(), announcementService.getAll().get(2).getName());
        Assertions.assertEquals(afterAdding.getCategory(), Category.AGRICULTURE);
        Assertions.assertEquals(announcementService.getAll().size(),3);
    }

}
