package com.mycompany.tarea.principios;

/**
 *
 * @author sermo
 */
public class BCP {

    private int pid;
    private int pc;
    private String estado;
    private String instruccion; // IR: la instrucción que se acaba de ejecutar

    public BCP(int pid, int pc, String estado, String instruccion) {
        this.pid = pid;
        this.pc = pc;
        this.estado = estado;
        this.instruccion = instruccion;
    }

    public int getPid() {
        return pid;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
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
}