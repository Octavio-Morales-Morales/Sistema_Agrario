/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package IG;
import Objetos.Parcela;
import Service.ParcelasDAO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author moral
 */
public class PanelParcelas extends javax.swing.JPanel {
    private JTextField txtCodigo, txtNombre, txtUbicacion, txtArea, txtTipoSuelo, txtEstado;
    private JButton btnAgregar, btnEliminar;
    private JTable tablaParcelas;
    private DefaultTableModel modeloTabla;
    private ParcelasDAO dao = new ParcelasDAO();
    private List<Parcela> lista = new ArrayList<>();
    
    public PanelParcelas() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initFormulario();
        initTabla();
        cargarDatosTabla();
        configurarEventos();
    }
    private void initFormulario() {
        JPanel pnlDatos = new JPanel(new GridLayout(12, 1, 5, 5));
        pnlDatos.setBorder(BorderFactory.createTitledBorder("Gestión de Parcelas"));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtUbicacion = new JTextField();
        txtArea = new JTextField();
        txtTipoSuelo = new JTextField();
        txtEstado = new JTextField();

        pnlDatos.add(new JLabel("Código de Parcela:"));
        pnlDatos.add(txtCodigo);
        pnlDatos.add(new JLabel("Nombre:"));
        pnlDatos.add(txtNombre);
        pnlDatos.add(new JLabel("Ubicación:"));
        pnlDatos.add(txtUbicacion);
        pnlDatos.add(new JLabel("Área (Metros cuadrados):"));
        pnlDatos.add(txtArea);
        pnlDatos.add(new JLabel("Tipo de Suelo:"));
        pnlDatos.add(txtTipoSuelo);
        pnlDatos.add(new JLabel("Estado:"));
        pnlDatos.add(txtEstado);

        JPanel pnlBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        btnAgregar = new JButton("Agregar Parcela");
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(192, 57, 43)); 
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        
        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnEliminar);
        
        JPanel pnlIzquierdo = new JPanel(new BorderLayout(10, 10));
        pnlIzquierdo.add(pnlDatos, BorderLayout.CENTER);
        pnlIzquierdo.add(pnlBotones, BorderLayout.SOUTH);
        pnlIzquierdo.setPreferredSize(new Dimension(260, 0));

        add(pnlIzquierdo, BorderLayout.WEST);
    }

    private void initTabla() {
        JPanel pnlCabeceraTabla = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Lista de Parcelas Registradas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));

        String[] columnas = {"Código", "Nombre", "Ubicación", "Área", "Tipo Suelo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaParcelas = new JTable(modeloTabla);

        JPanel pnlDerecho = new JPanel(new BorderLayout(5, 5));
        pnlDerecho.add(pnlCabeceraTabla, BorderLayout.NORTH);
        pnlDerecho.add(new JScrollPane(tablaParcelas), BorderLayout.CENTER);

        add(pnlDerecho, BorderLayout.CENTER);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        lista = dao.seleccionar();
        for (Parcela p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getCodigo(), 
                p.getNombre(), 
                p.getUbicacion(), 
                p.getArea(), 
                p.getTipoSuelo(), 
                p.getEstado()
            });
        }
    }
 private void configurarEventos() {
     
        btnAgregar.addActionListener(e -> {            
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String ubicacion = txtUbicacion.getText().trim();
            String areaTexto = txtArea.getText().trim();
            String tipoSuelo = txtTipoSuelo.getText().trim();
            String estado = txtEstado.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty() || ubicacion.isEmpty() || areaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double area = Double.parseDouble(areaTexto);
                
                if (area <= 0) {
                    JOptionPane.showMessageDialog(this, "El área de la parcela debe ser estrictamente mayor que cero.", "Validación de Negocio", JOptionPane.ERROR_MESSAGE);
                    txtArea.requestFocus();
                    return;
                }

                Parcela parcela = new Parcela();
                parcela.setCodigo(codigo);
                parcela.setNombre(nombre);
                parcela.setUbicacion(ubicacion);
                parcela.setArea(area);
                parcela.setTipoSuelo(tipoSuelo.isEmpty() ? "No especificado" : tipoSuelo);
                parcela.setEstado(estado.isEmpty() ? "Disponible" : estado);

                if (dao.insertar(parcela)) {
                    JOptionPane.showMessageDialog(this, "Parcela agregada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al insertar la parcela.\nVerifique que el código '" + codigo + "' no esté repetido.", "Error de Clave Primaria", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El campo Área debe recibir un valor numérico decimal válido (Ej: 1500.50).", "Error de Tipo de Dato", JOptionPane.ERROR_MESSAGE);
            }
        });

     btnEliminar.addActionListener(e -> {
            int fila = tablaParcelas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione la parcela que desea eliminar.");
                return;
            }
            String codigo = modeloTabla.getValueAt(fila, 0).toString();
            int resp = JOptionPane.showConfirmDialog(this, "¿Eliminar Parcela '" + codigo + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                if (dao.eliminar(codigo)) {
                    JOptionPane.showMessageDialog(this, "Parcela eliminada correctamente.");
                    cargarDatosTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la parcela.");
                }
            }
        });
     
        tablaParcelas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaParcelas.getSelectedRow() != -1) {
                int fila = tablaParcelas.getSelectedRow();
                txtCodigo.setText(obtenerValorCelda(fila, 0));
                txtNombre.setText(obtenerValorCelda(fila, 1));
                txtUbicacion.setText(obtenerValorCelda(fila, 2));
                txtArea.setText(obtenerValorCelda(fila, 3));
                txtTipoSuelo.setText(obtenerValorCelda(fila, 4));
                txtEstado.setText(obtenerValorCelda(fila, 5));
            }
        });
        
 }
     private String obtenerValorCelda(int fila, int columna) {
       Object valor = modeloTabla.getValueAt(fila, columna);
        if (valor == null || "-".equals(valor.toString())) {
            return "";
        }
        return valor.toString();
    }


    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtUbicacion.setText("");
        txtArea.setText("");
        txtTipoSuelo.setText("");
        txtEstado.setText("");
    }

    
    
    
    
    
    
    
    
    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
