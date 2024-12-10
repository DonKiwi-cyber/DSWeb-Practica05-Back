/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Controllers;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uv.dsweb.practica05.DTOs.AuthResponseDTO;
import org.uv.dsweb.practica05.DTOs.LoginDTO;
import org.uv.dsweb.practica05.DTOs.RegisterDTO;
import org.uv.dsweb.practica05.Models.RolModel;
import org.uv.dsweb.practica05.Models.UsuarioModel;
import org.uv.dsweb.practica05.Repository.RolRepository;
import org.uv.dsweb.practica05.Repository.UsuarioReposioty;
import org.uv.dsweb.practica05.Security.JwtGenerator;

/**
 *
 * @author ian
 */
@RestController
@RequestMapping("/api/auth/")
public class AuthRestController {
    
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final UsuarioReposioty usuarioReposioty;
    private final JwtGenerator jwtGenerator;
    
    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RolRepository rolRepository, UsuarioReposioty usuarioReposioty, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
        this.usuarioReposioty = usuarioReposioty;
        this.jwtGenerator = jwtGenerator;
    }
    
    //Método clásico de registro
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        if(usuarioReposioty.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        UsuarioModel usuario = new UsuarioModel();
        usuario.setUsername(registerDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        RolModel rol = rolRepository.findByName("USER").get();
        usuario.setRoles(Collections.singletonList(rol));
        usuarioReposioty.save(usuario);
        
        return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
    }
    
    @PostMapping("registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterDTO registerDTO){
        if(usuarioReposioty.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        UsuarioModel usuario = new UsuarioModel();
        usuario.setUsername(registerDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        RolModel rol = rolRepository.findByName("ADMIN").get();
        usuario.setRoles(Collections.singletonList(rol));
        usuarioReposioty.save(usuario);
        
        return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
    }
    
    //Método de inicio de sesión
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
}
