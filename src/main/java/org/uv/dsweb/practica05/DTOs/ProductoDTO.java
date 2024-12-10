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
public class ProductoDTO {
    
    private String descripcion;
    private Double precio;
    private Long stock;

    public ProductoDTO(String descripcion, Double costo, Long stock) {
        this.descripcion = descripcion;
        this.precio = costo;
        this.stock = stock;
    }
}
