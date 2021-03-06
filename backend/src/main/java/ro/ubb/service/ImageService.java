package ro.ubb.service;

import ro.ubb.model.Image;

import java.util.List;

public interface ImageService {
    Image add(Image image);

    List<Image> getImages();

    List<Image> getImagesForAnnouncement(int announcementId);

    List<Byte[]> getBytesForAnnouncement(int id);

    Byte[] getThumbnailForAnnouncement(int id);
}
