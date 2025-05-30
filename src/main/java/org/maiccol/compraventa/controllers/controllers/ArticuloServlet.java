package org.maiccol.compraventa.controllers.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import org.maiccol.compraventa.controllers.models.Articulo;
import org.maiccol.compraventa.controllers.models.Categoria;
import org.maiccol.compraventa.controllers.services.ArticuloService;
import org.maiccol.compraventa.controllers.services.CategoriaService;
import org.maiccol.compraventa.controllers.services.ArticuloServiceJdbcImplement;
import org.maiccol.compraventa.controllers.services.CategoriaServiceJdbcImplement;

@WebServlet("/articulo")
public class ArticuloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Connection conn = (Connection) req.getAttribute("conn");
        if (conn == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No hay conexión a la base de datos");
            return;
        }

        ArticuloService articuloService = new ArticuloServiceJdbcImplement(conn);
        CategoriaService categoriaService = new CategoriaServiceJdbcImplement(conn);

        try {
            List<Articulo> articulos = articuloService.listar();
            List<Categoria> categorias = categoriaService.listar();

            req.setAttribute("articulos", articulos);
            req.setAttribute("categorias", categorias);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/articulo.jsp");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar artículos.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Connection conn = (Connection) req.getAttribute("conn");
        if (conn == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No hay conexión a la base de datos");
            return;
        }

        ArticuloService articuloService = new ArticuloServiceJdbcImplement(conn);

        try {
            Long idArticulo = Long.parseLong(req.getParameter("idArticulo"));
            Long idCategoria = Long.parseLong(req.getParameter("idCategoria"));
            String codigo = req.getParameter("codigo");
            int stock = Integer.parseInt(req.getParameter("stock"));
            String descripcion = req.getParameter("descripcion");
            String imagen = req.getParameter("imagen");
            int condicion = Integer.parseInt(req.getParameter("condicion"));

            Articulo articulo = new Articulo(idArticulo, idCategoria, codigo, stock, descripcion, imagen, condicion);
            articuloService.guardar(articulo);

            resp.sendRedirect(req.getContextPath() + "/articulo");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar el artículo.");
        }
    }
}
