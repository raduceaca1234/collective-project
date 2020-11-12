package ro.ubb.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class LoginDataDto {
    @NotEmpty private String token;
    @NotEmpty private String firstName;
    @NotEmpty private String lastName;
    @NotEmpty private String email;
}
