/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.py1.disco;

import com.mycompany.py1.memoria.Posicion;
import java.util.ArrayList;

/**
 * @author sermo
 */
public class Almacenamiento {

    private static final String SIN_PROPOSITO = "Sin propósito";
    private static final String ZONA_INDICES = "Índices";
    private static final String ZONA_ARCHIVOS = "Archivos";
    private static final String ZONA_VIRTUAL = "Memoria Virtual";
    private static final int POSICIONES_POR_INDICE = 3; // nombre, inicio, tamaño

    int size;
    int inicioIndices;
    int inicioUsuario;   // inicio de la zona donde se guarda el contenido de los archivos
    int inicioVirtual;
    private ArrayList<Posicion> almacenamiento;
    String error = null;

    public Almacenamiento(int size) {
        this.size = size;
        this.inicioIndices = 0;
        this.inicioUsuario = size / 4;
        this.inicioVirtual = size - (size / 8);
        this.almacenamiento = new ArrayList<>(size);
        asignarAlmacenamiento();
    }

    // Llena las tres zonas con posiciones vacías
    private void asignarAlmacenamiento() {
        for (int i = inicioIndices; i < inicioUsuario; i++) {
            almacenamiento.add(new Posicion("0", ZONA_INDICES, SIN_PROPOSITO));
        }
        for (int i = inicioUsuario; i < inicioVirtual; i++) {
            almacenamiento.add(new Posicion("0", ZONA_ARCHIVOS, SIN_PROPOSITO));
        }
        for (int i = inicioVirtual; i < size; i++) {
            almacenamiento.add(new Posicion("0", ZONA_VIRTUAL, SIN_PROPOSITO));
        }
    }

    /** Guarda un archivo: reserva una entrada en el índice y espacio contiguo para su contenido. */
    public boolean guardarArchivo(String nombre, ArrayList<String> lineas) {
        error = null;

        if (lineas == null || lineas.isEmpty()) {
            error = "El archivo está vacío.";
            return false;
        }
        if (buscarIndice(nombre) != -1) {
            error = "Ya existe un archivo llamado " + nombre + ".";
            return false;
        }
        int indice = buscarIndiceLibre();
        if (indice == -1) {
            error = "No hay espacio en el área de índices para registrar el archivo.";
            return false;
        }
        int inicio = buscarEspacioContenido(lineas.size());
        if (inicio == -1) {
            error = "No hay espacio contiguo en el almacenamiento para " + lineas.size() + " líneas.";
            return false;
        }

        // Contenido
        for (int i = 0; i < lineas.size(); i++) {
            guardarValor(inicio + i, lineas.get(i), ZONA_ARCHIVOS, "Contenido: " + nombre);
        }
        // Entrada de índice: nombre, inicio, tamaño
        guardarValor(indice, nombre, ZONA_INDICES, "Índice: nombre");
        guardarValor(indice + 1, String.valueOf(inicio), ZONA_INDICES, "Índice: inicio");
        guardarValor(indice + 2, String.valueOf(lineas.size()), ZONA_INDICES, "Índice: tamaño");
        return true;
    }

    /** Devuelve las líneas del archivo, o null si no existe. */
    public ArrayList<String> leerArchivo(String nombre) {
        error = null;
        int indice = buscarIndice(nombre);
        if (indice == -1) {
            error = "No existe un archivo llamado " + nombre + ".";
            return null;
        }
        int inicio = Integer.parseInt(almacenamiento.get(indice + 1).getValor());
        int tamano = Integer.parseInt(almacenamiento.get(indice + 2).getValor());

        ArrayList<String> lineas = new ArrayList<>();
        for (int i = 0; i < tamano; i++) {
            lineas.add(almacenamiento.get(inicio + i).getValor());
        }
        return lineas;
    }

    /** Borra el archivo: libera su contenido y su entrada de índice. */
    public boolean eliminarArchivo(String nombre) {
        error = null;
        int indice = buscarIndice(nombre);
        if (indice == -1) {
            error = "No existe un archivo llamado " + nombre + ".";
            return false;
        }
        int inicio = Integer.parseInt(almacenamiento.get(indice + 1).getValor());
        int tamano = Integer.parseInt(almacenamiento.get(indice + 2).getValor());

        for (int i = 0; i < tamano; i++) {
            guardarValor(inicio + i, "0", ZONA_ARCHIVOS, SIN_PROPOSITO);
        }
        for (int i = 0; i < POSICIONES_POR_INDICE; i++) {
            guardarValor(indice + i, "0", ZONA_INDICES, SIN_PROPOSITO);
        }
        return true;
    }

