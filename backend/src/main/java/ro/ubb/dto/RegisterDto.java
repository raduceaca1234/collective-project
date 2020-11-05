package ro.ubb.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class RegisterDto {
    @NotNull private String firstName;
    @NotNull private String lastName;
    @NotNull
    @Email private String email;
    @NotNull private String password;
    @NotNull private String phoneNumber;
}
