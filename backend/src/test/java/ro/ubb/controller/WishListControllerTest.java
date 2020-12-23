package ro.ubb.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.ubb.converter.DtoConverter;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.Wishlist;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;
import ro.ubb.security.JWTUtil;
import ro.ubb.service.AnnouncementService;
import ro.ubb.service.UserService;
import ro.ubb.service.WishListService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Set<Announcement>announcements =new HashSet<>();
        announcements.add(announcement);
        Wishlist wishlist = new Wishlist();
        wishlist.setWantedAnnouncements(announcements);
        given(wishListService.getWishListByOwnerId(1)).willReturn(wishlist);
        List<Announcement> announcementsInWishList= wishListService.getWishListByOwnerId(1).getWantedAnnouncements().stream().collect(Collectors.toList());

        Pageable pageable1 = PageRequest.of(0, 5);
        Pageable pageable2 = PageRequest.of(1, 5);
        Pageable pageable3 = PageRequest.of(2, 5);
        given(wishListService.getWishListByOwnerId(1).getWantedAnnouncements().stream().collect(Collectors.toList())).willReturn(announcementsInWishList);
        given(wishListService.getAllAnnouncementPaged(pageable1, 1))
                .willReturn(new PageImpl<>(announcementsInWishList.subList(0, 5)));
        given(wishListService.getAllAnnouncementPaged(pageable2, 1))
                .willReturn(new PageImpl<>(announcementsInWishList.subList(5, 10)));
        given(wishListService.getAllAnnouncementPaged(pageable3, 1))
                .willReturn(new PageImpl<>(announcementsInWishList.subList(10, 11)));
        String result =
                mockMvc
                        .perform(get("/api/wishlist/1/1/5"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        verify(wishListService).getAllAnnouncementPaged(any(Pageable.class), 1);
        verify(dtoConverter).convertAnnouncementForGetPaginated(announcementService.getAll().get(5));
        verify(dtoConverter).convertAnnouncementForGetPaginated(announcementService.getAll().get(9));
    }

}
