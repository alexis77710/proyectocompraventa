package org.maiccol.compraventa.controllers.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
    private static String url="jdbc:mysql://localhost:3307/trabajoenclase?serverTimezone=UTC";
    private static String username="root";
    private static String password="";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,username,password);
    }
}
