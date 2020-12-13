package ro.ubb.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DiscussionResponseDto {

    private String email;
    private String phoneNumber;
}
