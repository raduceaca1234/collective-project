package ro.ubb.dto;

import lombok.Builder;
import lombok.Data;
import ro.ubb.model.enums.Order;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class OrderingDto {
    @NotEmpty private String field;
    @NotEmpty private Order order;
}
