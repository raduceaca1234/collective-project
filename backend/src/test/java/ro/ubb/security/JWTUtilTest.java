package ro.ubb.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JWTUtilTest {
  @Test
  public void jwtEncryptionDecryption() {
    JWTUtil jwtUtil = new JWTUtil("dummy secret key");
    String token = jwtUtil.createJWT(5, 100000);
    assertEquals(5, Long.parseLong(jwtUtil.decodeJWT(token).getId()));
  }
}
