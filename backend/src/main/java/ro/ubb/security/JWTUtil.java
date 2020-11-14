package ro.ubb.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

  public static final long DEFAULT_VALIDITY = 86400000; //24hrs

  private final String SECRET_KEY;

  public JWTUtil(@Value("${security.secretKey}") String secretKey) {
    SECRET_KEY = secretKey;
  }

  public String createJWT(Integer id, long validityInMillis) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    JwtBuilder builder =
        Jwts.builder()
            .setId(id.toString())
            .setIssuedAt(now)
            .setExpiration(new Date(nowMillis + validityInMillis))
            .signWith(signatureAlgorithm, signingKey);

    return builder.compact();
  }

  public Claims decodeJWT(String jwt) {
    return Jwts.parser()
        .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
        .parseClaimsJws(jwt)
        .getBody();
  }
}
