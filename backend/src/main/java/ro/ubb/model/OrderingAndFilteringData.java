package ro.ubb.model;

import lombok.Builder;
import lombok.Data;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Order;

import java.util.List;

@Data
@Builder
public class OrderingAndFilteringData {
  private String orderingField;
  private Order order;
  private Integer priceMinimum;
  private Integer priceMaximum;
  private List<Category> categories;
  private Integer minimumNumberOfDays;
}