    /** Crea un archivo vacío: solo ocupa su entrada de índice (inicio = -1, tamaño = 0). */
    public boolean crearArchivo(String nombre) {
        error = null;
        if (buscarIndice(nombre) != -1) {
            error = "Ya existe un archivo llamado " + nombre + ".";
            return false;
        }
        int indice = buscarIndiceLibre();
        if (indice == -1) {
            error = "No hay espacio en el área de índices para registrar el archivo.";
            return false;
        }
        guardarValor(indice, nombre, ZONA_INDICES, "Índice: nombre");
        guardarValor(indice + 1, "-1", ZONA_INDICES, "Índice: inicio");
        guardarValor(indice + 2, "0", ZONA_INDICES, "Índice: tamaño");
        return true;
    }

    /**
     * Reemplaza todo el contenido del archivo. Si el nuevo contenido no cabe donde estaba,
     * se reubica en otro hueco contiguo y se actualiza el índice.
     */
    public boolean escribirArchivo(String nombre, ArrayList<String> lineas) {
        error = null;
        int indice = buscarIndice(nombre);
        if (indice == -1) {
            error = "No existe un archivo llamado " + nombre + ".";
            return false;
        }
        int inicioViejo = Integer.parseInt(almacenamiento.get(indice + 1).getValor());
        int tamanoViejo = Integer.parseInt(almacenamiento.get(indice + 2).getValor());
        ArrayList<String> respaldo = leerArchivo(nombre);

        // Se libera el bloque viejo primero: su espacio también cuenta al buscar hueco,
        // así un archivo que crece junto a espacio libre se queda en su misma posición
        liberarContenido(inicioViejo, tamanoViejo);

        if (lineas.isEmpty()) {
            actualizarIndice(indice, -1, 0);
            return true;
        }

        int inicioNuevo = buscarEspacioContenido(lineas.size());
        if (inicioNuevo == -1) {
            // No cabe en ningún hueco: se restaura el contenido anterior
            for (int i = 0; i < tamanoViejo; i++) {
                guardarValor(inicioViejo + i, respaldo.get(i), ZONA_ARCHIVOS, "Contenido: " + nombre);
            }
            error = "No hay espacio contiguo en el almacenamiento para " + lineas.size() + " líneas.";
            return false;
        }

        for (int i = 0; i < lineas.size(); i++) {
            guardarValor(inicioNuevo + i, lineas.get(i), ZONA_ARCHIVOS, "Contenido: " + nombre);
        }
        actualizarIndice(indice, inicioNuevo, lineas.size());
        return true;
    }

    /** Agrega una línea al final del archivo (el archivo crece y puede reubicarse). */
    public boolean agregarLinea(String nombre, String linea) {
        ArrayList<String> lineas = leerArchivo(nombre);
        if (lineas == null) {
            return false;
        }
        lineas.add(linea);
        return escribirArchivo(nombre, lineas);
    }

    /** Cambia una línea existente sin mover el archivo (el tamaño no cambia). */
    public boolean modificarLinea(String nombre, int numeroLinea, String nuevoValor) {
        error = null;
        int indice = buscarIndice(nombre);
        if (indice == -1) {
            error = "No existe un archivo llamado " + nombre + ".";
            return false;
        }
        int inicio = Integer.parseInt(almacenamiento.get(indice + 1).getValor());
        int tamano = Integer.parseInt(almacenamiento.get(indice + 2).getValor());
        if (numeroLinea < 0 || numeroLinea >= tamano) {
            error = "La línea " + numeroLinea + " no existe en " + nombre + "; use agregarLinea para ampliarlo.";
            return false;
        }
        guardarValor(inicio + numeroLinea, nuevoValor, ZONA_ARCHIVOS, "Contenido: " + nombre);
        return true;
    }

