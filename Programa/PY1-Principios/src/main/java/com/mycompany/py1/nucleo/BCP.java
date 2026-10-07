package com.mycompany.py1.nucleo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author sermo
 */
public class BCP {

    private int pid;
    private int pc;
    private String estado;
    private String instruccion = "0"; // IR: la instrucción que se acaba de ejecutar
    private String ac = "0";
    private String ax = "0";
    private String bx = "0";
    private String cx = "0";
    private String dx = "0";
    private BCP siguiente;
    private CPU cpu;
    private Stack<String> pila;
    private LocalDateTime tiempoInicio;
    private long tiempoEjecucion;
    private ArrayList<String> archivos;
    private int inicio;
    private int tamano;
    private int prioridad;
    private String nombreArchivo;   // archivo en Almacenamiento del que sale el programa
    private int ciclosRestantes = 0;   // 0 = no hay instrucción a medias
    private int ciclosTotales = 0;
    private LocalDateTime tiempoFin;
    public static final int TAM_PILA = 5;

    
    

    public BCP(int pid, int pc, String estado, String instruccion, int inicio, int tamano, int prioridad) {
        this.pid = pid;
        this.pc = pc;
        this.estado = estado;
        this.instruccion = instruccion;
        this.inicio = inicio;
        this.tamano = tamano;
        this.prioridad = prioridad;
        this.pila = new Stack<>();
        this.archivos = new ArrayList<>();
        this.tiempoEjecucion = 0;
    }

    public int getPid() {
        return pid;
    }

    public int getPc() {
        return pc;
    }
    
    public int getInicio() {
        return inicio;
    }

    public int getTamano() {
        return tamano;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public BCP getSiguiente() {
        return siguiente;
    }

    public ArrayList<String> getArchivos() {
        return archivos;
    }
    public void setSiguiente(BCP siguiente) {
        this.siguiente = siguiente;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getAx() {
        return ax;
    }

    public void setAx(String ax) {
        this.ax = ax;
    }

    public String getBx() {
        return bx;
    }

    public void setBx(String bx) {
        this.bx = bx;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getDx() {
        return dx;
    }

    public void setDx(String dx) {
        this.dx = dx;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }   

    public void avanzarPc() {
        this.pc++;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    public boolean pushPila(String valor) {
        if (pila.size() >= TAM_PILA) {
            return false;
        }
        pila.push(valor);
        return true;
    }

    public String popPila() {
        if (pila.isEmpty()) {
            return null; // No hay elementos en la pila para sacar
        }
        return pila.pop();
    }

    public void abrirArchivo(String nombre) {
        if (!archivos.contains(nombre)) {
            archivos.add(nombre);
        }
    }

public void cerrarArchivo(String nombre) {
    archivos.remove(nombre);
}

    public Stack<String> getPila() {
        return pila;
    }

    public LocalDateTime getTiempoInicio() {
        return tiempoInicio;
    }

    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void incrementarTiempoEjecucion() {
        this.tiempoEjecucion++;
    }

    public void setTiempoEjecucion(long tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public void setTiempoInicio(LocalDateTime tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }
    public void inicioProceso() {
        this.tiempoInicio = LocalDateTime.now();
    }

    public int getCiclosRestantes() { return ciclosRestantes; }

    public void setCiclosRestantes(int c) { this.ciclosRestantes = c; }

    public int getCiclosTotales() { return ciclosTotales; }

    public void setCiclosTotales(int c) { this.ciclosTotales = c; }


    public String getNombreArchivo() { return nombreArchivo; }

    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    // La base puede cambiar entre que el trabajo se crea y que se admite en memoria
    public void setInicio(int inicio) { this.inicio = inicio; }

    

    public LocalDateTime getTiempoFin() {
        return tiempoFin;
    }

    public void setTiempoFin(LocalDateTime tiempoFin) {
        this.tiempoFin = tiempoFin;
    }

    public int espacioPila() {
        return TAM_PILA - pila.size();
    }


}