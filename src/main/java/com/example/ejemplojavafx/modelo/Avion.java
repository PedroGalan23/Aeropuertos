package com.example.ejemplojavafx.modelo;

public class Avion {
    private int id;
    private String modelo;
    private int numero_asientos;
    private int velocidad_maxima;
    private boolean activado;
    private int id_aeropuerto;

    public Avion(int id, String modelo, int numero_asientos, int velocidad_maxima) {
        this.id = id;
        this.modelo = modelo;
        this.numero_asientos = numero_asientos;
        this.velocidad_maxima = velocidad_maxima;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getNumero_asientos() {
        return numero_asientos;
    }

    public void setNumero_asientos(int numero_asientos) {
        this.numero_asientos = numero_asientos;
    }

    public int getVelocidad_maxima() {
        return velocidad_maxima;
    }

    public void setVelocidad_maxima(int velocidad_maxima) {
        this.velocidad_maxima = velocidad_maxima;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public int getId_aeropuerto() {
        return id_aeropuerto;
    }

    public void setId_aeropuerto(int id_aeropuerto) {
        this.id_aeropuerto = id_aeropuerto;
    }

    @Override
    public String toString() {
        return "Avion{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", numero_asientos=" + numero_asientos +
                ", velocidad_maxima=" + velocidad_maxima +
                ", activado=" + activado +
                ", id_aeropuerto=" + id_aeropuerto +
                '}';
    }
}