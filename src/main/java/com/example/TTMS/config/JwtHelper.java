package com.example.TTMS.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {

    private static final String USER_DETAILS = "userDetails";
    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;

    public JwtHelper(JwtDecoder jwtDecoder, JwtEncoder jwtEncoder) {
        this.jwtDecoder = jwtDecoder;
        this.jwtEncoder = jwtEncoder;
    }

    public String createJwtForClaims(String subject, Map<String, Object> claimMap) {
        Instant now = Instant.now();

        String role = (String) claimMap.get("role"); 

        List<String> authorities = List.of(role);

        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now).subject(subject)
                .expiresAt(now.plus(1, ChronoUnit.DAYS)).claim(USER_DETAILS, claimMap)
                .claim("authorities", authorities)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    
}
