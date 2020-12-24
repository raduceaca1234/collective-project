package ro.ubb.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import net.logstash.logback.encoder.org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ro.ubb.converter.DtoConverter;
import ro.ubb.dto.AnnouncementDto;
import ro.ubb.dto.BytesAnnouncementDto;
import ro.ubb.dto.OrderingAndFilteringDto;
import ro.ubb.dto.PagedAnnouncementDto;
import ro.ubb.model.*;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Order;
import ro.ubb.model.enums.Status;
import ro.ubb.security.JWTUtil;
import ro.ubb.service.*;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
public class WishListControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishListService wishListService;
    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private UserService userService;
    @MockBean
    private AnnouncementService announcementService;
    @MockBean
    private DtoConverter dtoConverter;

    @Test
    public void testAddWishList() throws Exception {
        given(userService.register(User.builder().email("ana@yahoo.com").password("Password!123").build())).willReturn(true);
        Claims claims = new DefaultClaims();
        claims.setId("1");
        given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
        given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
        given(userService.existsById(1)).willReturn(true);
        Announcement announcement = Announcement.builder().user(User.builder()
                .id(2).build())
                .name("test_name1")
                .description("test_description1")
                .location("test_location1")
                .category(Category.AGRICULTURE)
                .duration(30)
                .pricePerDay(50)
                .status(Status.OPEN)
                .build();
        Announcement announcementAdded = announcement;
        announcementAdded.setId(1);
        given(announcementService.add(announcement)).willReturn(announcementAdded);
        String token = jwtUtil.createJWT(1, JWTUtil.DEFAULT_VALIDITY);
        mockMvc.perform(
                post("/api/wishlist/{token}/1", token)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

    }

    @Test
    void testGetAnnouncementsPaginated() throws Exception {
        given(userService.register(User.builder().email("ana@yahoo.com").password("Password!123").build())).willReturn(true);
        Claims claims = new DefaultClaims();
        claims.setId("1");
        given(jwtUtil.createJWT(any(Integer.class), any(Long.class))).willReturn("token");
        given(jwtUtil.decodeJWT(any(String.class))).willReturn(claims);
        given(userService.existsById(1)).willReturn(true);
        Announcement announcement = Announcement.builder().user(User.builder()
                .id(2).build())
                .name("test_name1")
                .description("test_description1")
                .location("test_location1")
                .category(Category.AGRICULTURE)
                .duration(30)
                .pricePerDay(50)
                .status(Status.OPEN)
                .build();
        Announcement announcementAdded = announcement;
        announcementAdded.setId(1);
        given(announcementService.add(announcement)).willReturn(announcementAdded);
        Set<Announcement> announcements =new HashSet<>();
        announcements.add(announcement);
        Wishlist wishlist = new Wishlist();
        wishlist.setWantedAnnouncements(announcements);
        given(wishListService.getWishListByOwnerId(2)).willReturn(wishlist);
        given(dtoConverter.convertAnnouncementForGetPaginated(any(Announcement.class))).willReturn(new PagedAnnouncementDto());
        given(wishListService.getAllAnnouncementPaged(any(Pageable.class), anyInt())).willReturn((new PageImpl<>(Arrays.asList(announcement)))); // metoda asta e apelata de controller dar nu e mockuita
        Set<Announcement> announcementsInWishList = new HashSet<>();
        announcementsInWishList.add(Announcement.builder().id(1).build());
        announcementsInWishList.add(Announcement.builder().id(2).build());
        String token = jwtUtil.createJWT(1, JWTUtil.DEFAULT_VALIDITY);
        mockMvc.perform(
                get("/api/wishlist/{token}/1/5", token)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(wishListService).getAllAnnouncementPaged(any(Pageable.class), eq(2));
    }

}
