package com.example.gticsejercicioclase7.controller;

import com.example.gticsejercicioclase7.entity.Characters;
import com.example.gticsejercicioclase7.repository.CharactersRepository;
import com.example.gticsejercicioclase7.repository.RolesRepository;
import com.example.gticsejercicioclase7.repository.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonajeController {
    @Autowired
    CharactersRepository charactersRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    UsersRepository usersRepository;


    @GetMapping(value = {"/personaje/list"})
    public String principal(Model model,@RequestParam(name="palabra",required = false) String palabra){
        List<Characters> listaPersonajes = new ArrayList<>();
        if(palabra==null){
            listaPersonajes=charactersRepository.findAll();
        }else{
            listaPersonajes=charactersRepository.findByNameIgnoreCaseContaining(palabra);
        }
        model.addAttribute("listaPersonajes",listaPersonajes);
        return "personajes/list";
    }


    @GetMapping("/personaje/new")
    public String nuevoPersonaje(Model model, @ModelAttribute("personaje") Characters personaje) {

        return "personajes/frm";
    }

    @GetMapping("/personaje/edit")
    public String editarPersonaje(Model model, @RequestParam("id") int id, @ModelAttribute("personaje") Characters personaje) {

        Optional<Characters> optional = charactersRepository.findById(id);

        if (optional.isPresent()) {
            personaje = optional.get();
            model.addAttribute("personaje", optional.get());
            return "personajes/frm";
        } else {
            return "redirect:/personaje/list";
        }
    }

    @PostMapping("/personaje/save")
    public String guardarPersonaje(@ModelAttribute("personaje") @Valid Characters personaje, BindingResult bindingResult,
                              RedirectAttributes attr,
                              Model model) {

        if(bindingResult.hasErrors()){

            return "personajes/frm";
        }else {

            if (personaje.getId() == 0) {
                attr.addFlashAttribute("msg", "Personaje creado exitosamente");

                charactersRepository.save(personaje);

                return "redirect:/personaje/list";
            } else {


                charactersRepository.save(personaje);
                attr.addFlashAttribute("msg", "Personaje actualizado exitosamente");
                return "redirect:/personaje/list";
            }
        }
    }

    @GetMapping(value = {"/personaje/delete"})
    public String borrarPersonaje(@RequestParam("id") Integer id){
        Optional<Characters> opt = charactersRepository.findById(id);
        if(opt.isPresent()){
            charactersRepository.deleteById(id);
        }
        return "redirect:/personaje/list";
    }

    @PostMapping(value = "/personaje/buscar")
    public String buscarPersonaje(@RequestParam("palabra") String palabra,Model model){

        return "redirect:/personaje/list?palabra="+palabra;
    }

}
