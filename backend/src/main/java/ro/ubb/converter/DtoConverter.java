package ro.ubb.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.ubb.dto.LoginDataDto;
import ro.ubb.model.User;

@Component
@Slf4j
public class DtoConverter {
  public User convertCredentialsDto(ro.ubb.dto.CredentialsDto credentialsDto) {
    log.debug(
        "converting credentialsDto = {} to an User POJO with email", credentialsDto.getEmail());
    return User.builder()
        .email(credentialsDto.getEmail())
        .password(credentialsDto.getPassword())
        .build();
  }

  public Object convertSuccessfulLogin(Integer loggedInUserId) {
    return LoginDataDto.builder().id(loggedInUserId).build();
  }
}
