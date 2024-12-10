/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Entities;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ian
 */
@Entity
@Table(name="detalle_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @ManyToOne()
    @JoinColumn(name = "id_venta")
    private VentaEntity venta;
    
    @ManyToOne()
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;
    
    @Column(name="precio_producto")
    private double precioProducto;
    
    @Column(name="cantidad_producto")
    private int cantidadProducto;
    
    @Column(name="descripcion_producto")
    private String descripcionProducto;
    
    @Column
    private double total;
}
