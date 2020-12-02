package ro.ubb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ro.ubb.model.Announcement;
import ro.ubb.model.OrderingAndFilteringData;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Order;
import ro.ubb.model.enums.Status;
import ro.ubb.repository.AnnouncementRepository;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AnnouncementServiceTest {

  @Mock private AnnouncementRepository announcementRepository;
  private AnnouncementServiceImpl announcementService;

  @BeforeEach
  void setUp() {
    initMocks(this);
    announcementService = new AnnouncementServiceImpl();
    announcementService.setAnnouncementRepository(announcementRepository);
  }

  @Test
  void testGetAnnouncements() {
    List<Announcement> announcements1 =
        Arrays.asList(
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
                User.builder().id(1).build(),
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
                User.builder().id(1).build(),
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
                new HashSet<>()));
    List<Announcement> announcements2 =
        Collections.singletonList(
            new Announcement(
                6,
                User.builder().id(1).build(),
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
                new HashSet<>()));
    Page<Announcement> announcementPage1 = new PageImpl<>(announcements1);
    Page<Announcement> announcementPage2 = new PageImpl<>(announcements2);
    Pageable page1 = PageRequest.of(0, 5);
    Pageable page2 = PageRequest.of(1, 5);
    when(announcementRepository.findAll(page1)).thenReturn(announcementPage1);
    when(announcementRepository.findAll(page2)).thenReturn(announcementPage2);

    assertEquals(5, announcementService.getAllPaged(page1).getContent().size());
    assertEquals(1, announcementService.getAllPaged(page1).getContent().get(0).getId());
    assertEquals(5, announcementService.getAllPaged(page1).getContent().get(4).getId());
    assertEquals(1, announcementService.getAllPaged(page2).getContent().size());
    assertEquals(6, announcementService.getAllPaged(page2).getContent().get(0).getId());
  }

  @Test
  void testAddAnnouncement() {
    Announcement added =
        Announcement.builder()
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
    assertEquals(announcementService.add(added).getId(), 1);
  }

  @Test
  void filteringAndOrdering_fetchAnnouncementsUnsorted() {
    OrderingAndFilteringData orderingAndFilteringData = OrderingAndFilteringData.builder().build();
    announcementService.getAllOrderedAndFilteredPaged(
        orderingAndFilteringData, PageRequest.of(0, 1));
    verify(announcementRepository, times(0)).findAll(any(Sort.class));
    verify(announcementRepository).findAll();
  }

  @Test
  void filteringAndOrdering_fetchAnnouncementsSortedDescending() {
    OrderingAndFilteringData orderingAndFilteringData =
        OrderingAndFilteringData.builder().orderingField("name").order(Order.DESC).build();
    ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
    when(announcementRepository.findAll(sortArgumentCaptor.capture()))
        .thenReturn(Collections.emptyList());
    announcementService.getAllOrderedAndFilteredPaged(
        orderingAndFilteringData, PageRequest.of(0, 1));
    verify(announcementRepository).findAll(any(Sort.class));
    verify(announcementRepository, times(0)).findAll();
    assertNotNull(sortArgumentCaptor.getValue().getOrderFor("name"));
    assertEquals(
        Sort.Direction.DESC, sortArgumentCaptor.getValue().getOrderFor("name").getDirection());
    assertEquals("name", sortArgumentCaptor.getValue().getOrderFor("name").getProperty());
  }

  @Test
  void filteringAndOrdering_fetchAnnouncementsSortedDefault() {
    OrderingAndFilteringData orderingAndFilteringData =
        OrderingAndFilteringData.builder().orderingField("name").build();
    ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
    when(announcementRepository.findAll(sortArgumentCaptor.capture()))
        .thenReturn(Collections.emptyList());
    announcementService.getAllOrderedAndFilteredPaged(
        orderingAndFilteringData, PageRequest.of(0, 1));
    verify(announcementRepository).findAll(any(Sort.class));
    verify(announcementRepository, times(0)).findAll();
    assertNotNull(sortArgumentCaptor.getValue().getOrderFor("name"));
    assertEquals(
        Sort.Direction.ASC, sortArgumentCaptor.getValue().getOrderFor("name").getDirection());
    assertEquals("name", sortArgumentCaptor.getValue().getOrderFor("name").getProperty());
  }

  @Test
  void filteringAndOrdering_filterAnnouncementsByDays() {
    OrderingAndFilteringData orderingAndFilteringData =
        OrderingAndFilteringData.builder().minimumNumberOfDays(5).build();
    when(announcementRepository.findAll())
        .thenReturn(
            Arrays.asList(
                Announcement.builder().id(1).duration(3).build(),
                Announcement.builder().id(2).duration(8).build()));
    Page<Announcement> result =
        announcementService.getAllOrderedAndFilteredPaged(
            orderingAndFilteringData, PageRequest.of(0, 10));
    assertEquals(1, result.getContent().size());
    assertEquals(2, result.getContent().get(0).getId());
  }

  @Test
  void filteringAndOrdering_filterAnnouncementsByPrice() {
    OrderingAndFilteringData orderingAndFilteringData =
        OrderingAndFilteringData.builder().priceMinimum(10).priceMaximum(100).build();
    when(announcementRepository.findAll())
        .thenReturn(
            Arrays.asList(
                Announcement.builder().id(1).pricePerDay(5).build(),
                Announcement.builder().id(2).pricePerDay(50).build(),
                Announcement.builder().id(3).pricePerDay(500).build()));
    Page<Announcement> result =
        announcementService.getAllOrderedAndFilteredPaged(
            orderingAndFilteringData, PageRequest.of(0, 10));
    assertEquals(1, result.getContent().size());
    assertEquals(2, result.getContent().get(0).getId());
  }

  @Test
  void filteringAndOrdering_filterAnnouncementsByCategory() {
    OrderingAndFilteringData orderingAndFilteringData =
        OrderingAndFilteringData.builder()
            .categories(Arrays.asList(Category.TECH, Category.AGRICULTURE))
            .build();
    when(announcementRepository.findAll())
        .thenReturn(
            Arrays.asList(
                Announcement.builder().id(1).category(Category.TECH).build(),
                Announcement.builder().id(2).category(Category.AGRICULTURE).build(),
                Announcement.builder().id(3).category(Category.AUTO_MOTO).build()));
    Page<Announcement> result =
        announcementService.getAllOrderedAndFilteredPaged(
            orderingAndFilteringData, PageRequest.of(0, 10));
    assertEquals(2, result.getContent().size());
    assertEquals(1, result.getContent().get(0).getId());
    assertEquals(2, result.getContent().get(1).getId());
  }

  @Test
  void filteringAndOrdering_paging() {
    OrderingAndFilteringData orderingAndFilteringData = OrderingAndFilteringData.builder().build();
    when(announcementRepository.findAll())
        .thenReturn(
            Arrays.asList(
                Announcement.builder().id(1).build(),
                Announcement.builder().id(2).build(),
                Announcement.builder().id(3).build(),
                Announcement.builder().id(4).build(),
                Announcement.builder().id(5).build()));
    Page<Announcement> result =
        announcementService.getAllOrderedAndFilteredPaged(
            orderingAndFilteringData, PageRequest.of(0, 2));
    assertEquals(2, result.getContent().size());
    assertEquals(1, result.getContent().get(0).getId());
    assertEquals(2, result.getContent().get(1).getId());

    result =
        announcementService.getAllOrderedAndFilteredPaged(
            orderingAndFilteringData, PageRequest.of(1, 2));
    assertEquals(2, result.getContent().size());
    assertEquals(3, result.getContent().get(0).getId());
    assertEquals(4, result.getContent().get(1).getId());

    result =
        announcementService.getAllOrderedAndFilteredPaged(
            orderingAndFilteringData, PageRequest.of(2, 2));
    assertEquals(1, result.getContent().size());
    assertEquals(5, result.getContent().get(0).getId());
  }

  @Test
  void filteringAndOrdering_complex() {
    OrderingAndFilteringData orderingAndFilteringData =
        OrderingAndFilteringData.builder().orderingField("name").minimumNumberOfDays(2).build();
    when(announcementRepository.findAll(any(Sort.class)))
        .thenReturn(
            Arrays.asList(
                Announcement.builder().id(1).name("a").duration(5).build(),
                Announcement.builder().id(2).name("b").duration(4).build(),
                Announcement.builder().id(3).name("c").duration(3).build(),
                Announcement.builder().id(4).name("d").duration(2).build(),
                Announcement.builder().id(5).name("e").duration(1).build()));
    Page<Announcement> result =
        announcementService.getAllOrderedAndFilteredPaged(
            orderingAndFilteringData, PageRequest.of(0, 2));
    assertEquals(4, result.getTotalElements());
    assertEquals(1, result.getContent().get(0).getId());
    assertEquals(2, result.getContent().get(1).getId());

    result =
        announcementService.getAllOrderedAndFilteredPaged(
            orderingAndFilteringData, PageRequest.of(1, 2));
    assertEquals(3, result.getContent().get(0).getId());
    assertEquals(4, result.getContent().get(1).getId());
  }
}
