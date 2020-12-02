package ro.ubb.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.CredentialsDto;
import ro.ubb.dto.LoginDataDto;
import ro.ubb.dto.RegisterDto;
import ro.ubb.model.User;
import ro.ubb.security.JWTUtil;
import ro.ubb.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
  @MockBean private JWTUtil jwtUtil;

  @Test
  void loginUser_userExists() throws Exception {
    User user =
        User.builder()
            .id(1)
            .firstName("first")
            .lastName("last")
            .email("some@valid.email")
            .password("password")
            .build();
    LoginDataDto loginDataDto =
        LoginDataDto.builder()
            .token("token")
            .firstName("first")
            .lastName("last")
            .email("some@valid.email")
            .build();
    Claims claims = new DefaultClaims();
    claims.setId("1");
    given(jwtUtil.createJWT(anyInt(), anyLong())).willReturn("token");
    given(jwtUtil.decodeJWT(anyString())).willReturn(claims);
    given(dtoConverter.convertCredentialsDto(any(CredentialsDto.class))).willReturn(user);
    given(dtoConverter.convertSuccessfulLogin(any(User.class))).willReturn(loginDataDto);
    given(userService.validUserCredentials(any(User.class))).willReturn(true);
    given(userService.login(any(User.class))).willReturn(user);
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
        .andExpect(jsonPath("$.token").value("token"))
        .andExpect(jsonPath("$.firstName").value("first"))
        .andExpect(jsonPath("$.lastName").value("last"))
        .andExpect(jsonPath("$.email").value("some@valid.email"));
    verify(userService).login(user);
    verify(userService).validUserCredentials(user);
    verify(dtoConverter)
        .convertCredentialsDto(
            CredentialsDto.builder().email("some@valid.email").password("password").build());
    verify(dtoConverter).convertSuccessfulLogin(user);
  }

  @Test
  void loginUser_userDoesNotExist() throws Exception {
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

  @Test
  void registerUser_successful() throws Exception {
    User user =
        User.builder()
            .firstName("Ana")
            .lastName("Gloria")
            .email("some@valid.email")
            .password("Password!123")
            .phoneNumber("0723232323")
            .build();
    given(dtoConverter.convertRegisterDto(any(RegisterDto.class))).willReturn(user);
    given(userService.register(any(User.class))).willReturn(true);
    mvc.perform(
            post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\n"
                        + "    \"firstName\": \"Ana\",\n"
                        + "    \"lastName\": \"Gloria\",\n"
                        + "    \"email\": \"some@valid.email\",\n"
                        + "    \"password\": \"Password!123\",\n"
                        + "    \"phoneNumber\": \"0723232323\"\n"
                        + "}"))
        .andExpect(status().isOk());
    verify(userService).register(user);
    verify(dtoConverter)
        .convertRegisterDto(
            RegisterDto.builder()
                .firstName("Ana")
                .lastName("Gloria")
                .email("some@valid.email")
                .password("Password!123")
                .phoneNumber("0723232323")
                .build());
  }
}
