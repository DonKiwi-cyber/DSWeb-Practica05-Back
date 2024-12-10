/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uv.dsweb.practica05.DTOs.ProductoDTO;
import org.uv.dsweb.practica05.Entities.ProductoEntity;
import org.uv.dsweb.practica05.Repository.ProductoRepository;

/**
 *
 * @author ian
 */
@RestController
@RequestMapping("/api/productos/")
public class ProductoRestController {
    
    private final ProductoRepository productoRepository;

    public ProductoRestController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<String> postProducto(@RequestBody ProductoDTO productoDTO){
        try {
            ProductoEntity producto = new ProductoEntity();
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setStock(productoDTO.getStock());
            productoRepository.save(producto);
            
            return new ResponseEntity<>("Producto creado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping(headers = "Accept=application/json")
    public ResponseEntity<List<ProductoEntity>> getProductos(){
        try {
            List<ProductoEntity> lista = productoRepository.findAll();
            if(lista.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value="id/{id}", headers = "Accept=application/json")
    public ResponseEntity<ProductoEntity> getProductoById(@PathVariable Long id){
        try {
            Optional<ProductoEntity> producto = productoRepository.findById(id);
            if(producto.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(producto.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value="desc/{desc}", headers = "Accept=application/json")
    public ResponseEntity<ProductoEntity> getProductoByDescripcion(@PathVariable String desc){
        try {
            Optional<ProductoEntity> producto = productoRepository.getByDescripcion(desc);
            if(producto.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(producto.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value="{id}", headers = "Accept=application/json")
    public ResponseEntity<String> putProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO){
        try {
            Optional<ProductoEntity> producto = productoRepository.findById(id);
            if(producto.isEmpty()){
                return new ResponseEntity<>("ID incorrecta o inexistente", HttpStatus.NOT_FOUND);
            }
            producto.get().setDescripcion(productoDTO.getDescripcion());
            producto.get().setPrecio(productoDTO.getPrecio());
            producto.get().setStock(productoDTO.getStock());
            productoRepository.save(producto.get());
            
            return new ResponseEntity<>("Producto actualizado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Problema interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping(value="{id}", headers = "Accept=application/json")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id){
        try {
            if(productoRepository.findById(id).isEmpty()){
                return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
            }
            productoRepository.deleteById(id);
            return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Problema interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
