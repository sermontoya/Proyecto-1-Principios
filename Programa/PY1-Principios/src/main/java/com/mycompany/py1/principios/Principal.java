/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.py1.principios;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sermo
 */
public class Principal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Principal.class.getName());

    private Memoria memoria;
    private CPU cpu;
    private BCP bcp;
    private ArrayList<String> instrucciones;
    private int instruccionEjecutada = -1;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        configurarDiseno();
        crearMemoria();
    }

    // Configura el diseño de la interfaz, incluyendo colores, fuentes y estilos de las tablas
    private void configurarDiseno() {

        getContentPane().setBackground(
                new Color(245, 247, 250)
        );

        Font encabezado = new Font(
                "Segoe UI", Font.BOLD, 14
        );

        Font contenido = new Font(
                "Segoe UI", Font.PLAIN, 13
        );

        javax.swing.JTable[] tablas = {
            jTable1, jTable2, jTable3, jTable4
        };

        for (javax.swing.JTable tabla : tablas) {

            tabla.setFont(contenido);

            configurarEncabezado(tabla);

            tabla.setRowHeight(28);

            tabla.setShowGrid(true);

            tabla.setGridColor(
                    new Color(220, 225, 232)
            );

            tabla.setSelectionBackground(
                    new Color(210, 225, 245)
            );

            tabla.setSelectionForeground(Color.BLACK);
        }

        configurarResaltadoInstrucciones();
    }

    // Configura el encabezado de la tabla con colores, fuente y alineación
    private void configurarEncabezado(javax.swing.JTable tabla) {
        tabla.getTableHeader().setOpaque(true);
        tabla.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                Component componente = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                componente.setBackground(new Color(51, 65, 85));
                componente.setForeground(new Color(248, 250, 252));
                componente.setFont(new Font("Segoe UI", Font.BOLD, 13));
                setOpaque(true);
                setHorizontalAlignment(CENTER);

                return componente;
            }
        });
    }
    // Configura el resaltado de la instrucción ejecutada en la tabla de instrucciones
    private void configurarResaltadoInstrucciones() {

        jTable1.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                Component componente =
                        super.getTableCellRendererComponent(
                                table,
                                value,
                                isSelected,
                                hasFocus,
                                row,
                                column
                        );

                if (row == instruccionEjecutada) {

                    // Instrucción ejecutada
                    componente.setBackground(
                            new Color(220, 242, 225)
                    );

                    componente.setForeground(
                            new Color(25, 95, 45)
                    );

                    componente.setFont(
                            componente.getFont().deriveFont(
                                    Font.BOLD
                            )
                    );

                } else {

                    // Resto de instrucciones
                    componente.setBackground(Color.WHITE);

                    componente.setForeground(Color.DARK_GRAY);

                    componente.setFont(
                            componente.getFont().deriveFont(
                                    Font.PLAIN
                            )
                    );
                }

                return componente;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTable1.setForeground(new java.awt.Color(102, 102, 102));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Instrucción", "Binario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(30);
        jScrollPane1.setViewportView(jTable1);

        jTable2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Posición", "Valor", "Zona"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        jSpinner1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(128, 128, null, 1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Tamaño de memoria: ");

        jButton1.setText("Cargar Archivo");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jTable3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"AC", "0"},
                {"AX", "0"},
                {"BX", "0"},
                {"CX", "0"},
                {"DX", "0"}
            },
            new String [] {
                "Registro", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable3.setRowHeight(27);
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("CPU");

        jTable4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PID", null},
                {"PC", "0"},
                {"Estado", null},
                {"IR", null}
            },
            new String [] {
                "Atributo", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable4.setRowHeight(27);
        jScrollPane4.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setPreferredWidth(1);
            jTable4.getColumnModel().getColumn(1).setPreferredWidth(1);
        }

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("BCP");

        jButton2.setText("Ejecutar todo");
        jButton2.setEnabled(false);
        jButton2.setPreferredSize(new java.awt.Dimension(109, 23));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Ejecutar paso");
        jButton3.setEnabled(false);
        jButton3.setPreferredSize(new java.awt.Dimension(109, 23));
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Cambiar tamaño");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Instrucciones");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Memoria");

        jButton5.setText("Limpiar");
        jButton5.setPreferredSize(new java.awt.Dimension(109, 23));
        jButton5.addActionListener(this::jButton5ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(129, 129, 129)
                                        .addComponent(jLabel2))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(139, 139, 139)
                                        .addComponent(jLabel5)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(206, 206, 206)
                                .addComponent(jLabel4)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(118, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo .asm");

        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Archivos ASM (*.asm)", "asm"
        );
        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(this);

        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivoSeleccionado = selector.getSelectedFile();
        String ruta = archivoSeleccionado.getAbsolutePath();

        Validator loader = new Validator();

        if (!loader.cargarArchivo(ruta)) {
            JOptionPane.showMessageDialog(this, loader.getError(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        instrucciones = loader.getInstrucciones();
        memoria.asignarInstrucciones(instrucciones);

        if (memoria.getError() != null) {
            // El programa no cabe en el espacio de Usuario disponible
            JOptionPane.showMessageDialog(this, memoria.getError(), "Error", JOptionPane.ERROR_MESSAGE);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            return;
        }

        JOptionPane.showMessageDialog(this, "El archivo se cargó correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

        cargarTablaInstrucciones();
        actualizarTablaMemoria();

        cpu = new CPU(memoria);
        bcp = new BCP(1, 0, "LISTO", "", 0, 0, 1);
        actualizarTablaRegistros();

        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        while (bcp != null && bcp.getPc() < instrucciones.size()) {
            ejecutarUnPaso();
        }
        finalizarSiTermino();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (bcp != null && bcp.getPc() < instrucciones.size()) {
            ejecutarUnPaso();
        }
        finalizarSiTermino();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        crearMemoria();
    }//GEN-LAST:event_jButton4ActionPerformed
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        crearMemoria();
    }

    /** Ejecuta una sola instrucción  y refresca la tabla de CPU y BCP. */
    private void ejecutarUnPaso() {
        int indiceActual = bcp.getPc();

        String instruccionActual = instrucciones.get(indiceActual);

        // Guardar la instrucción que se está ejecutando
        instruccionEjecutada = indiceActual;

        bcp.setEstado("EJECUCION");

        bcp.setInstruccion(instruccionActual);

        // Ejecutar la instrucción
        cpu.ejecutarInstruccion(instruccionActual, bcp);

        // Avanzar al siguiente PC
        bcp.avanzarPc();

        // Actualizar las tablas
        actualizarTablaRegistros();
        actualizarTablaBcp();

        // Actualizar el resaltado de jTable1
        jTable1.repaint();

        // Desplazar la tabla para mostrar la instrucción
        // que se acaba de ejecutar
        if (indiceActual >= 0 &&
                indiceActual < jTable1.getRowCount()) {

            jTable1.setRowSelectionInterval(
                    indiceActual,
                    indiceActual
            );

            jTable1.scrollRectToVisible(
                    jTable1.getCellRect(indiceActual, 0, true)
            );
        }
    }

    /** Deshabilita los botones de ejecución y avisa cuando ya no quedan instrucciones. */
    private void finalizarSiTermino() {
        if (bcp != null && bcp.getPc() >= instrucciones.size()) {
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Programa terminado.",
                    "Ejecución completa", JOptionPane.INFORMATION_MESSAGE);
            bcp.setEstado("FINALIZADO");
            actualizarTablaBcp();
        }
    }

    /** Refleja los registros del CPU en jTable3. */
    private void actualizarTablaRegistros() {
        DefaultTableModel modeloCpu = (DefaultTableModel) jTable3.getModel();
        modeloCpu.setValueAt(String.valueOf(cpu.getAccumulator()), 0, 1);
        modeloCpu.setValueAt(String.valueOf(cpu.getAx()), 1, 1);
        modeloCpu.setValueAt(String.valueOf(cpu.getBx()), 2, 1);
        modeloCpu.setValueAt(String.valueOf(cpu.getCx()), 3, 1);
        modeloCpu.setValueAt(String.valueOf(cpu.getDx()), 4, 1);

        actualizarTablaBcp();
    }

    /** Refleja los atributos del BCP en jTable4. */
    private void actualizarTablaBcp() {
        DefaultTableModel modeloBcp = (DefaultTableModel) jTable4.getModel();
        modeloBcp.setValueAt(String.valueOf(bcp.getPid()), 0, 1);
        modeloBcp.setValueAt(String.valueOf(bcp.getPc()), 1, 1);
        modeloBcp.setValueAt(bcp.getEstado(), 2, 1);
        modeloBcp.setValueAt(bcp.getInstruccion(), 3, 1);
    }

    /** Traduce cada instrucción ya validada a binario y llena jTable1. */
    private void cargarTablaInstrucciones() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
       

    

        // Ajustar la altura del JScrollPane según la cantidad de filas
        int alturaFila = jTable1.getRowHeight();
        int cantidadFilas = modelo.getRowCount();
        int alturaDeseada = alturaFila * cantidadFilas
                + jTable1.getTableHeader().getPreferredSize().height;
        int alturaMaxima = 260;
        alturaDeseada = Math.min(alturaDeseada, alturaMaxima);

        jScrollPane1.setPreferredSize(
                new java.awt.Dimension(jScrollPane1.getPreferredSize().width, alturaDeseada));
        jScrollPane1.revalidate();
        jScrollPane1.repaint();
    }

    /** Crea una Memoria nueva del tamaño indicado en el spinner y limpia el estado de ejecución. */
    private void crearMemoria() {
        int tamano = (Integer) jSpinner1.getValue();
        memoria = new Memoria(tamano);
        actualizarTablaMemoria();

        // Un cambio de tamaño invalida cualquier programa/ejecución previa
        instrucciones = null;
        instruccionEjecutada = -1;
        cpu = null;
        bcp = null;
        jTable1.clearSelection();
        jTable1.repaint();
        ((DefaultTableModel) jTable1.getModel()).setRowCount(0);
        DefaultTableModel modeloRegistros = (DefaultTableModel) jTable3.getModel();
        for (int fila = 0; fila < modeloRegistros.getRowCount(); fila++) {
            modeloRegistros.setValueAt("0", fila, 1);
        }
        DefaultTableModel modeloBcp = (DefaultTableModel) jTable4.getModel();
        modeloBcp.setValueAt(null, 0, 1);
        modeloBcp.setValueAt("0", 1, 1);
        modeloBcp.setValueAt("NUEVO", 2, 1);
        modeloBcp.setValueAt(null, 3, 1);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }

    /** Refleja el contenido actual del objeto Memoria en jTable2. */
    private void actualizarTablaMemoria() {
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        modelo.setRowCount(0);
        ArrayList<Posicion> posiciones = memoria.getMemoria();
        for (int i = 0; i < posiciones.size(); i++) {
            Posicion p = posiciones.get(i);
            modelo.addRow(new Object[]{i, p.getValor(), p.getZona()});
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Principal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    // End of variables declaration//GEN-END:variables
}