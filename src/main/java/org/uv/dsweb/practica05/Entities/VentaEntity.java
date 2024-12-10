/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author ian
 */
@Entity
@Table(name="venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;
    
    @CreationTimestamp
    @Column(nullable=false, updatable=false)
    private LocalDateTime fecha;
    
    @Column(name="nombre_cliente")
    private String nombreCliente;
    
    @Column(name="correo_cliente")
    private String correoCliente;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteEntity cliente;
    
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleVentaEntity> detalleVentas;
}
