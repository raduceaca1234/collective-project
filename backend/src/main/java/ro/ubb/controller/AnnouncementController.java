package ro.ubb.controller;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.model.Announcement;
import ro.ubb.model.Image;
import ro.ubb.service.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/announcement")
@Slf4j
public class AnnouncementController {

    private AnnouncementService announcementService;
    private ImageService imageService;
    private DtoConverter dtoConverter;
    private UserService userService;

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
