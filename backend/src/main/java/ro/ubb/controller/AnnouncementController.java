package ro.ubb.controller;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.dto.PagedAnnouncementDto;
import ro.ubb.dto.PagingDto;
import ro.ubb.model.Announcement;
import ro.ubb.model.Image;
import ro.ubb.service.AnnouncementService;
import ro.ubb.service.ImageService;
import ro.ubb.service.UserService;
import ro.ubb.security.JWTUtil;
import ro.ubb.service.AnnouncementService;
import ro.ubb.service.ImageService;
import ro.ubb.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

@RestController
@RequestMapping("/api/announcement")
@Slf4j
public class AnnouncementController {

    private AnnouncementService announcementService;
    private ImageService imageService;
    private DtoConverter dtoConverter;
    private UserService userService;
    private JWTUtil jwtUtil;

    @GetMapping
    ResponseEntity<List<PagedAnnouncementDto>> getAnnouncements(@ModelAttribute PagingDto pagingDto) {
        int pageNo = pagingDto.getPageNo();
        int pageSize = pagingDto.getPageSize();
        pageNo = 1;
        pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        log.info("calling announcementService get...");
        Page<Announcement> announcementsPage = announcementService.getAllPaged(pageable);
        log.info("announcementService get finished...");

        List<PagedAnnouncementDto> pagedAnnouncementDtos = new ArrayList<>();
        for (Announcement a : announcementsPage) {
            PagedAnnouncementDto pad = dtoConverter.convertAnnouncementForGetPaginated(a);
            pad.setPageNumber(pageNo);
            log.info("calling imageService get...");
            imageService.getImagesForAnnouncement(a.getId()).stream()
                    .findFirst()
                    .ifPresent(i -> pad.setThumbnail(ArrayUtils.toPrimitive(i.getImageBytes())));
            log.info("imageService get finished...");
            pagedAnnouncementDtos.add(pad);
        }
        return ResponseEntity.ok(pagedAnnouncementDtos);
    }

    @PostMapping
    ResponseEntity<?> postAnnouncement(@ModelAttribute AnnouncementDto announcementDto){
        Announcement announcementToAdd = dtoConverter.convertAnnouncementDtoForPosting(announcementDto);
        int ownerId = Integer.parseInt(jwtUtil.decodeJWT(announcementDto.getOwnerId()).getId());
        if (!userService.existsById(ownerId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("calling announcementService add...");
        Announcement addedAnnouncement = announcementService.add(announcementToAdd);
        log.info("announcementService add finished...");

        if (announcementDto.getImages()!=null) {
            for (MultipartFile image : announcementDto.getImages()) {
                try {
                    log.info("calling imageService add...");
                    imageService.add(Image.builder()
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
    ResponseEntity<?> getAnnouncementDetailsById(@PathVariable Integer id){
        log.info("calling announcementService getById...");
        Announcement announcement = announcementService.getById(id);
        log.info("announcementService getById call done, announcement={}...", announcement);
        if (announcement.getId()==-1){
            log.error("no announcement with id={}",id);
            return ResponseEntity.notFound().build();
        }
        log.info("fetching image bytes for announcement={}..",announcement);
        List<Byte[]> imageBytes = imageService.getBytesForAnnouncement(id);
        log.info("image bytes fetching complete..");
        return ResponseEntity.ok(dtoConverter.convertAnnouncementWithImages(announcement, imageBytes));
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
