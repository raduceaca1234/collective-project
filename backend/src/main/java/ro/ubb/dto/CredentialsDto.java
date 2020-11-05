package ro.ubb.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialsDto {
    @NotNull @Email private String email;
    @NotNull private String password;
}
