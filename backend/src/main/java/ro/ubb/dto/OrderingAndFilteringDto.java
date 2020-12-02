package ro.ubb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderingAndFilteringDto {
  private OrderingDto ordering;
  private PriceFilteringDto priceFiltering;
  private CategoryFilteringDto categoryFiltering;
  private DurationFilteringDto durationFiltering;
  @NotNull private Integer pageSize;
  @NotNull private Integer pageNumber;
}
