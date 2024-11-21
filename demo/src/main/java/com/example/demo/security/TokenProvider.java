package com.example.demo.security;

import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    @Value("${security.token.VALID_DURATION_DAY}")
    private final long VALID_DURATION_DAY = 1;
    private final Key key = Jwts.SIG.HS512.key().build();
    private final UserService userService;

    public TokenProvider(UserService userService) {
        this.userService = userService;
    }
    //    TokenProvider(@Value("${security.token.SECRET_KEY}") String secretKeyPlain)  {
//        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
//        secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
//    }

    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(Instant.now().plus(VALID_DURATION_DAY, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(key)
                .subject(userEntity.getId())
                .issuer("demo app")
                .issuedAt(new Date())
                .expiration(expiryDate)
                .compact();
    }

    public UserEntity validateAndGetUserId(String token) {
        Claims claims = Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload();
        return userService.getById(claims.getSubject());
    }
}
