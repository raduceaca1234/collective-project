package ro.ubb.converter;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.dto.LoginDataDto;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;
import ro.ubb.security.JWTUtil;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

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

  public AnnouncementDto convertAnnouncementWithoutImages(Announcement announcement) {
    log.debug("converting announcement pojo ={} to an announcement dto", announcement);
    return AnnouncementDto.builder()
            .id(announcement.getId())
            .ownerId(announcement.getUser().getId())
            .name(announcement.getName())
            .description(announcement.getDescription())
            .location(announcement.getLocation())
            .category(String.valueOf(announcement.getCategory()))
            .status(String.valueOf(announcement.getStatus()))
            .duration(announcement.getDuration())
            .pricePerDay(announcement.getPricePerDay())
            .build();
  }

  public AnnouncementDto convertAnnouncementWithImages(Announcement announcement, List<Byte[]> imageBytes) {
    AnnouncementDto dto = AnnouncementDto.builder()
            .id(announcement.getId())
            .ownerId(announcement.getUser().getId())
            .name(announcement.getName())
            .description(announcement.getDescription())
            .location(announcement.getLocation())
            .category(String.valueOf(announcement.getCategory()))
            .status(String.valueOf(announcement.getStatus()))
            .duration(announcement.getDuration())
            .pricePerDay(announcement.getPricePerDay())
            .build();
    dto.setImages(imageBytes.stream().map(bytes -> new MultipartFile() {
      @Override
      public String getName() {
        return null;
      }

      @Override
      public String getOriginalFilename() {
        return null;
      }

      @Override
      public String getContentType() {
        return null;
      }

      @Override
      public boolean isEmpty() {
        return bytes==null || bytes.length==0;
      }

      @Override
      public long getSize() {
        return bytes.length;
      }

      @Override
      public byte[] getBytes() throws IOException {
        return ArrayUtils.toPrimitive(bytes);
      }

      @Override
      public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(ArrayUtils.toPrimitive(bytes));
      }

      @Override
      public void transferTo(File file) throws IOException, IllegalStateException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(ArrayUtils.toPrimitive(bytes));
        fos.close();
      }
    }).collect(Collectors.toList()));
    return dto;
  }

  @Autowired
  public void setJwtUtil(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

}
