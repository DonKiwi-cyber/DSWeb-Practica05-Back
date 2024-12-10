/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author ian
 */

@Configuration //Indicativo de una clase de seguridad que se iniciará al momento de correr la app
@EnableWebSecurity //Indica la activación de seguridad web, además de marcar que esta clase será la que almacenará todo lo referente a la seguridad
public class SecurityConfig {
    
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }
    
    //Bean que se encarga de verificar la información de los usuarios que ingresarán en la API
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    //Bean que se encarga de encriptar las contraseñas
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    //Bean que incorpora el filtro de seguridad de json web token 
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
    
    //Bean que establece una cadena de filtros en la app, donde se determinan los permisos y entradas de acceso según el rol del usuario
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
        // Configuración CSRF deshabilitada
        .csrf(csrf -> csrf.disable())
        // Manejo de excepciones con un AuthenticationEntryPoint personalizado
        .exceptionHandling(exceptionHandling -> 
            exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        // Política de gestión de sesiones sin estado
        .sessionManagement(sessionManagement -> 
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Autorización de solicitudes HTTP
        .authorizeHttpRequests(authorize -> 
            authorize
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, Constantes.VENTAS_URL, Constantes.CLIENTES_URL, Constantes.PRODUCTOS_URL).hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST,Constantes.CLIENTES_URL, Constantes.PRODUCTOS_URL).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, Constantes.CLIENTES_URL, Constantes.PRODUCTOS_URL).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, Constantes.CLIENTES_URL, Constantes.PRODUCTOS_URL).hasAuthority("ADMIN")
                .anyRequest().authenticated())
        // Configuración básica de autenticación HTTP
        .httpBasic(httpBasic -> httpBasic.disable()); // Puedes configurarlo según tus necesidades
    // Agregar un filtro personalizado antes del UsernamePasswordAuthenticationFilter
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }
}
