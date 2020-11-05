package ro.ubb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.CredentialsDto;
import ro.ubb.dto.LoginDataDto;
import ro.ubb.model.User;
import ro.ubb.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
  @Autowired private MockMvc mvc;
  @MockBean private UserService userService;
  @MockBean private DtoConverter dtoConverter;

  @Test
  public void loginUser_userExists() throws Exception {
    User user = User.builder().email("some@valid.email").password("password").build();
    LoginDataDto loginDataDto = LoginDataDto.builder().id(1).build();
    given(dtoConverter.convertCredentialsDto(any(CredentialsDto.class))).willReturn(user);
    given(dtoConverter.convertSuccessfulLogin(any(Integer.class))).willReturn(loginDataDto);
    given(userService.validUserCredentials(any(User.class))).willReturn(true);
    given(userService.login(any(User.class))).willReturn(1);
    mvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "    \"email\": \"some@valid.email\",\n"
                        + "    \"password\": \"password\"\n"
                        + "}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1));
    verify(userService).login(user);
    verify(userService).validUserCredentials(user);
    verify(dtoConverter)
        .convertCredentialsDto(
            CredentialsDto.builder().email("some@valid.email").password("password").build());
    verify(dtoConverter).convertSuccessfulLogin(1);
  }

  @Test
  public void loginUser_userDoesNotExist() throws Exception {
    User user = User.builder().email("email@email.email").password("password").build();
    given(dtoConverter.convertCredentialsDto(any(CredentialsDto.class))).willReturn(user);
    given(userService.validUserCredentials(any(User.class))).willReturn(false);
    mvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "    \"email\": \"some@invalid.email\",\n"
                        + "    \"password\": \"NOT FOUND\"\n"
                        + "}"))
        .andExpect(status().isNotFound());
    verify(userService).validUserCredentials(user);
    verify(dtoConverter)
        .convertCredentialsDto(
            CredentialsDto.builder().email("some@invalid.email").password("NOT FOUND").build());
  }
}
