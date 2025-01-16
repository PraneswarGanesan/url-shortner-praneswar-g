package com.shortner.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shortner.service.UserDetailsImpl;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
    private String  jwtSecret = "U2FsdGVkX1+Z5q5g5jMk5Q5xQ2pOwYwQ8Ew4oBF5d9U=";
  
    private int jwtExpiration =  172800000;


    //Authroization -> Bearer <TOKEN>
    public String getJwtFromHeader(HttpServletRequest request){
        //helper-method
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
    public String generateToken(UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        String roles = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(","));
        return Jwts.builder()
        .subject(username)
        .claim("roles",roles)
        .issuedAt(new Date())
        .expiration(new Date((new Date().getTime() + jwtExpiration)))
        .signWith(key())
        .compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser()
        .verifyWith((SecretKey) key())
        .build().parseSignedClaims(token)
        .getPayload().getSubject();
    }
    public boolean validateToken(String authToken){
            try {
                Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(authToken);
                return true;
            } catch (JwtException e) {
                // Handle any JWT parsing or verification errors
                System.err.println("Invalid JWT token: " + e.getMessage());
                return false;
            } catch (Exception e) {
                // Handle other exceptions
                System.err.println("Unexpected error occurred: " + e.getMessage());
                return false;
            }
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }



}
