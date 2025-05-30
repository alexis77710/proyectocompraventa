package org.maiccol.compraventa.controllers.services;

import org.maiccol.compraventa.controllers.models.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> listar();
    Optional<Categoria> porId(Long id);
    void guardar(Categoria categoria); // <-- Asegúrate de incluir esto
}