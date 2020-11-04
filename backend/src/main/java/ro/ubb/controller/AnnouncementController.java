package ro.ubb.controller;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.model.Announcement;
import ro.ubb.model.Image;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;
import ro.ubb.service.AnnouncementServiceImpl;
import ro.ubb.service.ImageServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("/api/announcement")
@Slf4j
public class AnnouncementController {

    private final AnnouncementServiceImpl announcementService;
    private final ImageServiceImpl imageService;

    public AnnouncementController(AnnouncementServiceImpl announcementService, ImageServiceImpl imageService) {
        this.announcementService = announcementService;
        this.imageService = imageService;
    }


    @PostMapping
    ResponseEntity<?> postAnnouncement(@ModelAttribute AnnouncementDto announcementDto){
        Announcement announcementToAdd = announcementService.add(Announcement.builder()
                .user(User.builder().id(announcementDto.getOwnerId()).build())
                .name(announcementDto.getName())
                .location(announcementDto.getLocation())
                .description(announcementDto.getDescription())
                .category(Category.valueOf(announcementDto.getCategory()))
                .status(Status.OPEN)
                .duration(announcementDto.getDuration())
                .pricePerDay(announcementDto.getPricePerDay())
                .build());
        log.info("calling announcementService add...");
        Announcement addedAnnouncement = announcementService.add(announcementToAdd);
        log.info("announcementSerivce add finished...");

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


}
