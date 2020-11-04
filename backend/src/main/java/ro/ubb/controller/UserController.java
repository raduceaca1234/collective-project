package ro.ubb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.CredentialsDto;
import ro.ubb.model.User;
import ro.ubb.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
  private UserService userService;
  private DtoConverter dtoConverter;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid CredentialsDto credentialsDto) {
    log.debug("Got login call with email = {}", credentialsDto.getEmail());
    User user = dtoConverter.convertCredentialsDto(credentialsDto);
    if (userService.validUserCredentials(user)) {
      log.debug("User with email = {} successfully logged in", credentialsDto.getEmail());
      return ResponseEntity.ok(dtoConverter.convertSuccessfulLogin(userService.login(user)));
    } else {
      log.error(
          "User with email = {} couldn't be found or has bad credentials.",
          credentialsDto.getEmail());
      return ResponseEntity.notFound().build();
    }
  }

  @Autowired
  public void setDtoConverter(DtoConverter dtoConverter) {
    this.dtoConverter = dtoConverter;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }
}
