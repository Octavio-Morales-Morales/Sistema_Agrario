/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IG;

import Objetos.LaborAgricola;
import Service.LaboresAgricolasDAO;
import Service.GeneradorInforme;
import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author moral
 */
public class PanelGenerarInforme extends JPanel {
    private JTable tablaLabores;
    private DefaultTableModel modeloTabla;
    private JButton btnGenerarInforme;
    
    private LaboresAgricolasDAO laboresDAO = new LaboresAgricolasDAO();
    private GeneradorInforme generador = new GeneradorInforme();
    private List<LaborAgricola> listaLabores;
    
        public PanelGenerarInforme() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initTabla();
        initBotones();
        configurarEventos();
        cargarDatosTabla();
    }

    private void initTabla() {
        JPanel pnlCabecera = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Historial de Labores Agrícolas para Exportación");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        pnlCabecera.add(lblTitulo, BorderLayout.WEST);

        String[] columnas = {"Código", "Fecha", "Parcela", "Cultivo", "Responsable", "Tipo Labor", "Costo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaLabores = new JTable(modeloTabla);
        tablaLabores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(pnlCabecera, BorderLayout.NORTH);
        add(new JScrollPane(tablaLabores), BorderLayout.CENTER);
    }
    

    private void initBotones() {
        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGenerarInforme = new JButton("Exportar Informe Plano (.txt)");
        btnGenerarInforme.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnGenerarInforme.setBackground(new Color(41, 128, 185));
        btnGenerarInforme.setForeground(Color.WHITE);
        btnGenerarInforme.setFocusPainted(false);
        
        pnlAcciones.add(btnGenerarInforme);
        add(pnlAcciones, BorderLayout.SOUTH);
    }

    public void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        listaLabores = laboresDAO.seleccionar();
        
        if (listaLabores != null) {
            for (LaborAgricola l : listaLabores) {
                String parcela = (l.getParcela() != null) ? l.getParcela().getNombre() : "N/A";
                String cultivo = (l.getCultivo() != null) ? l.getCultivo().getNombre() : "N/A";
                String resp = (l.getResponsable() != null) ? l.getResponsable().getNombreCompleto() : "N/A";

                modeloTabla.addRow(new Object[]{
                    l.getCodigo(),
                    l.getFechaRealizacion(),
                    parcela,
                    cultivo,
                    resp,
                    l.getTipoLabor(),
                    String.format("%.2f", l.getCostoEstimado())
                });
            }
        }
    }

    private void configurarEventos() {
        btnGenerarInforme.addActionListener(e -> {
            if (listaLabores == null || listaLabores.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay datos en la base de datos para generar un reporte.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser selectorArchivo = new JFileChooser();
            selectorArchivo.setDialogTitle("Seleccione dónde guardar el Informe de Labores");
            
            selectorArchivo.setSelectedFile(new File("informe_labores_agricolas.txt"));

            int seleccion = selectorArchivo.showSaveDialog(this);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivoDestino = selectorArchivo.getSelectedFile();
                
                if (generador.guardarInformePlano(archivoDestino, listaLabores)) {
                    JOptionPane.showMessageDialog(this, "¡Informe exportado con éxito!\nGuardado en: " + archivoDestino.getAbsolutePath(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
    

