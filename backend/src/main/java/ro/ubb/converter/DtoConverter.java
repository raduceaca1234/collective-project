package ro.ubb.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.dto.BytesAnnouncementDto;
import ro.ubb.dto.LoginDataDto;
import ro.ubb.dto.OrderingAndFilteringDto;
import ro.ubb.dto.PagedAnnouncementDto;
import ro.ubb.model.Announcement;
import ro.ubb.model.OrderingAndFilteringData;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Order;
import ro.ubb.model.enums.Status;
import ro.ubb.security.JWTUtil;

import java.util.Collections;
import java.util.List;

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
    log.debug("converting credentialsDto = {} to an User POJO with email", registerDto.getEmail());
    return User.builder()
        .firstName(registerDto.getFirstName())
        .lastName(registerDto.getLastName())
        .email(registerDto.getEmail())
        .password(registerDto.getPassword())
        .phoneNumber(registerDto.getPhoneNumber())
        .build();
  }

  public LoginDataDto convertSuccessfulLogin(User user) {
    String token = jwtUtil.createJWT(user.getId(), JWTUtil.DEFAULT_VALIDITY);
    return LoginDataDto.builder()
        .token(token)
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .build();
  }

  public Announcement convertAnnouncementDtoForPosting(AnnouncementDto announcementDto) {
    log.debug("converting announcement dto={} to an announcement POJO", announcementDto);
    return Announcement.builder()
        .user(
            User.builder()
                .id(Integer.parseInt(jwtUtil.decodeJWT(announcementDto.getOwnerId()).getId()))
                .build())
        .name(announcementDto.getName())
        .location(announcementDto.getLocation())
        .description(announcementDto.getDescription())
        .category(Category.valueOf(announcementDto.getCategory()))
        .status(Status.OPEN)
        .duration(announcementDto.getDuration())
        .pricePerDay(announcementDto.getPricePerDay())
        .build();
  }

  public BytesAnnouncementDto convertAnnouncementWithImages(
      Announcement announcement, List<Byte[]> imageBytes) {
    log.debug("converting announcement pojo={} to an announcement dto with images", announcement);
    return BytesAnnouncementDto.builder()
        .id(announcement.getId())
        .ownerId(jwtUtil.createJWT(announcement.getId(), JWTUtil.DEFAULT_VALIDITY))
        .name(announcement.getName())
        .description(announcement.getDescription())
        .location(announcement.getLocation())
        .category(String.valueOf(announcement.getCategory()))
        .status(String.valueOf(announcement.getStatus()))
        .duration(announcement.getDuration())
        .pricePerDay(announcement.getPricePerDay())
        .imageBytes(imageBytes)
        .build();
  }

  @Autowired
  public void setJwtUtil(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  public PagedAnnouncementDto convertAnnouncementForGetPaginated(Announcement announcement) {
    log.debug("converting announcement={}", announcement);
    long validityTimeMillis = 1000 * 600L;
    return PagedAnnouncementDto.builder()
        .id(announcement.getId())
        .name(announcement.getName())
        .description(announcement.getDescription())
        .location(announcement.getLocation())
        .category(announcement.getCategory().toString())
        .createdDate(announcement.getDate().toString())
        .duration(announcement.getDuration())
        .status(announcement.getStatus().toString())
        .pricePerDay(announcement.getPricePerDay())
        .ownerId(this.jwtUtil.createJWT(announcement.getUser().getId(), validityTimeMillis))
        .order(1)
        .build();
  }

  public OrderingAndFilteringData convertOrderingAndFilteringDto(
      OrderingAndFilteringDto orderingAndFilteringDto) {
    log.debug("converting ordering and filtering dto = {}", orderingAndFilteringDto.toString());
    return OrderingAndFilteringData.builder()
        .orderingField(getField(orderingAndFilteringDto))
        .order(getOrder(orderingAndFilteringDto))
        .priceMaximum(getMaxLimit(orderingAndFilteringDto))
        .priceMinimum(getMinLimit(orderingAndFilteringDto))
        .categories(getCategories(orderingAndFilteringDto))
        .minimumNumberOfDays(getMinDays(orderingAndFilteringDto))
        .build();
  }

  private String getField(OrderingAndFilteringDto orderingAndFilteringDto) {
    if (orderingAndFilteringDto.getOrderingDto() != null
        && orderingAndFilteringDto.getOrderingDto().getField() != null) {
      return orderingAndFilteringDto.getOrderingDto().getField();
    } else {
      return null;
    }
  }

  private Integer getMinDays(OrderingAndFilteringDto orderingAndFilteringDto) {
    if (orderingAndFilteringDto.getDurationFilteringDto() != null
        && orderingAndFilteringDto.getDurationFilteringDto().getMinDays() != null) {
      return orderingAndFilteringDto.getDurationFilteringDto().getMinDays();
    } else {
      return null;
    }
  }

  private List<Category> getCategories(OrderingAndFilteringDto orderingAndFilteringDto) {
    if (orderingAndFilteringDto.getCategoryFilteringDto() != null
        && orderingAndFilteringDto.getCategoryFilteringDto().getCategories() != null) {
      return orderingAndFilteringDto.getCategoryFilteringDto().getCategories();
    } else {
      return null;
    }
  }

  private Integer getMinLimit(OrderingAndFilteringDto orderingAndFilteringDto) {
    if (orderingAndFilteringDto.getPriceFilteringDto() != null
        && orderingAndFilteringDto.getPriceFilteringDto().getMinLimit() != null) {
      return orderingAndFilteringDto.getPriceFilteringDto().getMinLimit();
    } else {
      return null;
    }
  }

  private Integer getMaxLimit(OrderingAndFilteringDto orderingAndFilteringDto) {
    if (orderingAndFilteringDto.getPriceFilteringDto() != null
        && orderingAndFilteringDto.getPriceFilteringDto().getMaxLimit() != null) {
      return orderingAndFilteringDto.getPriceFilteringDto().getMaxLimit();
    } else {
      return null;
    }
  }

  private Order getOrder(OrderingAndFilteringDto orderingAndFilteringDto) {
    if (orderingAndFilteringDto.getOrderingDto() != null) {
      return orderingAndFilteringDto.getOrderingDto().getOrder() != null
          ? orderingAndFilteringDto.getOrderingDto().getOrder()
          : Order.ASC;
    } else {
      return null;
    }
  }
}
