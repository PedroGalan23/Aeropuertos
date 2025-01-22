package com.example.ejemplojavafx.modelo;

import com.example.ejemplojavafx.util.ConexionBD;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Aeropuerto {
    private int id;
    private String nombre;
    private Direccion direccion;
    private int anioInauguracion;
    private int capacidad;
    private ArrayList<Avion> aviones;

    public Aeropuerto(){}

    public Aeropuerto(String nombre, String pais, String calle, int numero, String ciudad, int anioInauguracion, int capacidad) {
        this.nombre = nombre;
        this.direccion = new Direccion(pais, ciudad, calle, numero);
        this.anioInauguracion = anioInauguracion;
        this.capacidad = capacidad;
        this.aviones = new ArrayList();
    }

    public Aeropuerto(int id, String nombre, Direccion direccion, int anioInauguracion, int capacidad) {
        this.id=id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.anioInauguracion = anioInauguracion;
        this.capacidad = capacidad;
    }

    public Aeropuerto(String nombre, Direccion direccion, int anioInauguracion, int capacidad) {
        this.nombre=nombre;
        this.direccion=direccion;
        this.anioInauguracion=anioInauguracion;
        this.capacidad=capacidad;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public String getPais(){
        return this.direccion.getPais();
    }

    public String getCiudad(){
        return this.direccion.getCiudad();
    }

    public String getCalle(){
        return this.direccion.getCalle();
    }

    public int getNumero(){
        return this.direccion.getNumero();
    }


    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public int getAnioInauguracion() {
        return anioInauguracion;
    }

    public void setAnioInauguracion(int anioInauguracion) {
        this.anioInauguracion = anioInauguracion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public ArrayList<Avion> getAviones() {
        return aviones;
    }

    public void setAviones(ArrayList<Avion> aviones) {
        this.aviones = aviones;
    }
    public abstract ObservableList getAeropuertos(String filtro) throws SQLException;

    @Override
    public String toString() {
        return "Aeropuerto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion=" + direccion +
                ", anioInauguracion=" + anioInauguracion +
                ", capacidad=" + capacidad +
                ", aviones=" + aviones +
                '}';
    }

    public boolean insertar() throws SQLException {
        ConexionBD conexion=new ConexionBD();

        // Formo el SQL
        String SQL = "";
        SQL += "INSERT INTO aeropuertos VALUES (null, ";
        SQL += "'" + this.nombre + "', " + this.anioInauguracion + ", ";
        SQL += this.capacidad + ", " + this.direccion.getId() + ")";

        int filas=conexion.ejecutarInstruccion(SQL);
        int ultimoID=conexion.ultimoID();
        this.id=ultimoID;
        conexion.cerrarConexion();

        if(filas > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean actualizar() throws SQLException {
        ConexionBD conexion=new ConexionBD();

        // Formo el SQL
        String SQL = "";
        SQL += "UPDATE aeropuertos ";
        SQL += "SET nombre = '"+this.nombre+"', anio_inauguracion = " + this.anioInauguracion + ", ";
        SQL += "capacidad = " + this.capacidad + ", id_direccion = " + this.direccion.getId() + " ";
        SQL += "WHERE id = " + this.id;

        int filas=conexion.ejecutarInstruccion(SQL);


        conexion.cerrarConexion();

        if(filas > 0){
            return true;
        }else{
            return false;
        }
    }
    public boolean borrarAeropuerto() throws SQLException {
        ConexionBD conexion=new ConexionBD();

        String SQL="";
        SQL +="DELETE FROM aeropuertos WHERE id= "+this.id;

        int filas=conexion.ejecutarInstruccion(SQL);
        conexion.cerrarConexion();
        return filas >0;

    }

}