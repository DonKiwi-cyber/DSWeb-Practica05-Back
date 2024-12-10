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
//Devuelve la información del accesToken junto con el tipo que tenga
@Data
public class AuthResponseDTO {
    private String accesToken;
    private String type = "Bearer ";

    public AuthResponseDTO(String token) {
        this.accesToken = token;
    }
}
