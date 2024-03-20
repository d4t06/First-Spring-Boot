package com.example.demo.security;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtProvider {

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    // public static DateTimeFormatter ofLaDateTimeFormatter(FormatStyle timeStyle)

    public JwtProvider(
            JwtDecoder jwtDecoder,
            JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String getJoinAuthoritiesFromPrefix(Collection<? extends GrantedAuthority> prefixRoles) {
        // call method getAuthorities of MyUserPrincipal
        return prefixRoles.stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(" "));
    }

    public String getJoinAuthoritiesFromUserRole(String userRole) {
        Collection<? extends GrantedAuthority> prefixRoles = getPrefixAuthorities(userRole);
        return getJoinAuthoritiesFromPrefix(prefixRoles);
    }

    public Collection<? extends GrantedAuthority> getPrefixAuthorities(String userRole) {
        return Arrays.stream(StringUtils.tokenizeToStringArray(userRole, " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    public String generateToken(String authorities, String username, long expiresIn) {
        Instant now = Instant.now();
        Instant expireAt = now.plus(expiresIn, ChronoUnit.SECONDS);

        System.out.println("expireAt: " + LocalTime.ofInstant(expireAt, ZoneId.of("UTC+7")).toString());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expireAt)
                .subject(username)
                .claim("authorities", authorities)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(String username, long expiresIn) {
        Instant now = Instant.now();
        Instant expireAt = now.plus(expiresIn, ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expireAt)
                .subject(username)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String parserUsernameFromToken(String token) {
        try {
            Jwt jwt = this.jwtDecoder.decode(token);
            return jwt.getSubject();
        } catch (Exception e) {
            throw new InvalidBearerTokenException("");
        }
    }

}
