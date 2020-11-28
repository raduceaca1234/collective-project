package ro.ubb.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class DurationFilteringDto {
    @NotNull private Integer minDays;
}