    private void liberarContenido(int inicio, int tamano) {
        for (int i = 0; i < tamano; i++) {
            guardarValor(inicio + i, "0", ZONA_ARCHIVOS, SIN_PROPOSITO);
        }
    }

    private void actualizarIndice(int indice, int inicio, int tamano) {
        guardarValor(indice + 1, String.valueOf(inicio), ZONA_INDICES, "Índice: inicio");
        guardarValor(indice + 2, String.valueOf(tamano), ZONA_INDICES, "Índice: tamaño");
    }

    // Posición de la entrada de índice del archivo, o -1 si no existe
    private int buscarIndice(String nombre) {
        for (int i = inicioIndices; i + POSICIONES_POR_INDICE <= inicioUsuario; i += POSICIONES_POR_INDICE) {
            Posicion p = almacenamiento.get(i);
            if (p.getProposito().equals("Índice: nombre") && p.getValor().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    // Primera entrada de índice libre, o -1 si el área de índices está llena
    private int buscarIndiceLibre() {
        for (int i = inicioIndices; i + POSICIONES_POR_INDICE <= inicioUsuario; i += POSICIONES_POR_INDICE) {
            if (almacenamiento.get(i).getProposito().equals(SIN_PROPOSITO)) {
                return i;
            }
        }
        return -1;
    }

    // Primer ajuste: busca 'cantidad' posiciones libres contiguas en la zona de archivos
    private int buscarEspacioContenido(int cantidad) {
        int seguidas = 0;
        for (int i = inicioUsuario; i < inicioVirtual; i++) {
            if (almacenamiento.get(i).getProposito().equals(SIN_PROPOSITO)) {
                seguidas++;
                if (seguidas == cantidad) {
                    return i - cantidad + 1;
                }
            } else {
                seguidas = 0;
            }
        }
        return -1;
    }

    // ---------------------------------------------------------------
    // Memoria virtual
    // ---------------------------------------------------------------

    /** Reserva espacio en la memoria virtual para un proceso. Devuelve la posición de inicio, o -1 si no cabe. */
    public int reservarVirtual(int pid, ArrayList<String> instrucciones) {
        error = null;
        int cantidad = instrucciones.size();
        int seguidas = 0;
        for (int i = inicioVirtual; i < size; i++) {
            if (almacenamiento.get(i).getProposito().equals(SIN_PROPOSITO)) {
                seguidas++;
                if (seguidas == cantidad) {
                    int inicio = i - cantidad + 1;
                    for (int j = 0; j < cantidad; j++) {
                        guardarValor(inicio + j, instrucciones.get(j), ZONA_VIRTUAL, "Swap PID " + pid);
                    }
                    return inicio;
                }
            } else {
                seguidas = 0;
            }
        }
        error = "No hay espacio suficiente en la memoria virtual (" + getTamanoVirtual() + " posiciones).";
        return -1;
    }

    /** Libera el espacio que ocupaba un proceso en la memoria virtual. */
    public void liberarVirtual(int pid) {
        for (int i = inicioVirtual; i < size; i++) {
            if (almacenamiento.get(i).getProposito().equals("Swap PID " + pid)) {
                guardarValor(i, "0", ZONA_VIRTUAL, SIN_PROPOSITO);
            }
        }
    }

    /** Verifica que el archivo exista en el índice
     *  si no, deja el mensaje en error. */
    public boolean existeArchivo(String nombre) {
        error = null;
        if (buscarIndice(nombre) == -1) {
            error = "No existe un archivo llamado " + nombre + ".";
            return false;
        }
        return true;
    }

    private void guardarValor(int direccion, String valor, String zona, String proposito) {
        almacenamiento.get(direccion).setValor(valor);
        almacenamiento.get(direccion).setZona(zona);
        almacenamiento.get(direccion).setProposito(proposito);
    }

    public int getSize() {
        return size;
    }

    public int getInicioIndices() {
        return inicioIndices;
    }

    public int getInicioUsuario() {
        return inicioUsuario;
    }

    public int getInicioVirtual() {
        return inicioVirtual;
    }

    public int getTamanoVirtual() {
        return size - inicioVirtual;
    }

    public ArrayList<Posicion> getAlmacenamiento() {
        return almacenamiento;
    }

    public String getError() {
        return error;
    }
}