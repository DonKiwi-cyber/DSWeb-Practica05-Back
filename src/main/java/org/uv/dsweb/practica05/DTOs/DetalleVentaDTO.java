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
public class DetalleVentaDTO {

    private Long idProducto;
    private String descripcionProducto;
    private Double precioProducto;
    private Integer cantidadProducto;
    private Double total;

    public DetalleVentaDTO(Long idProducto, String descripcionProducto, Double precioProducto, Integer cantidadProducto, Double total) {
        this.idProducto = idProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.cantidadProducto = cantidadProducto;
        this.total = total;
    }
    
    public DetalleVentaDTO(){
        
    }
}
