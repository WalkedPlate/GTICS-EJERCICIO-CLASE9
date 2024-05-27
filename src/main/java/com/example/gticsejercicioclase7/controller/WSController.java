package com.example.gticsejercicioclase7.controller;

import com.example.gticsejercicioclase7.entity.Characters;
import com.example.gticsejercicioclase7.repository.CharactersRepository;
import com.example.gticsejercicioclase7.repository.RolesRepository;
import com.example.gticsejercicioclase7.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ws/personaje")
public class WSController {

    @Autowired
    CharactersRepository charactersRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    UsersRepository usersRepository;



    @GetMapping("/list")
    public ResponseEntity<HashMap<String, Object>> listarPersonajes(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "id") String sort_attr,
            @RequestParam(defaultValue = "asc") String sort_type,
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(defaultValue = "name") String search_attr,
            @RequestParam(defaultValue = "") String search_text) {

        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (limit > 20) {
            respuesta.put("error", "Límite inválido, debe ser menor a 20.");
            respuesta.put("date", LocalDateTime.now());
            return ResponseEntity.badRequest().body(respuesta);
        }

        //Para ordenar la lista
        Sort sort = Sort.by(Sort.Direction.fromString(sort_type), sort_attr);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Characters> pagePersonajes = charactersRepository.findAll(pageable);

        //Body
        respuesta.put("content", pagePersonajes);
        respuesta.put("pageable", pageable);
        respuesta.put("last", pagePersonajes.isLast());
        respuesta.put("totalElements", pagePersonajes.getTotalElements());
        respuesta.put("totalPages", pagePersonajes.getTotalPages());
        respuesta.put("size", pagePersonajes.getSize());
        respuesta.put("sort", pagePersonajes.getSort());
        respuesta.put("first", pagePersonajes.isFirst());
        respuesta.put("numberOfElements", pagePersonajes.getNumberOfElements());

        return ResponseEntity.ok(respuesta);
    }


    //OBTENER
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<HashMap<String, Object>> buscarPersonaje(@PathVariable("id") String idStr) {



        try {
            int id = Integer.parseInt(idStr);
            Optional<Characters> byId = charactersRepository.findById(id);

            HashMap<String, Object> respuesta = new HashMap<>();

            if (byId.isPresent()) {

                respuesta.put("personaje", byId.get());
            } else {
                respuesta.put("error", "ID Personaje NO encontrado.");
                respuesta.put("date", LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
            return ResponseEntity.ok(respuesta);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // GUARDAR
    @PostMapping(value = "/save")
    public ResponseEntity<HashMap<String, Object>> guardarPersonaje(
            @RequestBody Characters characters) {

        HashMap<String, Object> responseJson = new HashMap<>();

        try {


            // Caso crear
            if(characters.getId() == null){

                charactersRepository.save(characters);
                responseJson.put("msg", "Personaje creado exitosamente");
                responseJson.put("personaje", characters);

            }//Caso actualizar
            else{
                charactersRepository.save(characters);
                responseJson.put("msg", "Personaje actualizado exitosamente");
                responseJson.put("personaje", characters);

            }

            return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
        } catch (NumberFormatException e) {
            responseJson.put("error", "Error en la validación de datos");
            responseJson.put("date", LocalDateTime.now());
            return ResponseEntity.badRequest().body(null);
        }


    }


    // /Product?id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> borrar(@PathVariable("id") String idStr){

        try{
            int id = Integer.parseInt(idStr);

            HashMap<String, Object> rpta = new HashMap<>();

            Optional<Characters> byId = charactersRepository.findById(id);
            if(byId.isPresent()){
                charactersRepository.deleteById(id);
                rpta.put("result","ok");
            }else {
                rpta.put("error", "ID Personaje NO encontrado.");
                rpta.put("date", LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rpta);
            }

            return ResponseEntity.ok(rpta);
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(null);
        }
    }



    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, Object>> gestionException(HttpServletRequest request) {
        HashMap<String, Object> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("error", "Error en la validación de datos");
            responseMap.put("date", LocalDateTime.now());
        }
        return ResponseEntity.badRequest().body(responseMap);
    }



}
