package org.maiccol.compraventa.controllers.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maiccol.compraventa.controllers.models.Categoria;
import org.maiccol.compraventa.controllers.services.CategoriaService;
import org.maiccol.compraventa.controllers.services.CategoriaServiceJdbcImplement;
import org.maiccol.compraventa.controllers.services.LoginService;
import org.maiccol.compraventa.controllers.services.LoginServiceSessionImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion=req.getParameter("accion");
        Connection conn = (Connection) req.getAttribute("conn");
        CategoriaService servicios = new CategoriaServiceJdbcImplement(conn);
        //List<Categoria> categorias = servicios.listar();
        if ("listar".equalsIgnoreCase(accion)){
            List<Categoria> categorias= servicios.listar();
            enviarComoJson(resp, categorias);
            return;
        }

        List<Categoria> categorias =servicios.listar();

        LoginService auth= new LoginServiceSessionImplement();
        Optional<String>usernameOptional=auth.getUserName(req);

        req.setAttribute("categorias", categorias);
        req.setAttribute("username",usernameOptional);
        getServletContext().getRequestDispatcher("/categoria.jsp").forward(req, resp);


    }

    private void enviarComoJson(HttpServletResponse response, List<Categoria> categorias)throws IOException{
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //Armar JSON
        JsonObject responseJson=new JsonObject();
        responseJson.add("data", new Gson().toJsonTree(categorias));
        //
        response.getWriter().write(responseJson.toString());
    }
}
