package com.example.ejemplojavafx.modelo;

import com.example.ejemplojavafx.util.ConexionBD;

import java.sql.SQLException;

public class Direccion {
    private int id;
    private String pais;
    private String ciudad;
    private String calle;
    private int numero;

    public Direccion(String pais, String ciudad, String calle, int numero) {
        this.pais = pais;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean insertar() throws SQLException {

        ConexionBD conexion = new ConexionBD();

        String SQL = "";
        SQL += "INSERT INTO direcciones VALUES(null, ";
        SQL += "'" + this.pais + "', '" + this.ciudad + "', '" + this.calle + "', " + this.numero + ")";

        int filas = conexion.ejecutarInstruccion(SQL);

        this.id = conexion.ultimoID();

        conexion.cerrarConexion();

        return filas > 0;

    }

    //Ejemplo de buen comentario de código:
    /**
     * Actualizar la direccion en la BD
     *
     * @return indica si se ha actualizado o no
     * @throws SQLException
     */

    public boolean actualizar() throws SQLException {

        ConexionBD conexion = new ConexionBD();

        String SQL = "";
        SQL += "UPDATE direcciones SET pais = '" + this.pais + "', ciudad = '" + this.ciudad + "', ";
        SQL += "calle = '" + this.calle + "', numero = " + this.numero + " ";
        SQL += "WHERE id = " + this.id;

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }
    public boolean borrar() throws SQLException {

        ConexionBD conexion = new ConexionBD();

        String SQL = "";
        SQL += "DELETE FROM direcciones WHERE id= "+this.id;

        int filas = conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        return filas > 0;

    }
}