/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica05.Controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uv.dsweb.practica05.DTOs.ClienteDTO;
import org.uv.dsweb.practica05.Entities.ClienteEntity;
import org.uv.dsweb.practica05.Repository.ClienteRepository;

/**
 *
 * @author ian
 */
@RestController
@RequestMapping("/api/clientes/")
public class ClienteRestController {
    
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteRestController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<String> postCliente(@RequestBody ClienteDTO clienteDTO){
        try {
            ClienteEntity cliente = new ClienteEntity();
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setCorreo(clienteDTO.getCorreo());
            clienteRepository.save(cliente);
            
            return new ResponseEntity<>("Cliente creado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(headers = "Accept=application/json")
    public ResponseEntity<List<ClienteEntity>> getClientes(){
        try {
            List<ClienteEntity> lista = clienteRepository.findAll();
            if(lista.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value="nombre/{nombre}", headers = "Accept=application/json")
    public ResponseEntity<ClienteEntity> getClienteByNombre(@PathVariable String nombre){
        try {
            Optional<ClienteEntity> cliente = clienteRepository.findByNombre(nombre);
            if(cliente.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value="id/{id}", headers = "Accept=application/json")
    public ResponseEntity<ClienteEntity> getClienteByNombre(@PathVariable Long id){
        try {
            Optional<ClienteEntity> cliente = clienteRepository.findById(id);
            if(cliente.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value="{id}", headers = "Accept=application/json")
    public ResponseEntity<String> putCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO){
        try {
            Optional<ClienteEntity> cliente = clienteRepository.findById(id);
            if(cliente.isEmpty()){
                return new ResponseEntity<>("ID incorrecta o inexistente", HttpStatus.NOT_FOUND);
            }
            cliente.get().setNombre(clienteDTO.getNombre());
            cliente.get().setCorreo(clienteDTO.getCorreo());
            clienteRepository.save(cliente.get());
            
            return new ResponseEntity<>("Cliente actualizado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Problema interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping(value="{id}", headers = "Accept=application/json")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id){
        try {
            if(clienteRepository.findById(id).isEmpty()){
                return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
            }
            clienteRepository.deleteById(id);
            return new ResponseEntity<>("Cliente eliminado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Problema interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
