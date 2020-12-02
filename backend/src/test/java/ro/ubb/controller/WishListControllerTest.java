package ro.ubb.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.ubb.model.Announcement;
import ro.ubb.model.User;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;
import ro.ubb.security.JWTUtil;
import ro.ubb.service.AnnouncementService;
import ro.ubb.service.UserService;
import ro.ubb.service.WishListService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    public void testAddWishList() throws Exception {
        given(userService.register(User.builder().email("ana@yahoo.com").password("Password!123").build())).willReturn(true);
        Claims claims = new DefaultClaims();
        claims.setId("1");
        given(jwtUtil.createJWT(any(Integer.class),any(Long.class))).willReturn("token");
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
                post("/api/wishlist/{token}/1",token)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

    }
}
