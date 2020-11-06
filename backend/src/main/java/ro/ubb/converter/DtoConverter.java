package ro.ubb.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.dto.LoginDataDto;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;

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
}
