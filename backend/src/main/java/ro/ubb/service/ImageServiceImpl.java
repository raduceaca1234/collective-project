package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.ubb.model.Image;
import ro.ubb.repository.ImageRepository;

import java.util.List;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image add(Image image) {
        log.info("Saving image=" + image.toString() + "...");
        return imageRepository.save(image);
    }

    @Override
    public List<Image> getImages() {
        return imageRepository.findAll();
    }
}
