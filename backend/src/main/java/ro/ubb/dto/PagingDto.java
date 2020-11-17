package ro.ubb.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingDto {

    @NotNull private int pageNo;
    @NotNull private int pageSize;
}
