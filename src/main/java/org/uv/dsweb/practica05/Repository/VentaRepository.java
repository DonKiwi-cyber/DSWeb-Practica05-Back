/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.dsweb.practica05.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.dsweb.practica05.Entities.VentaEntity;

/**
 *
 * @author ian
 */
public interface VentaRepository extends JpaRepository<VentaEntity, Long>{
    
}
