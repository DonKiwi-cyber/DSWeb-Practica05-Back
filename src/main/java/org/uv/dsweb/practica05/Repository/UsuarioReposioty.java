/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.dsweb.practica05.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.dsweb.practica05.Models.UsuarioModel;

/**
 *
 * @author ian
 */
public interface UsuarioReposioty extends JpaRepository<UsuarioModel, Long> {

    //Método de búsqueda por nombre
    Optional<UsuarioModel> findByUsername(String usuarioNombre);

    //Método de verificación del usuario por nombre
    Boolean existsByUsername(String usuarioNombre);
}
