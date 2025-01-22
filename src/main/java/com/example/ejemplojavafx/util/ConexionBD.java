package com.example.ejemplojavafx.util;

import java.sql.*;

public class ConexionBD {

    private Connection conexion;

    public ConexionBD(){

        try {
            String host="localhost";
            String baseDatos="aeropuertos";
            String usuario="root";
            String password="";

            String cadenaConexion="jdbc:mysql://"+host+"/"+baseDatos;
            conexion= DriverManager.getConnection(cadenaConexion,usuario,password);
            conexion.setAutoCommit(true); //Cuando hagamos un insert/delete/update se haga automaticamente sin confirmar
        } catch (SQLException e) {
            System.err.println("Error al conectar o consultar la base de datos: " + e.getMessage());
            throw new RuntimeException(e); //Propagar excepcion
        }
    }
    //Lanzamos todas las excepciones hacia arriba
    public ResultSet ejecutarConsulta(String SQL) throws SQLException {
        Statement statement =this.conexion.createStatement();
        return statement.executeQuery(SQL);

    }
    public int ejecutarInstruccion(String SQL) throws SQLException {
        Statement statement =this.conexion.createStatement();
        return statement.executeUpdate(SQL);
    }
    //Util para recuperar el último ID insertado,actualizado o mostrado...
    public int ultimoID() throws SQLException {

        ResultSet rs = this.ejecutarConsulta("SELECT last_insert_id() as last_id");
        rs.next();
        return rs.getInt("last_id");
    }

    public void cerrarConexion() throws SQLException {
        this.conexion.close();
    }
}
