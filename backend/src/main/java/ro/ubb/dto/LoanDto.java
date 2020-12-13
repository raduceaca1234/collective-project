package ro.ubb.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoanDto {

    @NotNull
    private String interestedTokenUser;
    @NotNull
    private int announcementId;
}