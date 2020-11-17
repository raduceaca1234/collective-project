package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.model.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query("SELECT i.imageBytes FROM Image i where i.announcement.id=:aid")
    List<Byte[]> getImageBytesForAnnouncement(@Param("aid") int aid);
}
