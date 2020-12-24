package ro.ubb.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InterestedUserDto {
    private String token;
    private String email;
    private Integer discussionId;
}
