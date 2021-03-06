package ro.ubb.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BytesAnnouncementDto {
    @EqualsAndHashCode.Include
    private int id;
    @NotNull
    private String ownerId;
    @NotNull private String name;
    @NotNull private String description;
    @NotNull private String location;
    @NotNull private String category;
    private String status;
    @NotNull private int duration;
    @NotNull private int pricePerDay;
    private List<Byte[]> imageBytes;
}
