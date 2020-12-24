package ro.ubb.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.dto.BytesAnnouncementDto;
import ro.ubb.dto.OrderingAndFilteringDto;
import ro.ubb.dto.PagedAnnouncementDto;
import ro.ubb.model.*;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Order;
import ro.ubb.model.enums.Status;
import ro.ubb.security.JWTUtil;
import ro.ubb.service.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnnouncementController.class)
class AnnouncementControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private AnnouncementService announcementService;
  @MockBean private ImageService imageService;
  @MockBean private UserService userService;
  @MockBean private DiscussionService discussionService;
  @MockBean private LoanService loanService;
  @MockBean private ClosedLoanService closedLoanService;
  @MockBean private DtoConverter dtoConverter;
  @MockBean private JWTUtil jwtUtil;

  @Test
  void testGetAnnouncementsPaginated() throws Exception {
    List<Announcement> announcements = getAnnouncements();
    List<PagedAnnouncementDto> announcementDtos = getAnnouncementsDto();

    Pageable pageable1 = PageRequest.of(0, 5);
    Pageable pageable2 = PageRequest.of(1, 5);
    Pageable pageable3 = PageRequest.of(2, 5);
    given(announcementService.getAll()).willReturn(announcements);
    given(announcementService.getAllPaged(pageable1))
        .willReturn(new PageImpl<>(announcements.subList(0, 5)));
    given(announcementService.getAllPaged(pageable2))
        .willReturn(new PageImpl<>(announcements.subList(5, 10)));
    given(announcementService.getAllPaged(pageable3))
        .willReturn(new PageImpl<>(announcements.subList(10, 11)));
    IntStream.range(0, announcements.size())
        .forEach(
            i ->
                given(dtoConverter.convertAnnouncementForGetPaginated(announcements.get(i)))
                    .willReturn(announcementDtos.get(i)));

    String result =
        mockMvc
            .perform(get("/api/announcement/1/5"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    verify(announcementService).getAllPaged(any(Pageable.class));
    verify(dtoConverter).convertAnnouncementForGetPaginated(announcementService.getAll().get(5));
    verify(dtoConverter).convertAnnouncementForGetPaginated(announcementService.getAll().get(9));
  }

  private List<PagedAnnouncementDto> getAnnouncementsDto() {
    return Arrays.asList(
        new PagedAnnouncementDto(
            1, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 1, 1),
        new PagedAnnouncementDto(
            2, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 1, 1),
        new PagedAnnouncementDto(
            3, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 1, 1),
        new PagedAnnouncementDto(
            4, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 1, 1),
        new PagedAnnouncementDto(
            5, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 1, 1),
        new PagedAnnouncementDto(
            6, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 2, 1),
        new PagedAnnouncementDto(
            7, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 2, 1),
        new PagedAnnouncementDto(
            8, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 2, 1),
        new PagedAnnouncementDto(
            9, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 2, 1),
        new PagedAnnouncementDto(
            10, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 2, 1),
        new PagedAnnouncementDto(
            11, "announcement1", "description1", "location1", "", "", 120, "open", 25, "1", 3, 1));
  }

  private List<Announcement> getAnnouncements() {
    return Arrays.asList(
        new Announcement(
            1,
            User.builder().id(1).build(),
            "announcement1",
            "description1",
            "location1",
            new Date(System.currentTimeMillis() - 3600000),
            Category.TECH,
            120,
            Status.OPEN,
            25,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            2,
            User.builder().id(1).build(),
            "announcement2",
            "description2",
            "location2",
            new Date(System.currentTimeMillis() - 7200000),
            Category.SPORT,
            240,
            Status.IN_DISCUSSION,
            50,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            3,
            User.builder().id(1).build(),
            "announcement3",
            "description3",
            "location3",
            new Date(System.currentTimeMillis() - 9600000),
            Category.TECH,
            300,
            Status.OPEN,
            22,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            4,
            User.builder().id(2).build(),
            "announcement4",
            "description4",
            "location4",
            new Date(System.currentTimeMillis() - 2400000),
            Category.SPORT,
            600,
            Status.IN_DISCUSSION,
            66,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            5,
            User.builder().id(2).build(),
            "announcement5",
            "description5",
            "location5",
            new Date(System.currentTimeMillis() - 1800000),
            Category.TECH,
            480,
            Status.OPEN,
            55,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            6,
            User.builder().id(2).build(),
            "announcement6",
            "description6",
            "location6",
            new Date(System.currentTimeMillis() - 6000000),
            Category.SPORT,
            720,
            Status.IN_DISCUSSION,
            43,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            7,
            User.builder().id(3).build(),
            "announcement7",
            "description7",
            "location7",
            new Date(System.currentTimeMillis() - 3600000),
            Category.TECH,
            120,
            Status.OPEN,
            25,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            8,
            User.builder().id(3).build(),
            "announcement8",
            "description8",
            "location8",
            new Date(System.currentTimeMillis() - 7200000),
            Category.SPORT,
            240,
            Status.IN_DISCUSSION,
            50,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            9,
            User.builder().id(3).build(),
            "announcement9",
            "description9",
            "location9",
            new Date(System.currentTimeMillis() - 9600000),
            Category.TECH,
            300,
            Status.OPEN,
            22,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            10,
            User.builder().id(4).build(),
            "announcement10",
            "description10",
            "location10",
            new Date(System.currentTimeMillis() - 2400000),
            Category.SPORT,
            600,
            Status.IN_DISCUSSION,
            66,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()),
        new Announcement(
            11,
            User.builder().id(4).build(),
            "announcement11",
            "description11",
            "location11",
            new Date(System.currentTimeMillis() - 1800000),
            Category.TECH,
            480,
            Status.OPEN,
            55,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>()));
  }

  @Test
  void testPostAnnouncement_userExists() throws Exception {
    AnnouncementDto dto =
        AnnouncementDto.builder()
            .ownerToken("token")
            .name("test_name1")
            .description("test_description1")
            .location("test_location1")
            .category("AGRICULTURE")
            .status("OPEN")
            .duration(30)
            .pricePerDay(50)
            .build();
    Announcement announcement =
        Announcement.builder()
            .user(User.builder().id(1).build())
            .name("test_name1")
            .description("test_description1")
            .location("test_location1")
            .category(Category.AGRICULTURE)
            .duration(30)
            .pricePerDay(50)
            .status(Status.OPEN)
            .build();
    Claims claims = new DefaultClaims();
    claims.setId("1");
    given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
    given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
    given(userService.existsById(any(Integer.class))).willReturn(true);
    given(dtoConverter.convertAnnouncementDtoForPosting(any(AnnouncementDto.class)))
        .willReturn(announcement);
    given(announcementService.add(any(Announcement.class)))
        .willReturn(Announcement.builder().id(3).build());
    mockMvc
        .perform(
            post("/api/announcement")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("ownerId", jwtUtil.createJWT(1, JWTUtil.DEFAULT_VALIDITY))
                .param("name", "test_name1")
                .param("description", "test_description1")
                .param("location", "test_location1")
                .param("category", "AGRICULTURE")
                .param("duration", "30")
                .param("pricePerDay", "50"))
        .andExpect(status().isCreated());
    verify(userService).existsById(1);
    verify(dtoConverter).convertAnnouncementDtoForPosting(dto);
    verify(announcementService).add(announcement);
  }

  @Test
  void testPostAnnouncement_userDoesNotExist() throws Exception {
    AnnouncementDto dto =
        AnnouncementDto.builder()
            .ownerToken("token")
            .name("test_name1")
            .description("test_description1")
            .location("test_location1")
            .category("AGRICULTURE")
            .status("OPEN")
            .duration(30)
            .pricePerDay(50)
            .build();
    Announcement announcement =
        Announcement.builder()
            .user(User.builder().id(100).build())
            .name("test_name1")
            .description("test_description1")
            .location("test_location1")
            .category(Category.AGRICULTURE)
            .duration(30)
            .pricePerDay(50)
            .status(Status.OPEN)
            .build();
    Claims claims = new DefaultClaims();
    claims.setId("100");
    given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
    given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
    given(dtoConverter.convertAnnouncementDtoForPosting(any(AnnouncementDto.class)))
        .willReturn(announcement);
    given(userService.existsById(any(Integer.class))).willReturn(false);
    mockMvc
        .perform(
            post("/api/announcement")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("ownerId", jwtUtil.createJWT(100, JWTUtil.DEFAULT_VALIDITY))
                .param("name", "test_name1")
                .param("description", "test_description1")
                .param("location", "test_location1")
                .param("category", "AGRICULTURE")
                .param("duration", "30")
                .param("pricePerDay", "50"))
        .andExpect(status().isNotFound());
    verify(dtoConverter).convertAnnouncementDtoForPosting(dto);
    verify(userService).existsById(announcement.getUser().getId());
  }

  @Test
  void testGetAnnouncementById_Exists() throws Exception {
    Announcement announcement =
        Announcement.builder()
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
    BytesAnnouncementDto dto =
        BytesAnnouncementDto.builder()
            .id(1)
            .ownerId("token")
            .name("name1")
            .description("description1")
            .location("location1")
            .category("AGRICULTURE")
            .status("OPEN")
            .duration(3)
            .pricePerDay(50)
            .build();
    Claims claims = new DefaultClaims();
    claims.setId("100");
    given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
    given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
    given(announcementService.getById(any(Integer.class))).willReturn(announcement);
    given(imageService.getBytesForAnnouncement(any(Integer.class))).willReturn(new ArrayList<>());
    given(dtoConverter.convertAnnouncementWithImages(any(Announcement.class), any(List.class)))
        .willReturn(dto);
    mockMvc
        .perform(get("/api/announcement/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("name1"))
        .andExpect(jsonPath("$.pricePerDay").value(50));
    verify(announcementService).getById(1);
    verify(imageService).getBytesForAnnouncement(1);
    verify(dtoConverter).convertAnnouncementWithImages(announcement, new ArrayList<>());
  }

  @Test
  void testGetAnnouncementById_DoesNotExist() throws Exception {
    Announcement announcement = Announcement.builder().id(-1).build();
    given(announcementService.getById(any(Integer.class))).willReturn(announcement);
    mockMvc.perform(get("/api/announcement/{id}", 10)).andExpect(status().isNotFound());
    verify(announcementService).getById(10);
  }

  @Test
  void testGetThumbnailForId_Exists() throws Exception {
    given(imageService.getThumbnailForAnnouncement(any(Integer.class)))
        .willReturn(ArrayUtils.toObject("img".getBytes()));
    ResultActions actions =
        mockMvc
            .perform(get("/api/announcement/thumbnail/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE))
            .andExpect(content().bytes(new byte[] {105, 109, 103}));
    verify(imageService).getThumbnailForAnnouncement(1);
  }

  @Test
  void testGetThumbnailForId_DoesNotExist() throws Exception {
    given(imageService.getThumbnailForAnnouncement(any(Integer.class))).willReturn(null);
    mockMvc.perform(get("/api/announcement/thumbnail/{id}", -1)).andExpect(status().isNotFound());
    verify(imageService).getThumbnailForAnnouncement(-1);
  }

  @Test
  void getAnnouncementsOrderedAndFiltered() throws Exception {
    OrderingAndFilteringData orderingAndFilteringData =
        OrderingAndFilteringData.builder()
            .orderingField("name")
            .order(Order.DESC)
            .priceMinimum(10)
            .priceMaximum(50)
            .categories(Arrays.asList(Category.SPORT, Category.TECH))
            .minimumNumberOfDays(2)
            .build();
    List<Announcement> announcements = getAnnouncements();
    List<Announcement> page0Announcements =
        Arrays.asList(announcements.get(9), announcements.get(8));
    when(dtoConverter.convertOrderingAndFilteringDto(any(OrderingAndFilteringDto.class)))
        .thenReturn(orderingAndFilteringData);
    when(
            announcementService.getAllOrderedAndFilteredPaged(
                any(OrderingAndFilteringData.class), any(Pageable.class)))
        .thenReturn(new PageImpl<>(page0Announcements));
    when(dtoConverter.convertAnnouncementForGetPaginated(announcements.get(9)))
        .thenReturn(PagedAnnouncementDto.builder().id(9).pageNumber(0).createdDate("2222").build());
    when(dtoConverter.convertAnnouncementForGetPaginated(announcements.get(8)))
        .thenReturn(PagedAnnouncementDto.builder().id(8).pageNumber(0).createdDate("8888").build());
    mockMvc
        .perform(
            post("/api/announcement/custom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "  \"ordering\": {\n"
                        + "    \"field\": \"name\",\n"
                        + "    \"order\": \"DESC\"\n"
                        + "  },\n"
                        + "  \"priceFiltering\": {\n"
                        + "    \"minLimit\": 10,\n"
                        + "    \"maxLimit\": 50\n"
                        + "  },\n"
                        + "  \"categoryFiltering\": {\n"
                        + "    \"categories\": [\"SPORT\", \"TECH\"]\n"
                        + "  },\n"
                        + "  \"durationFiltering\": {\n"
                        + "    \"minDays\": 2\n"
                        + "  },\n"
                        + "  \"pageNumber\": 0,\n"
                        + "  \"pageSize\": 2\n"
                        + "}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(9))
        .andExpect(jsonPath("$[0].pageNumber").value(0))
        .andExpect(jsonPath("$[1].id").value(8))
        .andExpect(jsonPath("$[1].pageNumber").value(0));
    verify(announcementService)
        .getAllOrderedAndFilteredPaged(eq(orderingAndFilteringData), eq(PageRequest.of(0, 2)));
    verify(dtoConverter, times(2)).convertAnnouncementForGetPaginated(any(Announcement.class));
  }

  @Test
  void testStartDiscussion() throws Exception {
    Claims claims = new DefaultClaims();
    claims.setId("3");
    given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
    given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
    given(userService.getById(any(Integer.class))).willReturn(User.builder().id(3).build());
    given(announcementService.getById(any(Integer.class))).willReturn(Announcement.builder().id(2).user(User.builder().id(4).build()).build());
    String token = jwtUtil.createJWT(3, JWTUtil.DEFAULT_VALIDITY);
    mockMvc.perform(
            post("/api/announcement/discussion")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n"
                            + "  \"interestedTokenUser\": \"" + token + "\",\n"
                            + "  \"announcementId\": 2\n"
                            + "}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    verify(jwtUtil).decodeJWT("token");
    verify(userService).getById(3);
    verify(announcementService).getById(2);
    verify(discussionService).add(any(Discussion.class));
  }

  @Test
  void testStartDiscussion_notFound() throws Exception {
    Claims claims = new DefaultClaims();
    claims.setId("3");
    given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
    given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
    given(userService.getById(any(Integer.class))).willReturn(User.builder().id(3).build());
    given(announcementService.getById(any(Integer.class))).willReturn(null);
    String token = jwtUtil.createJWT(3, JWTUtil.DEFAULT_VALIDITY);
    mockMvc.perform(
            post("/api/announcement/discussion")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n"
                            + "  \"interestedTokenUser\": \"" + token + "\",\n"
                            + "  \"announcementId\": 2\n"
                            + "}"))
            .andExpect(status().isNotFound());

    verify(jwtUtil).decodeJWT(any(String.class));
    verify(userService).getById(3);
    verify(announcementService).getById(2);
    verify(discussionService, never()).add(any(Discussion.class));
  }

  @Test
  void testGetDiscussions() throws Exception {
    given(announcementService.getById(1)).willReturn(Announcement.builder().id(2).user(User.builder().id(4).build()).build());

    mockMvc.perform(
            get("/api/announcement/get-discussions/1"))
            .andExpect(status().isOk());

    verify(announcementService).getById(1);
    verify(discussionService).getAllByAnnouncement(any(Announcement.class));
  }

  @Test
  void testGetDiscussions_notFound() throws Exception {
    given(announcementService.getById(2)).willReturn(null);

    mockMvc.perform(
            get("/api/announcement/get-discussions/2"))
            .andExpect(status().isNotFound());

    verify(announcementService).getById(2);
    verify(discussionService, never()).getAllByAnnouncement(any(Announcement.class));
  }

  @Test
  void testLoan() throws Exception {
    Claims claims = new DefaultClaims();
    claims.setId("3");
    given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
    given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
    given(userService.getById(any(Integer.class))).willReturn(User.builder().id(3).build());
    given(announcementService.getById(any(Integer.class))).willReturn(Announcement.builder().id(2).user(User.builder().id(4).build()).build());
    given(discussionService.getByAnnouncementAndInterestedUser(any(User.class), any(Announcement.class))).willReturn(Discussion.builder()
            .discussedAnnouncement(Announcement.builder().id(2).user(User.builder().id(4).build()).build())
            .interestedUser(User.builder().id(3).build())
            .build());
    String token = jwtUtil.createJWT(3, JWTUtil.DEFAULT_VALIDITY);
    mockMvc.perform(
            post("/api/announcement/loan")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n"
                            + "  \"interestedTokenUser\": \"" + token + "\",\n"
                            + "  \"announcementId\": 2\n"
                            + "}"))
            .andExpect(status().isOk());

    verify(jwtUtil).decodeJWT(any(String.class));
    verify(userService).getById(3);
    verify(announcementService).getById(2);
    verify(discussionService).getByAnnouncementAndInterestedUser(any(User.class), any(Announcement.class));
    verify(loanService).add(any(Loan.class));
  }

  @Test
  void testLoan_notFound() throws Exception {
    Claims claims = new DefaultClaims();
    claims.setId("3");
    given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
    given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
    given(userService.getById(any(Integer.class))).willReturn(User.builder().id(3).build());
    given(announcementService.getById(any(Integer.class))).willReturn(null);
    given(discussionService.getByAnnouncementAndInterestedUser(any(User.class), any(Announcement.class))).willReturn(Discussion.builder()
            .discussedAnnouncement(Announcement.builder().id(2).user(User.builder().id(4).build()).build())
            .interestedUser(User.builder().id(3).build())
            .build());
    String token = jwtUtil.createJWT(3, JWTUtil.DEFAULT_VALIDITY);
    mockMvc.perform(
            post("/api/announcement/loan")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n"
                            + "  \"interestedTokenUser\": \"" + token + "\",\n"
                            + "  \"announcementId\": 2\n"
                            + "}"))
            .andExpect(status().isNotFound());

    verify(jwtUtil).decodeJWT(any(String.class));
    verify(userService).getById(3);
    verify(announcementService).getById(2);
    verify(discussionService, never()).getByAnnouncementAndInterestedUser(any(User.class), any(Announcement.class));
    verify(loanService, never()).add(any(Loan.class));
  }

  @Test
  void testCloseLoan() throws Exception {
    Claims claims = new DefaultClaims();
    claims.setId("3");
    given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
    given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
    given(userService.getById(any(Integer.class))).willReturn(User.builder().id(3).build());
    given(announcementService.getById(any(Integer.class))).willReturn(Announcement.builder().id(2).user(User.builder().id(4).build()).build());
    given(loanService.getByAnnouncementAndInterestedUser(any(User.class), any(Announcement.class))).willReturn(Loan.builder().discussion(
            Discussion.builder()
                    .discussedAnnouncement(Announcement.builder().id(2).user(User.builder().id(4).build()).build())
                    .interestedUser(User.builder().id(3).build())
                    .build()
    ).build());
    String token = jwtUtil.createJWT(3, JWTUtil.DEFAULT_VALIDITY);
    mockMvc.perform(
            post("/api/announcement/closed-loan")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n"
                            + "  \"interestedTokenUser\": \"" + token + "\",\n"
                            + "  \"announcementId\": 2\n"
                            + "}"))
            .andExpect(status().isOk());

    verify(jwtUtil).decodeJWT(any(String.class));
    verify(userService).getById(3);
    verify(announcementService).getById(2);
    verify(loanService).getByAnnouncementAndInterestedUser(any(User.class), any(Announcement.class));
    verify(closedLoanService).add(any(ClosedLoan.class));
  }

  @Test
  void testCloseLoan_notFound() throws Exception {
    Claims claims = new DefaultClaims();
    claims.setId("3");
    given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
    given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
    given(userService.getById(any(Integer.class))).willReturn(User.builder().id(3).build());
    given(announcementService.getById(any(Integer.class))).willReturn(Announcement.builder().id(2).user(User.builder().id(4).build()).build());
    given(loanService.getByAnnouncementAndInterestedUser(any(User.class), any(Announcement.class))).willReturn(null);
    String token = jwtUtil.createJWT(3, JWTUtil.DEFAULT_VALIDITY);
    mockMvc.perform(
            post("/api/announcement/closed-loan")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n"
                            + "  \"interestedTokenUser\": \"" + token + "\",\n"
                            + "  \"announcementId\": 2\n"
                            + "}"))
            .andExpect(status().isNotFound());

    verify(jwtUtil).decodeJWT(any(String.class));
    verify(userService).getById(3);
    verify(announcementService).getById(2);
    verify(loanService).getByAnnouncementAndInterestedUser(any(User.class), any(Announcement.class));
    verify(closedLoanService, never()).add(any(ClosedLoan.class));
  }
}
