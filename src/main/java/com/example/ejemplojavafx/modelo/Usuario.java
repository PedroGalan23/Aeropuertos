package com.example.ejemplojavafx.modelo;

import com.example.ejemplojavafx.util.ConexionBD;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Usuario {
    private String usuario;
    private String password;

    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //Metodo que llama a la base de datos
    public boolean login() throws SQLException {
        //TODO Codigo muy basico, se necesita controlar la inyeccion con un PreparedStatement
        ConexionBD conexion= new ConexionBD();
        String SQL="";
        SQL +="SELECT * FROM usuarios ";
        SQL +="WHERE lower(usuario) = '"+usuario.toLowerCase()+"' and password= '"+password+"'";

        ResultSet rs= conexion.ejecutarConsulta(SQL);

        boolean hayUsuario= rs.next();
        conexion.cerrarConexion();
        return hayUsuario;

    }

}
