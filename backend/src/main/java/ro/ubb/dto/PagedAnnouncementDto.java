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
public class PagedAnnouncementDto {
    @EqualsAndHashCode.Include
    private int id;
    @NotNull private String name;
    @NotNull private String description;
    @NotNull private String location;
    @NotNull private String category;
    @NonNull private String createdDate;
    @NotNull private int duration;
    private String status;
    @NotNull private int pricePerDay;
    @NotNull private String ownerId;
    @NonNull private int pageNumber;
    @NotNull private int order;
    private byte[] thumbnail;
}
