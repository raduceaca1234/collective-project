package ro.ubb.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ThumbnailDto {
    @NotNull
    private Integer id;
    @NotNull
    private Byte[] thumbnail;
}
