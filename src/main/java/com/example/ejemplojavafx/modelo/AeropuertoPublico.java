package com.example.ejemplojavafx.modelo;

import com.example.ejemplojavafx.util.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AeropuertoPublico extends Aeropuerto {
    private int id_aeropuerto;
    private Double finaniacion;
    private int numero_trab_discapacitados;

    public AeropuertoPublico() {
    }

    public AeropuertoPublico(double financiacion, int numTrabajadoresDiscapacitados, String nombre, Direccion direccion, int anioInauguracion, int capacidad) {
        super(nombre, direccion, anioInauguracion, capacidad);
        this.finaniacion = financiacion;
        this.numero_trab_discapacitados = numTrabajadoresDiscapacitados;
    }

    public AeropuertoPublico(int id, String nombre, Direccion direccion, int anioInauguracion, int capacidad, Double finaniacion, int numero_trab_discapacitados) {
        super(id, nombre, direccion, anioInauguracion, capacidad);
        this.finaniacion = finaniacion;
        this.numero_trab_discapacitados = numero_trab_discapacitados;
    }


    public int getId_aeropuerto() {
        return id_aeropuerto;
    }

    public void setId_aeropuerto(int id_aeropuerto) {
        this.id_aeropuerto = id_aeropuerto;
    }

    public Double getFinaniacion() {
        return finaniacion;
    }

    public void setFinaniacion(Double finaniacion) {
        this.finaniacion = finaniacion;
    }

    public int getNumero_trab_discapacitados() {
        return numero_trab_discapacitados;
    }

    public void setNumero_trab_discapacitados(int numero_trab_discapacitados) {
        this.numero_trab_discapacitados = numero_trab_discapacitados;
    }

    @Override
    public ObservableList<AeropuertoPublico> getAeropuertos(String filtro) throws SQLException {
        ObservableList<AeropuertoPublico> aeropuertos= FXCollections.observableArrayList();
        ConexionBD conexion=new ConexionBD();

        String SQL="";
        SQL +="SELECT a.id,a.nombre, a.anio_inauguracion, a.capacidad,d.id as id_direccion ,d.pais, d.ciudad, d.calle, d.numero, ";
        SQL +="ap.financiacion, ap.num_trab_discapacitados ";
        SQL +="FROM aeropuertos a,direcciones d, aeropuertos_publicos ap ";
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
            int idDireccion=rs.getInt("id_direccion");
            String pais = rs.getString("pais");
            String ciudad = rs.getString("ciudad");
            String calle = rs.getString("calle");
            int numero = rs.getInt("numero");
            Double financiacion = rs.getDouble("financiacion");
            int num_trab_discapacitados = rs.getInt("num_trab_discapacitados");

            Direccion direccion= new Direccion(pais,ciudad,calle,numero);
            AeropuertoPublico a =new AeropuertoPublico(id,nombre,direccion,anio_inauguracion,capacidad,financiacion,num_trab_discapacitados);
            a.getDireccion().setId(idDireccion);
            aeropuertos.add(a);
        }
        rs.close();
        conexion.cerrarConexion();
        return aeropuertos;
    }

    @Override
    public boolean insertar() throws SQLException {
        boolean exito=super.insertar();
        System.out.println("Buenas si para pedir");
        if(exito){
            System.out.println("Joder caca");

            // Abro la conexion
            ConexionBD conexion = new ConexionBD();

            // Formo el SQL
            //Utilizamos el super para coger el id del anterior aeropuerto insertado
            String SQL = "";
            SQL += "INSERT INTO aeropuertos_publicos VALUES (" + super.getId() + ", ";
            SQL += this.finaniacion + ", " + this.numero_trab_discapacitados + ")";

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
        }else{
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
            SQL += "UPDATE aeropuertos_publicos SET financiacion = " + this.finaniacion + ", ";
            SQL += "num_trab_discapacitados = " + this.numero_trab_discapacitados + " ";
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