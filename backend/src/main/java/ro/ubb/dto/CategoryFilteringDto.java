package ro.ubb.dto;

import lombok.Builder;
import lombok.Data;
import ro.ubb.model.enums.Category;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class CategoryFilteringDto {
    @NotNull private List<Category> categories;
}
