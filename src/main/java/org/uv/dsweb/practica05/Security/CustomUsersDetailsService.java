/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.uv.dsweb.practica05.Models.RolModel;
import org.uv.dsweb.practica05.Models.UsuarioModel;
import org.uv.dsweb.practica05.Repository.UsuarioReposioty;

/**
 *
 * @author ian
 */
@Service
public class CustomUsersDetailsService implements UserDetailsService{
    
    private final UsuarioReposioty usuarioRepository;
    
    @Autowired
    public CustomUsersDetailsService(UsuarioReposioty usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    //Método para mapear a todos los roles registrados en forma de lista
    public Collection<GrantedAuthority> mapToAuthorities(List<RolModel> roles){
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
    }

    //Método que encuentra el usuario por su nombre y regresa sus datos (nombre contraseña y roles)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioModel usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado en la base de datos"));
        return new User(usuario.getUsername(), usuario.getPassword(), mapToAuthorities(usuario.getRoles()));
    }
}
