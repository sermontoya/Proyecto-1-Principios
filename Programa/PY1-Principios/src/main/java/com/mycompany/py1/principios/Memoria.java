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
            switch (i) {
                case 0:
                    memoria.add(new Posicion("0", "Sistema Operativo", "Puntero Lista de Trabajo"));
                    break;
                case 1:
                    memoria.add(new Posicion("0", "Sistema Operativo", "Fin Lista de Trabajo"));
                    break;
                case 2:
                    memoria.add(new Posicion("0", "Sistema Operativo", "Puntero Lista de Procesos"));
                    break;
                case 3:
                    memoria.add(new Posicion("0", "Sistema Operativo", "Fin Lista de Procesos"));
                    break;
                default:
                    memoria.add(new Posicion("0", "Sistema Operativo", "Sin propósito"));
                    break;
            }
        }
        for (int i = inicioMemoriaUsuario; i < size; i++) {
            memoria.add(new Posicion("0", "Usuario", "Sin propósito"));
        }
    }
    // Asigna las instrucciones del programa a la memoria de usuario
    public void asignarInstrucciones(ArrayList<String> instrucciones) {
        if (getTamanoKernelSinUso() < 22) {
            error = "No se puede cargar el programa, no hay suficiente espacio en el kernel.";
            return;
        }
        error = null;
        int ultimaPosicion = getUltimaPosicionInstruccion();

        // si el programa es más grande que el espacio de usuario disponible, se genera un error
        if (instrucciones.size() > (size - inicioMemoriaUsuario)) {
            error = "El programa (" + instrucciones.size() + " instrucciones) no cabe en el "
                    + "espacio de Usuario disponible (" + (size - inicioMemoriaUsuario) + " posiciones).";
        } else if (ultimaPosicion + instrucciones.size() > size) {
            error = "No hay suficiente espacio en la memoria de usuario para cargar el programa.";
        } else {
            for (int i = 0; i < instrucciones.size(); i++) {
                int posMemoria = ultimaPosicion + i;
                String instruccion = instrucciones.get(i);
                this.memoria.get(posMemoria).setValor(instruccion);
                this.memoria.get(posMemoria).setProposito("Instrucción del programa");
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

    public int getTamanoKernelSinUso() {
        int tamanoKernelSinUso = 0;
        for (int i = 0; i < inicioMemoriaUsuario; i++) {
            if (memoria.get(i).getProposito().equals("Sin propósito")) {
                tamanoKernelSinUso++;
            }
        }
        return tamanoKernelSinUso;
    }

    public int getUltimaPosicionInstruccion() {
        for (int i = size - 1; i >= inicioMemoriaUsuario; i--) {
            if (!memoria.get(i).getValor().equals("0")) {
                return i+1; // Devuelve la posición siguiente a la última instrucción encontrada
            }
        }
        return inicioMemoriaUsuario; // Si no hay instrucciones, devuelve la posición del inicio de memoria de usuario
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

    public void guardarValor(int direccion, String valor, String zona, String proposito) {
        memoria.get(direccion).setValor(valor);
        memoria.get(direccion).setZona(zona);
        memoria.get(direccion).setProposito(proposito);
    }
    
    private int getUltimaPosicionKernel() {
        for (int i = 0; i < size; i++) {
            if (memoria.get(i).getProposito().equals("Sin propósito")) {
                return i;
            }
        }
        return -1; // No hay posiciones libres
    }

    public void guardarBCP(BCP bcp) {
        int posicion = getUltimaPosicionKernel();
        if (posicion != -1) {
            for (int i = 0; i < 22; i++) {
                switch (i) {
                    case 0:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPid()), "Sistema Operativo", "ID BCP");
                        break;
                    case 1:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPc()), "Sistema Operativo", "PC");
                        break;
                    case 2:
                        this.guardarValor(posicion + i, bcp.getEstado(), "Sistema Operativo", "Estado");
                        break;
                    case 3:
                        this.guardarValor(posicion + i, bcp.getInstruccion(), "Sistema Operativo", "IR");
                        break;
                    case 4:
                        this.guardarValor(posicion + i, bcp.getAc(), "Sistema Operativo", "AC");
                        break;
                    case 5:
                        this.guardarValor(posicion + i, bcp.getAx(), "Sistema Operativo", "AX");
                        break;
                    case 6:
                        this.guardarValor(posicion + i, bcp.getBx(), "Sistema Operativo", "BX");
                        break;
                    case 7:
                        this.guardarValor(posicion + i, bcp.getCx(), "Sistema Operativo", "CX");
                        break;
                    case 8:
                        this.guardarValor(posicion + i, bcp.getDx(), "Sistema Operativo", "DX");
                        break;
                    case 9:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(0)), "Sistema Operativo", "Pila[0]");
                        break;
                    case 10:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(1)), "Sistema Operativo", "Pila[1]");
                        break;
                    case 11:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(2)), "Sistema Operativo", "Pila[2]");
                        break;
                    case 12:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(3)), "Sistema Operativo", "Pila[3]");
                        break;
                    case 13:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(4)), "Sistema Operativo", "Pila[4]");
                        break;
                    case 14:
                        if (bcp.getCpu() != null) {
                            this.guardarValor(posicion + i, String.valueOf(bcp.getCpu().getId()), "Sistema Operativo", "CPU");
                        } else {
                            this.guardarValor(posicion + i, "-1", "Sistema Operativo", "CPU");
                        }
                        break;
                    case 15:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getTiempoInicio()), "Sistema Operativo", "Tiempo Inicio");
                        break;
                    case 16:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getTiempoEjecucion()), "Sistema Operativo", "Tiempo Ejecución");
                        break;
                    case 17:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getArchivos()), "Sistema Operativo", "Archivos");
                        break;
                    case 18:
                        if (bcp.getSiguiente() != null) {
                            this.guardarValor(posicion + i, String.valueOf(bcp.getSiguiente().getPid()), "Sistema Operativo", "Siguiente");
                        } else {
                            this.guardarValor(posicion + i, "-1", "Sistema Operativo", "Siguiente");
                        }
                        break;
                    case 19:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getInicio()), "Sistema Operativo", "Inicio");
                        break;
                    case 20:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getTamano()), "Sistema Operativo", "Tamaño");
                        break;
                    case 21:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPrioridad()), "Sistema Operativo", "Prioridad");
                        break;
                }   
            }
        } else {
            error = "No hay espacio en la memoria del kernel para guardar el BCP.";
        }
    }

    public void actualizarBCP(BCP bcp) {
        int id = bcp.getPid();
        int posicion = -1;
        for(int i = 0; i < inicioMemoriaUsuario; i++) {
            if (memoria.get(i).getProposito().equals("ID BCP") && memoria.get(i).getValor().equals(String.valueOf(id))) {
                posicion = i;
                break;
            }
        }
        if (posicion >= 0) {
            for (int i = 0; i < 22; i++) {
                switch (i) {
                    case 0:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPid()), "Sistema Operativo", "ID BCP");
                        break;
                    case 1:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPc()), "Sistema Operativo", "PC");
                        break;
                    case 2:
                        this.guardarValor(posicion + i, bcp.getEstado(), "Sistema Operativo", "Estado");
                        break;
                    case 3:
                        this.guardarValor(posicion + i, bcp.getInstruccion(), "Sistema Operativo", "IR");
                        break;
                    case 4:
                        this.guardarValor(posicion + i, bcp.getAc(), "Sistema Operativo", "AC");
                        break;
                    case 5:
                        this.guardarValor(posicion + i, bcp.getAx(), "Sistema Operativo", "AX");
                        break;
                    case 6:
                        this.guardarValor(posicion + i, bcp.getBx(), "Sistema Operativo", "BX");
                        break;
                    case 7:
                        this.guardarValor(posicion + i, bcp.getCx(), "Sistema Operativo", "CX");
                        break;
                    case 8:
                        this.guardarValor(posicion + i, bcp.getDx(), "Sistema Operativo", "DX");
                        break;
                    case 9:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(0)), "Sistema Operativo", "Pila[0]");
                        break;
                    case 10:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(1)), "Sistema Operativo", "Pila[1]");
                        break;
                    case 11:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(2)), "Sistema Operativo", "Pila[2]");
                        break;
                    case 12:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(3)), "Sistema Operativo", "Pila[3]");
                        break;
                    case 13:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPila().indexOf(4)), "Sistema Operativo", "Pila[4]");
                        break;
                    case 14:
                        if (bcp.getCpu() != null) {
                            this.guardarValor(posicion + i, String.valueOf(bcp.getCpu().getId()), "Sistema Operativo", "CPU");
                        } else {
                            this.guardarValor(posicion + i, "-1", "Sistema Operativo", "CPU");
                        }
                        break;
                    case 15:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getTiempoInicio()), "Sistema Operativo", "Tiempo Inicio");
                        break;
                    case 16:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getTiempoEjecucion()), "Sistema Operativo", "Tiempo Ejecución");
                        break;
                    case 17:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getArchivos()), "Sistema Operativo", "Archivos");
                        break;
                    case 18:
                        if (bcp.getSiguiente() != null) {
                            this.guardarValor(posicion + i, String.valueOf(bcp.getSiguiente().getPid()), "Sistema Operativo", "Siguiente");
                        } else {
                            this.guardarValor(posicion + i, "-1", "Sistema Operativo", "Siguiente");
                        }
                        break;
                    case 19:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getInicio()), "Sistema Operativo", "Inicio");
                        break;
                    case 20:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getTamano()), "Sistema Operativo", "Tamaño");
                        break;
                    case 21:
                        this.guardarValor(posicion + i, String.valueOf(bcp.getPrioridad()), "Sistema Operativo", "Prioridad");
                        break;
                }   
            }
        } else {
            error = "No se encontró el BCP con ID " + id + " en la memoria del kernel para actualizarlo.";
        }
    }
}