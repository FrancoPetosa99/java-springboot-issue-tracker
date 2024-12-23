package  com.issue_tracker.issue_tracker.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class JwtToken {

    private static final String SECRET_KEY = "LeoAndJaviMyFavoriteTeachers";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 horas    

    public static Builder generateToken() {
        return new Builder();
    }

    public static Map<String, Object> getPayload(String token) {
        try {

            Claims claims = Jwts
                            .parser()
                            .setSigningKey(SECRET_KEY)
                            .parseClaimsJws(token)
                            .getBody();
                        
            if(claims.getExpiration().before(new Date())) {
                throw new IllegalArgumentException(); 
            }

            return new HashMap<>(claims);

        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public static class Builder {

        private Map<String, Object> claims;
        private String subject;
        private long time;

        public Builder() {
            this.time = EXPIRATION_TIME;
            this.claims = new HashMap<>();
        }

        public Builder addClaim(String key, Object value) {
            claims.put(key, value);
            return this;
        }

        public Builder setSubject(String value) {
            this.subject = value;
            return this; 
        }

        public Builder setTimeMinutes(int value) {
            // convertir minutos a milisegundos
            this.time = (long) value * 60 * 1000;
            return this;
        }

        public Builder setTimeHours(int value) {
            // convertir horas a milisegundos
            this.time = (long) value * 60 * 60 * 1000;
            return this;
        }

        public String build() {
            return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + time))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
        }
    }
}