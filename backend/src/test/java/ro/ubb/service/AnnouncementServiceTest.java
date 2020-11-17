package ro.ubb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;
import ro.ubb.repository.AnnouncementRepository;

import java.sql.Array;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AnnouncementServiceTest {

    @Mock
    AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementService announcementService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetAnnouncements() {
        List<Announcement> announcements1 = Arrays.asList(
                new Announcement(1, User.builder().id(1).build(), "announcement1", "description1", "location1", new Date(System.currentTimeMillis() - 3600000), Category.TECH, 120, Status.OPEN, 25, new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new Announcement(2, User.builder().id(1).build(), "announcement2", "description2", "location2", new Date(System.currentTimeMillis() - 7200000), Category.SPORT, 240, Status.IN_DISCUSSION, 50, new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new Announcement(3, User.builder().id(1).build(), "announcement3", "description3", "location3", new Date(System.currentTimeMillis() - 9600000), Category.TECH, 300, Status.OPEN, 22, new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new Announcement(4, User.builder().id(1).build(), "announcement4", "description4", "location4", new Date(System.currentTimeMillis() - 2400000), Category.SPORT, 600, Status.IN_DISCUSSION, 66, new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new Announcement(5, User.builder().id(1).build(), "announcement5", "description5", "location5", new Date(System.currentTimeMillis() - 1800000), Category.TECH, 480, Status.OPEN, 55, new HashSet<>(), new HashSet<>(), new HashSet<>())
        );
        List<Announcement> announcements2 = Collections.singletonList(new Announcement(6, User.builder().id(1).build(), "announcement6", "description6", "location6", new Date(System.currentTimeMillis() - 6000000), Category.SPORT, 720, Status.IN_DISCUSSION, 43, new HashSet<>(), new HashSet<>(), new HashSet<>()));
        Page<Announcement> announcementPage1 = new PageImpl<>(announcements1);
        Page<Announcement> announcementPage2 = new PageImpl<>(announcements2);
        Pageable page1 = PageRequest.of(0, 5);
        Pageable page2 = PageRequest.of(1, 5);
        when(announcementRepository.findAll(page1)).thenReturn(announcementPage1);
        when(announcementRepository.findAll(page2)).thenReturn(announcementPage2);

        Assertions.assertEquals(5, announcementRepository.findAll(page1).getContent().size());
        Assertions.assertEquals(1, announcementRepository.findAll(page1).getContent().get(0).getId());
        Assertions.assertEquals(5, announcementRepository.findAll(page1).getContent().get(4).getId());
        Assertions.assertEquals(1, announcementRepository.findAll(page2).getContent().size());
        Assertions.assertEquals(6, announcementRepository.findAll(page2).getContent().get(0).getId());
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
