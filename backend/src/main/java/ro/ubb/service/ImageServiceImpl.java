package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.model.Image;
import ro.ubb.repository.ImageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private ImageRepository imageRepository;

    @Override
    public Image add(Image image) {
        log.info("Saving image=" + image.toString() + "...");
        return imageRepository.save(image);
    }

    @Override
    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    @Override
    public List<Image> getImagesForAnnouncement(int announcementId) {
        return imageRepository.findAll().stream().filter(i -> i.getAnnouncement().getId() == announcementId).collect(Collectors.toList());
    }

    @Override
    public List<Byte[]> getBytesForAnnouncement(int id) {
        return imageRepository.getImageBytesForAnnouncement(id);
    }


    @Autowired
    public void setImageRepository(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
}
