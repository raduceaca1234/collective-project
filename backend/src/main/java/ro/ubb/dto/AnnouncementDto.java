package ro.ubb.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AnnouncementDto {
    @EqualsAndHashCode.Include
    private int id;
    @NotNull private int ownerId;
    @NotNull private String name;
    @NotNull private String description;
    @NotNull private String location;
    @NotNull private String category;
    private String status;
    @NotNull private int duration;
    @NotNull private int pricePerDay;
    private List<MultipartFile> images;
}
