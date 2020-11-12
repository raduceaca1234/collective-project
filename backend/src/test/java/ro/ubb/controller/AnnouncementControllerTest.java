package ro.ubb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;
import ro.ubb.service.AnnouncementService;
import ro.ubb.service.ImageService;
import ro.ubb.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnnouncementController.class)
public class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementService announcementService;
    @MockBean
    private ImageService imageService;
    @MockBean
    private UserService userService;
    @MockBean
    private DtoConverter dtoConverter;


    @Test
    public void testPostAnnouncement_userExists() throws Exception {
        AnnouncementDto dto = AnnouncementDto.builder()
                .ownerId(1)
                .name("test_name1")
                .description("test_description1")
                .location("test_location1")
                .category("AGRICULTURE")
                .status("OPEN")
                .duration(30)
                .pricePerDay(50)
                .build();
        Announcement announcement = Announcement.builder().user(User.builder()
                .id(1).build())
                .name("test_name1")
                .description("test_description1")
                .location("test_location1")
                .category(Category.AGRICULTURE)
                .duration(30)
                .pricePerDay(50)
                .status(Status.OPEN)
                .build();

        given(userService.existsById(any(Integer.class))).willReturn(true);
        given(dtoConverter.convertAnnouncementDtoForPosting(any(AnnouncementDto.class))).willReturn(announcement);
        given(announcementService.add(any(Announcement.class))).willReturn(Announcement.builder().id(3).build());
        mockMvc.perform(
                post("/api/announcement")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("ownerId", String.valueOf(1))
                        .param("name","test_name1")
                        .param("description","test_description1")
                        .param("location","test_location1")
                        .param("category", "AGRICULTURE")
                        .param("duration","30")
                        .param("pricePerDay","50"))
                .andExpect(status().isCreated());

        verify(userService).existsById(1);
        verify(dtoConverter).convertAnnouncementDtoForPosting(dto);
        verify(announcementService).add(announcement);
    }

    @Test
    public void testPostAnnouncement_userDoesNotExist() throws Exception {
        AnnouncementDto dto = AnnouncementDto.builder()
                .ownerId(100)
                .name("test_name1")
                .description("test_description1")
                .location("test_location1")
                .category("AGRICULTURE")
                .status("OPEN")
                .duration(30)
                .pricePerDay(50)
                .build();
        Announcement announcement = Announcement.builder().user(User.builder()
                .id(100).build())
                .name("test_name1")
                .description("test_description1")
                .location("test_location1")
                .category(Category.AGRICULTURE)
                .duration(30)
                .pricePerDay(50)
                .status(Status.OPEN)
                .build();
        given(dtoConverter.convertAnnouncementDtoForPosting(any(AnnouncementDto.class))).willReturn(announcement);
        given(userService.existsById(any(Integer.class))).willReturn(false);
        mockMvc.perform(
                post("/api/announcement")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("ownerId", String.valueOf(100))
                        .param("name","test_name1")
                        .param("description","test_description1")
                        .param("location","test_location1")
                        .param("category", "AGRICULTURE")
                        .param("duration","30")
                        .param("pricePerDay","50"))
                .andExpect(status().isNotFound());
        verify(dtoConverter).convertAnnouncementDtoForPosting(dto);
        verify(userService).existsById(announcement.getUser().getId());
    }

    @Test
    public void testGetAnnouncementById_Exists() throws Exception {
        Announcement announcement = Announcement.builder()
                .id(1)
                .user(User.builder().id(1).build())
                .name("name1")
                .description("description1")
                .location("location1")
                .category(Category.AGRICULTURE)
                .duration(3)
                .pricePerDay(50)
                .status(Status.OPEN)
                .build();
        AnnouncementDto dto = AnnouncementDto.builder()
                .id(1)
                .ownerId(1)
                .name("name1")
                .description("description1")
                .location("location1")
                .category("AGRICULTURE")
                .status("OPEN")
                .duration(3)
                .pricePerDay(50)
                .build();
        given(announcementService.getById(any(Integer.class))).willReturn(announcement);
        given(imageService.getBytesForAnnouncement(any(Integer.class))).willReturn(new ArrayList<>());
        given(dtoConverter.convertAnnouncementWithImages(any(Announcement.class), any(List.class))).willReturn(dto);
        mockMvc.perform(
                    get("/api/announcement/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.pricePerDay").value(50));
        verify(announcementService).getById(1);
        verify(imageService).getBytesForAnnouncement(1);
        verify(dtoConverter).convertAnnouncementWithImages(announcement,new ArrayList<>());
    }

    @Test
    public void testGetAnnouncementById_DoesntExist() throws Exception {
        Announcement announcement = Announcement.builder()
                .id(-1)
                .build();
        given(announcementService.getById(any(Integer.class))).willReturn(announcement);
        mockMvc.perform(
                get("/api/announcement/{id}",10))
                .andExpect(status().isNotFound());
        verify(announcementService).getById(10);
    }
}
