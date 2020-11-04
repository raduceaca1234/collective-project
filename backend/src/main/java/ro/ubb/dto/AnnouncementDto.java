package ro.ubb.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {
    private int id;
    private int ownerId;
    private String name;
    private String description;
    private String location;
    private String category;
    private String status;
    private int duration;
    private int pricePerDay;
    private List<MultipartFile> images;
}
