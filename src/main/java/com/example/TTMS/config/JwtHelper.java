package com.example.TTMS.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import com.example.TTMS.dto.JwtResponse;

@Component
public class JwtHelper {

    private static final String USER_DETAILS = "userDetails";
    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;

    public JwtHelper(JwtDecoder jwtDecoder, JwtEncoder jwtEncoder) {
        this.jwtDecoder = jwtDecoder;
        this.jwtEncoder = jwtEncoder;
    }

    public JwtResponse createJwtForClaims(String subject, Map<String, Object> claimMap) {
        Instant now = Instant.now();
        Instant expiry = now.plus(30, ChronoUnit.MINUTES);

        String role = (String) claimMap.get("role");
        List<String> authorities = List.of(role);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(subject)
                .expiresAt(expiry)
                .claim("userDetails", claimMap)
                .claim("authorities", authorities)
                .build();

        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new JwtResponse(token, expiry);
    }

    public Map<String, Object> decodeToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaims();
    }

    public Map<String, Object> decodeFromAuth(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            throw new IllegalStateException("No valid authentication found in SecurityContext");
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaims();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            return Map.of();
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return (Map<String, Object>) jwt.getClaims().get(USER_DETAILS);
    }

    public String getRole() {
        Map<String, Object> userDetails = getUserDetails();
        return userDetails == null ? null : (String) userDetails.get("role");
    }

    public String getUserId() {
        Map<String, Object> userDetails = getUserDetails();
        return userDetails == null ? null : (String) userDetails.get("userId");
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                !(authentication instanceof AnonymousAuthenticationToken) &&
                authentication.isAuthenticated();
    }

}
