package org.maiccol.compraventa.controllers.services;

import org.maiccol.compraventa.controllers.models.Articulo;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ArticuloService {
    List<Articulo> listar();
    Optional<Articulo> porId(Long id) throws SQLException;

    void guardar(Articulo articulo);
}
