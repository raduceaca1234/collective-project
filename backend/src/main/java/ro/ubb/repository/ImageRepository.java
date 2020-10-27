package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
