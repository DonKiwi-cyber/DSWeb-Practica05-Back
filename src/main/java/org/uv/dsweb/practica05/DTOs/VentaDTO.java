/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.DTOs;

import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *
 * @author ian
 */
@Data
public class VentaDTO {
    
    private Long idCliente;
    private String nombreCliente;
    private String correoCliente;
    private List<DetalleVentaDTO> listaDetalles;

    public VentaDTO(Long idCliente, String nombreCliente, String correoCliente, List<DetalleVentaDTO> listaDetalles) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.correoCliente = correoCliente;
        this.listaDetalles = listaDetalles;
    }
}
