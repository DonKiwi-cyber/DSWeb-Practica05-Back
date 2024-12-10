/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.dsweb.practica05.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.dsweb.practica05.Entities.DetalleVentaEntity;

/**
 *
 * @author ian
 */
public interface DetalleVentaRepository extends JpaRepository<DetalleVentaEntity, Long> {
}
