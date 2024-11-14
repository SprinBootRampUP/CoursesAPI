package Utils;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public class Utils {

    public  static Long getIdFromToken( Authentication authentication ){
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Long userId =  (Long) jwt.getClaims().get("userId");
        return userId;
    }

    public  static String getToken( Authentication authentication ){
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getTokenValue();
    }

}
