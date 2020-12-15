package ro.ubb.controller;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.*;
import ro.ubb.model.*;
import ro.ubb.model.enums.Status;
import ro.ubb.security.JWTUtil;
import ro.ubb.service.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/announcement")
@Slf4j
public class AnnouncementController {

  private AnnouncementService announcementService;
  private ImageService imageService;
  private DiscussionService discussionService;
  private LoanService loanService;
  private ClosedLoanService closedLoanService;
  private DtoConverter dtoConverter;
  private UserService userService;
  private JWTUtil jwtUtil;

  @GetMapping(value = "/{pageNo}/{pageSize}")
  ResponseEntity<List<PagedAnnouncementDto>> getAnnouncements(
      @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);

    log.info("pageNo = {}", pageNo);
    log.info("pageSize = {}", pageSize);
    log.info("calling announcementService get...");
    Page<Announcement> announcementsPage = announcementService.getAllPaged(pageable);
    log.info("announcementService get finished...");
    log.info("page = {}", announcementsPage);

    List<PagedAnnouncementDto> pagedAnnouncementDtos = new ArrayList<>();
    for (Announcement a : announcementsPage) {
      PagedAnnouncementDto pad = dtoConverter.convertAnnouncementForGetPaginated(a);
      pad.setPageNumber(pageNo);
      pagedAnnouncementDtos.add(pad);
    }
    return ResponseEntity.ok(pagedAnnouncementDtos);
  }

  @PostMapping(value = "/custom")
  public ResponseEntity<List<PagedAnnouncementDto>> getAnnouncementsOrderedAndFiltered(
      @RequestBody @Valid OrderingAndFilteringDto orderingAndFilteringDto) {
    log.debug(
        "Got ordering and filtering call with params = {}", orderingAndFilteringDto.toString());
    OrderingAndFilteringData orderingAndFilteringData =
        dtoConverter.convertOrderingAndFilteringDto(orderingAndFilteringDto);
    Pageable pageable = PageRequest.of(orderingAndFilteringDto.getPageNumber(), orderingAndFilteringDto.getPageSize());
    Page<Announcement> announcementsOrderedAndFilteredPage = announcementService.getAllOrderedAndFilteredPaged(orderingAndFilteringData, pageable);

    List<PagedAnnouncementDto> pagedOrderedAndFilteredAnnouncementDtos = new ArrayList<>();
    for (Announcement announcement : announcementsOrderedAndFilteredPage) {
        PagedAnnouncementDto pagedAnnouncementDto = dtoConverter.convertAnnouncementForGetPaginated(announcement);
        pagedAnnouncementDto.setPageNumber(orderingAndFilteringDto.getPageNumber());
        pagedOrderedAndFilteredAnnouncementDtos.add(pagedAnnouncementDto);
    }
    return ResponseEntity.ok(pagedOrderedAndFilteredAnnouncementDtos);
  }

  @PostMapping
  ResponseEntity<?> postAnnouncement(@ModelAttribute AnnouncementDto announcementDto) {
    Announcement announcementToAdd = dtoConverter.convertAnnouncementDtoForPosting(announcementDto);
    int ownerId = Integer.parseInt(jwtUtil.decodeJWT(announcementDto.getOwnerId()).getId());
    if (!userService.existsById(ownerId)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    log.info("calling announcementService add...");
    Announcement addedAnnouncement = announcementService.add(announcementToAdd);
    log.info("announcementService add finished...");

    if (announcementDto.getImages() != null) {
      for (MultipartFile image : announcementDto.getImages()) {
        try {
          log.info("calling imageService add...");
          imageService.add(
              Image.builder()
                  .announcement(addedAnnouncement)
                  .imageBytes(ArrayUtils.toObject(image.getBytes()))
                  .build());
          log.info("imageService add finished...");
        } catch (IOException e) {
          log.error("Exception encountered-" + e.getMessage());
          return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
      }
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}")
  ResponseEntity<?> getAnnouncementDetailsById(@PathVariable Integer id) {
    log.info("calling announcementService getById...");
    Announcement announcement = announcementService.getById(id);
    log.info("announcementService getById call done, announcement={}...", announcement);
    if (announcement.getId() == -1) {
      log.error("no announcement with id={}", id);
      return ResponseEntity.notFound().build();
    }
    log.info("fetching image bytes for announcement={}..", announcement);
    List<Byte[]> imageBytes = imageService.getBytesForAnnouncement(id);
    log.info("image bytes fetching complete..");
    return ResponseEntity.ok(dtoConverter.convertAnnouncementWithImages(announcement, imageBytes));
  }

  @GetMapping(value = "/thumbnail/{id}")
  ResponseEntity<?> getThumbnail(@PathVariable Integer id) {
    log.info("fetching thumbnail image bytes for announcement with id={}..", id);
    Byte[] bytes = imageService.getThumbnailForAnnouncement(id);
    log.info("thumbnail fetching complete..");
    if (bytes == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok()
        .contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE))
        .body(ArrayUtils.toPrimitive(bytes));
  }

  @GetMapping(value = "/interestedIn/{id}")
  ResponseEntity<?> getInterestedUsers(@PathVariable Integer id){
    List<Discussion> discussions = discussionService.getAllByAnnouncementId(id);
    List<InterestedUserDto> dtos = discussions.stream().map(discussion ->
      new InterestedUserDto(jwtUtil.createJWT(discussion.getInterestedUser().getId(),JWTUtil.DEFAULT_VALIDITY),discussion.getInterestedUser().getEmail()))
            .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @PostMapping(value = "/discussion")
  ResponseEntity<?> startDiscussion(@RequestBody LoanDto loanDto) {
    int interestedUser = Integer.parseInt(jwtUtil.decodeJWT(loanDto.getInterestedTokenUser()).getId());
    int announcementId = loanDto.getAnnouncementId();

    log.info("calling userService getById ...");
    User user = userService.getById(interestedUser);
    if (user == null) {
      log.error("no user with id={}", interestedUser);
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    log.info("userService getById complete");
    log.info("calling announcementService getById ...");
    Announcement announcement = announcementService.getById(announcementId);
    if (announcement == null) {
      log.error("no announcement with id={}", announcementId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("announcementService getById complete");

    Discussion discussion = Discussion.builder()
            .interestedUser(user)
            .discussedAnnouncement(announcement)
            .build();
    announcement.setStatus(Status.IN_DISCUSSION);
    log.info("calling discussionService add ...");
    discussionService.add(discussion);
    log.info("discussionService add complete");
    DiscussionResponseDto discussionResponseDto = DiscussionResponseDto.builder()
            .email(announcement.getUser().getEmail())
            .phoneNumber(announcement.getUser().getPhoneNumber())
            .build();
    return ResponseEntity.ok().body(discussionResponseDto);
  }

  @GetMapping(value = "/get-discussions/{announcementId}")
  ResponseEntity<?> getDiscussions(@PathVariable int announcementId) {
    log.info("userService getById complete");
    log.info("calling announcementService getById ...");
    Announcement announcement = announcementService.getById(announcementId);
    if (announcement == null) {
      log.error("no announcement with id={}", announcementId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("announcementService getById complete");

    log.info("calling discussionService getAllByAnnouncement ...");
    List<Discussion> discussions = discussionService.getAllByAnnouncement(announcement);
    log.info("discussionService getAllByAnnouncement complete");

    return ResponseEntity.ok().body(discussions.toString());
  }

  @PostMapping(value = "/loan")
  ResponseEntity<?> loan(@RequestBody LoanDto loanDto) {
    int interestedUser = Integer.parseInt(jwtUtil.decodeJWT(loanDto.getInterestedTokenUser()).getId());
    int announcementId = loanDto.getAnnouncementId();

    log.info("calling userService getById ...");
    User user = userService.getById(interestedUser);
    if (user == null) {
      log.error("no user with id={}", interestedUser);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("userService getById complete");
    log.info("calling announcementService getById ...");
    Announcement announcement = announcementService.getById(announcementId);
    if (announcement == null) {
      log.error("no announcement with id={}", announcementId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("announcementService getById complete");
    log.info("calling discussionService getByAnnouncementAndInterestedUser ...");
    Discussion discussion = discussionService.getByAnnouncementAndInterestedUser(user, announcement);
    if (discussion == null) {
      log.error("no discussion with announcement={} and interestedUser={}", announcement, user);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("discussionService getByAnnouncementAndInterestedUser complete");

    Loan loan = Loan.builder()
            .discussion(discussion)
            .build();
    announcement.setStatus(Status.LOANED);
    log.info("calling discussionService add ...");
    loanService.add(loan);
    log.info("discussionService add complete");
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/closed-loan")
  ResponseEntity<?> closeLoan(@RequestBody LoanDto loanDto) {
    int interestedUser = Integer.parseInt(jwtUtil.decodeJWT(loanDto.getInterestedTokenUser()).getId());
    int announcementId = loanDto.getAnnouncementId();

    log.info("calling userService getById ...");
    User user = userService.getById(interestedUser);
    if (user == null) {
      log.error("no user with id={}", interestedUser);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("userService getById complete");
    log.info("calling announcementService getById ...");
    Announcement announcement = announcementService.getById(announcementId);
    if (announcement == null) {
      log.error("no announcement with id={}", announcementId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("announcementService getById complete");
    log.info("calling loanService getByAnnouncementAndInterestedUser ...");
    Loan loan = loanService.getByAnnouncementAndInterestedUser(user, announcement);
    if (loan == null) {
      log.error("no loan with announcement={} and interestedUser={}", announcement, user);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("loanService getByAnnouncementAndInterestedUser complete");

    ClosedLoan closedLoan = ClosedLoan.builder()
            .loan(loan)
            .build();
    announcement.setStatus(Status.CLOSED);
    log.info("calling closedLoanService add ...");
    closedLoanService.add(closedLoan);
    log.info("closedLoanService add complete");
    return ResponseEntity.ok().build();
  }

  @Autowired
  public void setAnnouncementService(AnnouncementService announcementService) {
    this.announcementService = announcementService;
  }

  @Autowired
  public void setImageService(ImageService imageService) {
    this.imageService = imageService;
  }

  @Autowired
  public void setDiscussionService(DiscussionService discussionService) {
    this.discussionService = discussionService;
  }

  @Autowired
  public void setLoanService(LoanService loanService) {
    this.loanService = loanService;
  }

  @Autowired
  public void setClosedLoanService(ClosedLoanService closedLoanService) {
    this.closedLoanService = closedLoanService;
  }

  @Autowired
  public void setDtoConverter(DtoConverter dtoConverter) {
    this.dtoConverter = dtoConverter;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setJwtUtil(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }
}
