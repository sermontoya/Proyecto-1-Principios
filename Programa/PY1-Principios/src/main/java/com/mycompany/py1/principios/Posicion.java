/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.py1.principios;

/**
 *
 * @author sermo
 */
public class Posicion {
    private String valor;
    private String zona; // Si es Sistema Operativo o Usuario
    private String proposito; // Que hay en la posición de memoria (Si es del BCP, lista de trabajo, etc.)

    public Posicion(String valor, String zona, String proposito) {
        this.valor = valor;
        this.zona = zona;
        this.proposito = proposito;
    }

    public String getValor() {
        return valor;
    }

    public String getZona() {
        return zona;
    }

    public String getProposito() {
        return proposito;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public void setZona(String zona) {
        this.zona = zona;
    }
    public void setProposito(String proposito) {
        this.proposito = proposito;
    }
}
