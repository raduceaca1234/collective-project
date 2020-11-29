package ro.ubb.dto;

import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class WishListDto {
    @EqualsAndHashCode.Include
    private int id;
    @NotNull
    private int ownerId;
    private Set<AnnouncementDto> announcements;
}
