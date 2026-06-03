package com.ufps.tareas.controller;

import com.ufps.tareas.model.Tarea;
import com.ufps.tareas.service.TareaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tareas")
public class TareaWebController {

    private final TareaService service;

    public TareaWebController(TareaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tareas", service.listar());
        model.addAttribute("tarea", new Tarea());
        return "tareas";
    }

    @GetMapping("/nueva")
    public String nuevaForm(Model model) {
        model.addAttribute("tarea", new Tarea());
        return "nueva-tarea";
    }

    @PostMapping("/nueva")
    public String crearTarea(@ModelAttribute Tarea tarea) {
        service.crear(tarea);
        return "redirect:/tareas";
    }

    @PostMapping("/{id}/completar")
    public String completar(@PathVariable Long id) {
        service.completar(id);
        return "redirect:/tareas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/tareas";
    }
}
