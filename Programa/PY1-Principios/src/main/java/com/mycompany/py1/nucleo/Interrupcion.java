/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.py1.nucleo;

import com.mycompany.py1.nucleo.BCP;

/**
 *
 * @author sermo
 */
public interface Interrupcion {

    /** Muestra un texto en la interfaz. */
    void imprimir(String texto);

    /** Pide un dato al usuario y lo devuelve (null si cancela). */
    String leer();

    /** Crea un archivo vacío en el almacenamiento. */
    boolean crearArchivo(String nombre);

    /** Escribe una línea al final del archivo (el archivo crece si hace falta). */
    boolean escribirArchivo(String nombre, String contenido);

    /** Devuelve el contenido del archivo (null si no existe). */
    String leerArchivo(String nombre);

    /** Elimina el archivo del almacenamiento. */
    boolean eliminarArchivo(String nombre);


    void terminarPrograma(BCP bcp);

    boolean abrirArchivo(String nombre);
    
    void abortarProceso(BCP proceso, String mensaje);
}