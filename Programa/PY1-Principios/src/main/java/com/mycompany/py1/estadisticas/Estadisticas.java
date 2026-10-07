package com.mycompany.py1.estadisticas;

import com.mycompany.py1.nucleo.BCP;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Estadisticas {
    private static final DateTimeFormatter HORA_MINUTO = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ArrayList<BCP> procesosEjecutados;

    public Estadisticas() {
        this.procesosEjecutados = new ArrayList<>();
    }

    /** Se llama cuando el proceso termina: marca la hora final y lo guarda. */
    public void agregarProcesoEjecutado(BCP proceso) {
        if (proceso.getTiempoInicio() != null) {
            proceso.setTiempoFin(
                proceso.getTiempoInicio().plusSeconds(proceso.getTiempoEjecucion())
            );
        }

        procesosEjecutados.add(proceso);
    }

    /** Filas para una tabla: Proceso, Inicio (HH:mm), Final (HH:mm), Duración (s). */
    public ArrayList<Object[]> getFilas() {
        ArrayList<Object[]> filas = new ArrayList<>();
        for (BCP p : procesosEjecutados) {
            filas.add(new Object[]{
                "PID " + p.getPid() + (p.getNombreArchivo() != null ? " (" + p.getNombreArchivo() + ")" : ""),
                p.getTiempoInicio() == null ? "-" : p.getTiempoInicio().format(HORA_MINUTO),
                p.getTiempoFin().format(HORA_MINUTO),
                p.getTiempoEjecucion()
            });
        }
        return filas;
    }

    /** Reporte final en texto, una línea por proceso. */
    public String estadisticasFinales() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-28s %-8s %-8s %s%n", "Proceso", "Inicio", "Final", "Duración (s)"));
        sb.append("-".repeat(60)).append("\n");
        for (Object[] fila : getFilas()) {
            sb.append(String.format("%-28s %-8s %-8s %s%n", fila[0], fila[1], fila[2], fila[3]));
        }
        return sb.toString();
    }

    public boolean estaVacia() {
        return procesosEjecutados.isEmpty();
    }

    public void limpiar() {
        procesosEjecutados.clear();
    }
}