package ro.ubb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.CredentialsDto;
import ro.ubb.dto.RegisterDto;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto registerDto) {
        log.debug("Got register call with email = {}", registerDto.getEmail());
        User user = dtoConverter.convertRegisterDto(registerDto);
        try {
            if (userService.register(user)) {
                log.debug("User with email = {} successfully singed up", registerDto.getEmail());
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                log.debug("User with email = {} couldn't be created.",
                        registerDto.getEmail());
                return ResponseEntity.noContent().build();
            }
        } catch (HttpServerErrorException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
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
