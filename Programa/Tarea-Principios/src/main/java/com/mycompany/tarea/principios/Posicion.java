/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea.principios;

/**
 *
 * @author sermo
 */
public class Posicion {
    private String valor;
    private String zona;

    public Posicion(String valor, String zona) {
        this.valor = valor;
        this.zona = zona;
    }

    public String getValor() {
        return valor;
    }

    public String getZona() {
        return zona;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
