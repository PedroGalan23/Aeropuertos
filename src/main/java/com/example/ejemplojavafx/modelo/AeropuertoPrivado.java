package com.example.ejemplojavafx.modelo;

import com.example.ejemplojavafx.util.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AeropuertoPrivado extends Aeropuerto {
    private int id_aeropuerto;
    private int numero_socios;

    public AeropuertoPrivado() {
    }

    public AeropuertoPrivado(int id, String nombre, Direccion direccion, int anioInauguracion, int capacidad, int numero_socios) {
        super(id, nombre, direccion, anioInauguracion, capacidad);
        this.numero_socios = numero_socios;
    }
    //Aeropuerto sin id para crar uno en la bd
    public AeropuertoPrivado(int numSocios, String nombre, String pais, String calle, int numero, String ciudad, int anioInauguracion, int capacidad) {
        super(nombre, pais, calle, numero, ciudad, anioInauguracion, capacidad);
        this.numero_socios = numSocios;
    }
    public AeropuertoPrivado(int numSocios, String nombre, Direccion direccion, int anioInauguracion, int capacidad) {
        super(nombre, direccion, anioInauguracion, capacidad);
        this.numero_socios = numSocios;
    }

    public int getId_aeropuerto() {
        return id_aeropuerto;
    }

    public void setId_aeropuerto(int id_aeropuerto) {
        this.id_aeropuerto = id_aeropuerto;
    }

    public int getNumero_socios() {
        return numero_socios;
    }

    public void setNumero_socios(int numero_socios) {
        this.numero_socios = numero_socios;
    }

    @Override
    public ObservableList<AeropuertoPrivado> getAeropuertos(String filtro) throws SQLException {
        ObservableList<AeropuertoPrivado> aeropuertos= FXCollections.observableArrayList();

        ConexionBD conexion=new ConexionBD();

        String SQL="";
        SQL +="SELECT a.id,a.nombre, a.anio_inauguracion, a.capacidad,d.id as id_direccion ,d.pais, d.ciudad, d.calle, d.numero, ";
        SQL +="ap.numero_socios ";
        SQL +="FROM aeropuertos a,direcciones d, aeropuertos_privados ap ";
        SQL +="WHERE a.id_direccion=d.id and ap.id_aeropuerto=a.id ";
        if(filtro!=null && !filtro.isEmpty()){
            SQL +=" and lower(trim(a.nombre)) LIKE '%"+filtro.toLowerCase().trim()+"%'";
        }

        ResultSet rs = conexion.ejecutarConsulta(SQL);

        while(rs.next()){
            int id=rs.getInt("id");
            String nombre = rs.getString("nombre");
            int anio_inauguracion = rs.getInt("anio_inauguracion");
            int capacidad = rs.getInt("capacidad");
            int idDireccion = rs.getInt("id_direccion");
            String pais = rs.getString("pais");
            String ciudad = rs.getString("ciudad");
            String calle = rs.getString("calle");
            int numero = rs.getInt("numero");
            int numero_socios=rs.getInt("numero_socios");


            Direccion direccion= new Direccion(pais,ciudad,calle,numero);

            AeropuertoPrivado p =new AeropuertoPrivado(id,nombre,direccion,anio_inauguracion,capacidad,numero_socios);
            p.getDireccion().setId(idDireccion);
            aeropuertos.add(p);
        }
        rs.close();
        conexion.cerrarConexion();

        return aeropuertos;
    }

    @Override
    public String toString() {
        return "AeropuertoPrivado{" +
                "numero_socios=" + numero_socios +
                '}';
    }

    @Override
    public boolean insertar() throws SQLException {

        // Llamo al padre para insertar el aeropuerto
        boolean exito = super.insertar();

        // Si se inserto el aeropuerto
        if (exito) {

            // Abro la conexion
            ConexionBD conexion = new ConexionBD();

            // Formo el SQL
            String SQL = "";
            SQL += "INSERT INTO aeropuertos_privados VALUES (" + super.getId() + ", ";
            SQL += this.numero_socios + ")";

            // Ejecuto la instruccion
            int filas = conexion.ejecutarInstruccion(SQL);

            // cierro la conexion
            conexion.cerrarConexion();

            // Indico si se ha insertado o no
            if (filas > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public boolean actualizar() throws SQLException {

        // Llamo al padre para insertar el aeropuerto
        boolean exito = super.actualizar();

        // Si se inserto el aeropuerto
        if (exito) {

            // Abro la conexion
            ConexionBD conexion = new ConexionBD();

            // Formo el SQL
            String SQL = "";
            SQL += "UPDATE aeropuertos_privados SET numero_socios = " + this.numero_socios + " ";
            SQL += "WHERE id_aeropuerto = " + super.getId();

            // Ejecuto la instruccion
            int filas = conexion.ejecutarInstruccion(SQL);

            // cierro la conexion
            conexion.cerrarConexion();

            // Indico si se ha insertado o no
            if (filas > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

}
