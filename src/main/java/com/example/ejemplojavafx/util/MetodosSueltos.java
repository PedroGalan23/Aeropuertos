package com.example.ejemplojavafx.util;

public class MetodosSueltos {

    /**
     * Valida si una cadena es un numero entero
     *
     * @param texto String que contiene el valor a validar
     * @return True = es un numero entero
     */
    public static boolean validaNumeroEntero_Exp(String texto) {
        return texto.matches("^-?[0-9]+$");
    }

    /**
     * Valida si una cadena es un numero real (positivo o negativo)
     *
     * @param texto String que contiene el valor a validar
     * @return True = es un numero real
     */
    public static boolean validaNumeroReal_Exp(String texto) {
        return texto.matches("^-?[0-9]+([\\.,][0-9]+)?$");
    }

}