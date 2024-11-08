package org.course.api.Controller;


import jakarta.persistence.Entity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class test {


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(){
        return  "ADMIN  PAGE" ;
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String user(){
        return  "User  PAGE" ;
    }

    @GetMapping("/get-id")
    public String getUserId(Authentication authentication) {
        // Ensure the authentication is of type JwtAuthenticationToken
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = (Jwt) authentication.getPrincipal(); // Get the JWT from the authentication
            // Retrieve the 'id' claim from the JWT
            String roles = jwt.getClaims().get("roles").toString();
            String userId =  jwt.getClaims().get("userId").toString();

            // Replace "id" with your actual claim name
            return roles+userId;
        }
        return "No JWT Authentication found";
    }

}
