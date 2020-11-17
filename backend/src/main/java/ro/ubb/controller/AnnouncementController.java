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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/announcement")
@Slf4j
public class AnnouncementController {

    private AnnouncementService announcementService;
    private ImageService imageService;
    private DtoConverter dtoConverter;
    private UserService userService;

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

        if (!userService.existsById(announcementDto.getOwnerId())){
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
}
