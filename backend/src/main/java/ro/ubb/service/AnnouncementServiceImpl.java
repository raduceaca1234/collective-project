package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.ubb.model.Announcement;
import ro.ubb.model.OrderingAndFilteringData;
import ro.ubb.model.enums.Order;
import ro.ubb.repository.AnnouncementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnnouncementServiceImpl implements AnnouncementService {

  private AnnouncementRepository announcementRepository;

  @Override
  public Announcement add(Announcement announcement) {
    return announcementRepository.save(announcement);
  }

  @Override
  public List<Announcement> getAll() {
    List<Announcement> announcements = new ArrayList<>();
    announcementRepository.findAll().forEach(announcements::add);
    return announcements;
  }

  @Override
  public Page<Announcement> getAllPaged(Pageable pageable) {
    return announcementRepository.findAll(pageable);
  }

  @Override
  public Announcement getById(int id) {
    return announcementRepository.findById(id).orElse(Announcement.builder().id(-1).build());
  }

  @Override
  public boolean existsById(int id) {
    return announcementRepository.existsById(id);
  }

  @Override
  public Page<Announcement> getAllOrderedAndFilteredPaged(
      OrderingAndFilteringData orderingAndFilteringData, Pageable pageable) {
    List<Announcement> result = fetchAnnouncementsSorted(orderingAndFilteringData);
    result = getAnnouncementsFilteredByMinNumberOfDays(orderingAndFilteringData, result);
    result = getAnnouncementsFilteredByMaxPrice(orderingAndFilteringData, result);
    result = getAnnouncementsFilteredByMinPrice(orderingAndFilteringData, result);
    result = getAnnouncementsFilteredByCategory(orderingAndFilteringData, result);
    long start = pageable.getOffset();
    long end = (start + pageable.getPageSize()) > result.size() ? result.size() : (start + pageable.getPageSize());
    return new PageImpl<>(result.subList((int) start, (int) end), pageable, result.size());
  }

  private List<Announcement> fetchAnnouncementsSorted(OrderingAndFilteringData orderingAndFilteringData)
  {
    List<Announcement> result = new ArrayList<>();
    Iterable<Announcement> announcementsIterable;
    if (orderingAndFilteringData.getOrderingField() != null) {
      Sort.Direction direction;
      if (orderingAndFilteringData.getOrder() != null) {
        direction = getDirection(orderingAndFilteringData);
      } else {
        direction = Sort.Direction.ASC;
      }
      announcementsIterable =
          announcementRepository.findAll(
              Sort.by(direction, orderingAndFilteringData.getOrderingField()));
    } else {
      announcementsIterable = announcementRepository.findAll();
    }
    announcementsIterable.forEach(result::add);
    return result;
  }

  private List<Announcement> getAnnouncementsFilteredByCategory(OrderingAndFilteringData orderingAndFilteringData,
                                                                List<Announcement> result)
  {
    if (orderingAndFilteringData.getCategories() != null) {
      result =
          result.stream()
              .filter(
                  announcement ->
                      orderingAndFilteringData.getCategories().contains(announcement.getCategory()))
              .collect(Collectors.toList());
    }
    return result;
  }

  private List<Announcement> getAnnouncementsFilteredByMinPrice(OrderingAndFilteringData orderingAndFilteringData,
                                                                List<Announcement> result)
  {
    if (orderingAndFilteringData.getPriceMinimum() != null) {
      result =
          result.stream()
              .filter(
                  announcement ->
                      announcement.getPricePerDay() >= orderingAndFilteringData.getPriceMinimum())
              .collect(Collectors.toList());
    }
    return result;
  }

  private List<Announcement> getAnnouncementsFilteredByMaxPrice(OrderingAndFilteringData orderingAndFilteringData,
                                                                List<Announcement> result)
  {
    if (orderingAndFilteringData.getPriceMaximum() != null) {
      result =
          result.stream()
              .filter(
                  announcement ->
                      announcement.getPricePerDay() <= orderingAndFilteringData.getPriceMaximum())
              .collect(Collectors.toList());
    }
    return result;
  }

  private List<Announcement> getAnnouncementsFilteredByMinNumberOfDays(
  OrderingAndFilteringData orderingAndFilteringData, List<Announcement> result)
  {
    if (orderingAndFilteringData.getMinimumNumberOfDays() != null) {
      result =
          result.stream()
              .filter(
                  announcement ->
                      announcement.getDuration()
                          >= orderingAndFilteringData.getMinimumNumberOfDays())
              .collect(Collectors.toList());
    }
    return result;
  }

  private Sort.Direction getDirection(OrderingAndFilteringData orderingAndFilteringData) {
    return orderingAndFilteringData.getOrder() == Order.ASC
        ? Sort.Direction.ASC
        : Sort.Direction.DESC;
  }

  @Autowired
  public void setAnnouncementRepository(AnnouncementRepository announcementRepository) {
    this.announcementRepository = announcementRepository;
  }
}
