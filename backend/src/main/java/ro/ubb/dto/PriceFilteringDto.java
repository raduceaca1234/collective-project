package ro.ubb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceFilteringDto {
    @NotEmpty private Integer minLimit;
    @NotEmpty private Integer maxLimit;
}
