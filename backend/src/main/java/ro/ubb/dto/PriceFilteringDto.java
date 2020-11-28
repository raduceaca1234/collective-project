package ro.ubb.dto;

import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class PriceFilteringDto {
    @NotEmpty private Integer minLimit;
    @NotEmpty private Integer maxLimit;
}
