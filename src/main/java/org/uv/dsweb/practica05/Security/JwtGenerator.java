/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 *
 * @author ian
 */
@Component
public class JwtGenerator {
    
    //Método de generación del token de acceso por medio de authentication
    public String generateToken(Authentication auth){
        String username = auth.getName();
        //Momento en que se generó el token
        Date currentTime = new Date();
        //Tiempo de uso limitado del token
        Date tokenExpiration = new Date(currentTime.getTime() + Constantes.JWT_EXPIRATION_TOKEN);
        
        //generación del token
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentTime)
                .setExpiration(tokenExpiration)
                .signWith(SignatureAlgorithm.HS512, Constantes.JWT_FIRM)
                .compact();
        
        return token;
    }
    
    //Método para extraer el nombre del usuario del token
    public String obtainUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(Constantes.JWT_FIRM)
                .parseClaimsJws(token) //Confirma que el token obtenido esté firmado y no haya sido alterado
                .getBody();
        
        return claims.getSubject();
    }
    
    //Método para la validación del token
    public boolean validateToken(String token){
        try {
            Jwts.parser()
                .setSigningKey(Constantes.JWT_FIRM)
                .parseClaimsJws(token); //Confirma que el token obtenido esté firmado y no haya sido alterado
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("El token ha expirado o es incorrecto");
        }
    }
}
