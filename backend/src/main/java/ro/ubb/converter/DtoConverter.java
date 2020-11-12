package ro.ubb.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.dto.LoginDataDto;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;
import ro.ubb.security.JWTUtil;

@Component
@Slf4j
public class DtoConverter {
  private JWTUtil jwtUtil;

  public User convertCredentialsDto(ro.ubb.dto.CredentialsDto credentialsDto) {
    log.debug(
        "converting credentialsDto = {} to an User POJO with email", credentialsDto.getEmail());
    return User.builder()
        .email(credentialsDto.getEmail())
        .password(credentialsDto.getPassword())
        .build();
  }
  public User convertRegisterDto(ro.ubb.dto.RegisterDto registerDto) {
    log.debug(
            "converting credentialsDto = {} to an User POJO with email", registerDto.getEmail());
    return User.builder()
            .firstName(registerDto.getFirstName())
            .lastName(registerDto.getLastName())
            .email(registerDto.getEmail())
            .password(registerDto.getPassword())
            .phoneNumber(registerDto.getPhoneNumber())
            .build();
  }

  public LoginDataDto convertSuccessfulLogin(User user) {
    String token = jwtUtil.createJWT(user.getId(), 86400000);
    return LoginDataDto.builder().token(token).email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).build();
  }

  public Announcement convertAnnouncementDtoForPosting(AnnouncementDto announcementDto){
    log.debug("converting announcement dto={} to an announcement POJO", announcementDto);
    return Announcement.builder()
            .user(User.builder().id(announcementDto.getOwnerId()).build())
            .name(announcementDto.getName())
            .location(announcementDto.getLocation())
            .description(announcementDto.getDescription())
            .category(Category.valueOf(announcementDto.getCategory()))
            .status(Status.OPEN)
            .duration(announcementDto.getDuration())
            .pricePerDay(announcementDto.getPricePerDay())
            .build();
  }

  @Autowired
  public void setJwtUtil(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }
}
