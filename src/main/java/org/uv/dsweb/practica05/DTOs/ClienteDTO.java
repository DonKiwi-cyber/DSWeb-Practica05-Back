/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.DTOs;

import lombok.Data;

/**
 *
 * @author ian
 */
@Data
public class ClienteDTO {
    
    private String nombre;
    private String correo;

    public ClienteDTO(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }
}
