package ro.ubb.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class OrderingAndFilteringDto {
  private OrderingDto orderingDto;
  private PriceFilteringDto priceFilteringDto;
  private CategoryFilteringDto categoryFilteringDto;
  private DurationFilteringDto durationFilteringDto;
  @NotNull private Integer pageSize;
  @NotNull private Integer pageNumber;
}
