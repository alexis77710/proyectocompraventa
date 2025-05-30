package org.maiccol.compraventa.controllers.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.maiccol.compraventa.controllers.services.ServiceJdcException;
import org.maiccol.compraventa.controllers.util.ConexionBDD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ArticuloFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try(Connection conn = ConexionBDD.getConnection()){
            if(conn .getAutoCommit()){
                conn.setAutoCommit(false);
            }
            try {
                servletRequest.setAttribute("conn", conn);
                filterChain.doFilter(servletRequest,servletResponse);
                conn.commit();
            }catch(SQLException | ServiceJdcException e){
                conn.rollback();
                ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
