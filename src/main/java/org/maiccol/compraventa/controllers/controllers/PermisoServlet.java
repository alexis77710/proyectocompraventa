package org.maiccol.compraventa.controllers.controllers;

import org.maiccol.compraventa.controllers.models.Permiso;
import org.maiccol.compraventa.controllers.services.PermisoService;
import org.maiccol.compraventa.controllers.services.PermisoServiceJdbcImplement;
import org.maiccol.compraventa.controllers.util.ConexionBDD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/permiso")
public class PermisoServlet extends HttpServlet {

    private PermisoService service;
    private ConexionBDD ConexionBaseDatos;

    @Override
    public void init() throws ServletException {
        Connection conn = null;
        try {
            conn = ConexionBaseDatos.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.service = new PermisoServiceJdbcImplement(conn);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("editar".equals(accion)) {
            Long id = Long.parseLong(req.getParameter("id"));
            Optional<Permiso> permisoOpt = null;
            try {
                permisoOpt = service.porId(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            permisoOpt.ifPresent(permiso -> req.setAttribute("permiso", permiso));
        } else if ("eliminar".equals(accion)) {
            Long id = Long.parseLong(req.getParameter("id"));
            service.eliminar(id);
        }

        List<Permiso> permisos = service.listar();
        req.setAttribute("permisos", permisos);
        getServletContext().getRequestDispatcher("/permiso.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("idPermiso");
        String nombre = req.getParameter("nombre");

        Permiso permiso = new Permiso();
        permiso.setNombre(nombre);

        if (idParam != null && !idParam.isEmpty()) {
            permiso.setIdPermiso(Long.parseLong(idParam));
        }

        service.guardar(permiso);
        resp.sendRedirect(req.getContextPath() + "/permiso");
    }
}
