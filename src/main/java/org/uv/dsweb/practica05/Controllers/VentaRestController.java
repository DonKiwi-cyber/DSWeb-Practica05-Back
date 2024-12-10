/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Controllers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uv.dsweb.practica05.DTOs.DetalleVentaDTO;
import org.uv.dsweb.practica05.DTOs.VentaDTO;
import org.uv.dsweb.practica05.Entities.ClienteEntity;
import org.uv.dsweb.practica05.Entities.DetalleVentaEntity;
import org.uv.dsweb.practica05.Entities.ProductoEntity;
import org.uv.dsweb.practica05.Entities.VentaEntity;
import org.uv.dsweb.practica05.Repository.ClienteRepository;
import org.uv.dsweb.practica05.Repository.DetalleVentaRepository;
import org.uv.dsweb.practica05.Repository.ProductoRepository;
import org.uv.dsweb.practica05.Repository.VentaRepository;

/**
 *
 * @author ian
 */
@RestController
@RequestMapping("/api/ventas/")
public class VentaRestController {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    @Autowired
    VentaRestController(
            VentaRepository ventaRepository,
            ClienteRepository clienteRepository,
            ProductoRepository productoRepository,
            DetalleVentaRepository detalleVentaRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @PostMapping(headers="Accept=application/json")
    public ResponseEntity<String> createVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            VentaEntity venta = new VentaEntity();
            venta.setCliente(getCliente(ventaDTO.getIdCliente()));
            venta.setNombreCliente(ventaDTO.getNombreCliente());
            venta.setCorreoCliente(ventaDTO.getCorreoCliente());

            VentaEntity ventaID = ventaRepository.save(venta);
            
            List<DetalleVentaDTO> listaDetalles = ventaDTO.getListaDetalles();
            for (Iterator<DetalleVentaDTO> iterator = listaDetalles.iterator(); iterator.hasNext();) {
                DetalleVentaDTO next = iterator.next();
                DetalleVentaEntity detalleVenta = new DetalleVentaEntity();

                detalleVenta.setVenta(ventaID);
                detalleVenta.setProducto(getProducto(next.getIdProducto()));
                detalleVenta.setDescripcionProducto(next.getDescripcionProducto());
                detalleVenta.setPrecioProducto(next.getPrecioProducto());
                detalleVenta.setCantidadProducto(next.getCantidadProducto());
                detalleVenta.setTotal(next.getTotal());

                detalleVentaRepository.save(detalleVenta);
            }
            return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(headers="Accept=application/json")
    public ResponseEntity<List<DetalleVentaEntity>> getAllDetalleVentas(){
        return new ResponseEntity<>(detalleVentaRepository.findAll(), HttpStatus.FOUND);
    }
    
    @GetMapping(value="{id}",headers="Accept=application/json")
    public ResponseEntity<DetalleVentaEntity> getDetalleVentaById(@PathVariable Long id){
        Optional<DetalleVentaEntity> detalleVenta = detalleVentaRepository.findById(id);
        if(detalleVenta.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(detalleVenta.get(), HttpStatus.FOUND);
    }
    
    @GetMapping(value="reporte/{id}",headers="Accept=application/json")
    public ResponseEntity<byte[]> generarReporte(@PathVariable Long idVenta) {
        try {
            Optional<VentaEntity> opcional = ventaRepository.findById(idVenta);
            if(opcional.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            // Obtener los datos
            VentaEntity venta = opcional.get(); // Replace with actual ID
            List<DetalleVentaEntity> detalles = venta.getDetalleVentas();

            // Cargar el archivo JRXML
            InputStream inputStream = getClass().getResourceAsStream("/home/ian/NetBeansProjects/DSWebPractica05vD/reporte.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Configurar los parámetros
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", venta.getId());
            parameters.put("nombreCliente", venta.getNombreCliente());
            parameters.put("fecha", venta.getFecha());

            // Crear el datasource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detalles);

            // Llenar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exportar el reporte a un flujo de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            // Retornar el PDF como respuesta
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=reporte_venta.pdf")
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(outputStream.toByteArray());
        } catch (JRException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ClienteEntity getCliente(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public ProductoEntity getProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }
}
