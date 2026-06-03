package com.ufps.tareas.service;

import com.ufps.tareas.model.Tarea;
import com.ufps.tareas.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private final TareaRepository repo;

    public TareaService(TareaRepository repo) {
        this.repo = repo;
    }

    public List<Tarea> listar() {
        return repo.findAll();
    }

    public Tarea crear(Tarea tarea) {
        return repo.save(tarea);
    }

    public Optional<Tarea> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Optional<Tarea> completar(Long id) {
        return repo.findById(id).map(t -> {
            t.setCompletada(true);
            return repo.save(t);
        });
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
