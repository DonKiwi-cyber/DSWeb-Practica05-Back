/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Models;

import jakarta.persistence.*;
import lombok.*;

/**
 *
 * @author ian
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="rol")
public class RolModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rol")
    Long id;
    @Column(name="name")
    String name;
}
