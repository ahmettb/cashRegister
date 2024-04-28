package jwt;

import com.cashregister.authentacition.model.ERole;
import com.cashregister.authentacition.model.Role;
import com.cashregister.authentacition.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class   JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private String jwtSecret=System.getenv("JWT_KEY");

    private long jwtExpirationMs = 700000000; // 30 dakika
    public String generateJwtToken(Authentication authentication, Set<Role> roles) {

            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            String username = loggedInUser.getName();

            // Rol isimlerini içeren bir String listesi oluştur
            List<String> roleNames = roles.stream()
                    .map(role -> role.getName().toString())
                    .collect(Collectors.toList());

            Map<String, Object> rolesClaim = new HashMap<>();
            rolesClaim.put("roles", roleNames);

            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                    .addClaims(rolesClaim)
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();

            return token;
        }

//        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//        Map<String, Object> rolesClaim = new HashMap<>();
//        String username = loggedInUser.getName();
//        List<ERole> roleList = new ArrayList<>();
//        roles.forEach(role -> roleList.add(role.getName()));
//            rolesClaim.put("roles",roleList);
//       String token =Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime()+jwtExpirationMs))
//                .addClaims(rolesClaim)
//                .signWith(SignatureAlgorithm.HS256, jwtSecret)
//                .compact();
//       return  token;


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        getRoleFromJwt(token);
        Claims claims= Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();

        return  claims.getSubject();

    }

    public void getRoleFromJwt(String token)
    {
        Claims claims= Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();

    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("validateJwtToken   "+authToken);
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
