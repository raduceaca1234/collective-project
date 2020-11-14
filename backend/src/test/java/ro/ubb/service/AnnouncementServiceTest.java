package ro.ubb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;
import ro.ubb.repository.AnnouncementRepository;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class AnnouncementServiceTest {

    @Mock
    AnnouncementRepository announcementRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
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
        Announcement afterAdding = added;
        afterAdding.setId(1);
        when(announcementRepository.save(added)).thenReturn(afterAdding);
        Assertions.assertEquals(announcementRepository.save(added).getId(),1);
    }

}
