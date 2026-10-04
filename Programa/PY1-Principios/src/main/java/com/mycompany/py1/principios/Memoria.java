/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.py1.principios;

import java.util.ArrayList;

/**
 *
 * @author sermo
 */
public class Memoria {
    int size;
    int inicioMemoriaUsuario;
    private ArrayList<Posicion> memoria;
    String error = null;

    public Memoria(int size) {
        this.size = size;
        this.memoria = new ArrayList<>();
        this.inicioMemoriaUsuario = size / 4;
        asignarMemoria();
    }

    // Asigna la memoria del sistema operativo y la memoria de usuario
    private void asignarMemoria() {
        for (int i = 0; i < inicioMemoriaUsuario; i++) {
            memoria.add(new Posicion("0", "Sistema Operativo"));
        }
        for (int i = inicioMemoriaUsuario; i < size; i++) {
            memoria.add(new Posicion("0", "Usuario"));
        }
    }
    // Asigna las instrucciones del programa a la memoria de usuario
    public void asignarInstrucciones(ArrayList<String> instrucciones) {
        error = null;
        // limpiar el espacio de usuario antes de cargar el nuevo programa
        for (int i = inicioMemoriaUsuario; i < size; i++) {
            memoria.get(i).setValor("0");
        }
        // si el programa es más grande que el espacio de usuario disponible, se genera un error
        if (instrucciones.size() > (size - inicioMemoriaUsuario)) {
            error = "El programa (" + instrucciones.size() + " instrucciones) no cabe en el "
                    + "espacio de Usuario disponible (" + (size - inicioMemoriaUsuario) + " posiciones).";
        } else {
            for (int i = 0; i < instrucciones.size(); i++) {
                int posMemoria = inicioMemoriaUsuario + i;
                String instruccion = instrucciones.get(i);
                this.memoria.get(posMemoria).setValor(instruccion);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int getInicioMemoriaUsuario() {
        return inicioMemoriaUsuario;
    }

    public ArrayList<Posicion> getMemoria() {
        return memoria;
    }

    public String leerMemoria(int direccion) {
        if (direccion < 0 || direccion >= size) {
            return null; // Dirección inválida
        }
        return memoria.get(direccion).getValor();
    }

    public String getError() {
        return error;
    }
}