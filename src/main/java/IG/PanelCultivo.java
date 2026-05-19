/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IG;

import javax.swing.JPanel;
import Objetos.Cultivo;
import Objetos.CultivoAnual;
import Objetos.CultivoPerenne;
import Service.CultivosDAO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author moral
 */
public class PanelCultivo extends JPanel {
    private JTextField txtCodigo, txtNombre, txtVariedad, txtFechaSiembra, txtDuracionCiclo, txtAniosProduccion;
    private JComboBox<String> cbTipoCultivo;
    private JButton btnAgregar, btnEliminar;
    private JTable tablaCultivos;
    private DefaultTableModel modeloTabla;
    private CultivosDAO dao = new CultivosDAO();
    private List<Cultivo> lista = new ArrayList<>();
    
    public PanelCultivo() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initFormulario();
        initTabla();
        configurarEventos();
        cargarDatosTabla();
    }
    private void initFormulario() {
        JPanel pnlDatos = new JPanel(new GridLayout(14, 1, 5, 5));
        pnlDatos.setBorder(BorderFactory.createTitledBorder("Gestión de Cultivos"));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtVariedad = new JTextField();
        txtFechaSiembra = new JTextField();
        cbTipoCultivo = new JComboBox<>(new String[]{"Anual", "Perenne"});
        txtDuracionCiclo = new JTextField();
        txtAniosProduccion = new JTextField();

        txtAniosProduccion.setEnabled(false);
        txtAniosProduccion.setBackground(Color.LIGHT_GRAY);

        pnlDatos.add(new JLabel("Código de Cultivo:"));
        pnlDatos.add(txtCodigo);
        pnlDatos.add(new JLabel("Nombre:"));
        pnlDatos.add(txtNombre);
        pnlDatos.add(new JLabel("Variedad:"));
        pnlDatos.add(txtVariedad);
        pnlDatos.add(new JLabel("Fecha Siembra:"));
        pnlDatos.add(txtFechaSiembra);
        pnlDatos.add(new JLabel("Tipo de Cultivo:"));
        pnlDatos.add(cbTipoCultivo);
        pnlDatos.add(new JLabel("Duración Ciclo (Días) - Solo Anual:"));
        pnlDatos.add(txtDuracionCiclo);
        pnlDatos.add(new JLabel("Años de Producción - Solo Perenne:"));
        pnlDatos.add(txtAniosProduccion);

        JPanel pnlBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        btnAgregar = new JButton("Agregar Cultivo");
                
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(192, 57, 43)); // Color Rojo Elegante
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
        JLabel lblTitulo = new JLabel("Lista de Cultivos Registrados");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));
        pnlCabeceraTabla.add(lblTitulo, BorderLayout.WEST);

        String[] columnas = {"Código", "Nombre", "Variedad", "Fecha Siembra", "Tipo", "Ciclo Días", "Años Prod."};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; }
        };
        tablaCultivos = new JTable(modeloTabla);
        tablaCultivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel pnlDerecho = new JPanel(new BorderLayout(5, 5));
        pnlDerecho.add(pnlCabeceraTabla, BorderLayout.NORTH);
        pnlDerecho.add(new JScrollPane(tablaCultivos), BorderLayout.CENTER);

        add(pnlDerecho, BorderLayout.CENTER);
    }
       
    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        lista = dao.seleccionar();
        for (Cultivo c : lista) {
            String ciclo = "-";
            String anios = "-";

            if (c instanceof CultivoAnual) {
                ciclo = String.valueOf(((CultivoAnual) c).getDuracionCicloDias());
            } else if (c instanceof CultivoPerenne) {
                anios = String.valueOf(((CultivoPerenne) c).getAniosProduccion());
            }

            modeloTabla.addRow(new Object[]{
                c.getCodigo(),
                c.getNombre(),
                c.getVariedad(),
                c.getFechaSiembra(),
                c.getTipoCultivo(),
                ciclo,
                anios
            });
        }
    }

    private void configurarEventos() {
        cbTipoCultivo.addActionListener(e -> {
            String seleccion = cbTipoCultivo.getSelectedItem().toString();
            if ("Anual".equals(seleccion)) {
                txtDuracionCiclo.setEnabled(true);
                txtDuracionCiclo.setBackground(Color.WHITE);
                txtAniosProduccion.setEnabled(false);
                txtAniosProduccion.setBackground(Color.LIGHT_GRAY);
                txtAniosProduccion.setText("");
            } else {
                txtDuracionCiclo.setEnabled(false);
                txtDuracionCiclo.setBackground(Color.LIGHT_GRAY);
                txtDuracionCiclo.setText("");
                txtAniosProduccion.setEnabled(true);
                txtAniosProduccion.setBackground(Color.WHITE);
            }
        });
        
        btnAgregar.addActionListener(e -> {
            
            if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios: Código y Nombre.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tipo = cbTipoCultivo.getSelectedItem().toString();
            
            if ("Anual".equals(tipo) && txtDuracionCiclo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la duración del ciclo en días.", "Validación", JOptionPane.WARNING_MESSAGE);
                txtDuracionCiclo.requestFocus();
                return;
            }
            if ("Perenne".equals(tipo) && txtAniosProduccion.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar los años de producción estimados.", "Validación", JOptionPane.WARNING_MESSAGE);
                txtAniosProduccion.requestFocus();
                return;
            }

            Cultivo cultivo;
            try {
                if ("Anual".equals(tipo)) {
                    int dias = Integer.parseInt(txtDuracionCiclo.getText().trim());
                    if (dias <= 0) {
                        JOptionPane.showMessageDialog(this, "La duración del ciclo debe ser un número estrictamente mayor que cero.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                        txtDuracionCiclo.requestFocus();
                        return;
                    }
                    CultivoAnual ca = new CultivoAnual();
                    ca.setDuracionCicloDias(dias);
                    cultivo = ca;
                } else {
                    int anios = Integer.parseInt(txtAniosProduccion.getText().trim());
                    if (anios <= 0) {
                        JOptionPane.showMessageDialog(this, "Los años de producción estimados deben ser un número estrictamente mayor que cero.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                        txtAniosProduccion.requestFocus();
                        return;
                    }
                    CultivoPerenne cp = new CultivoPerenne();
                    cp.setAniosProduccion(anios);
                    cultivo = cp;
                }

                cultivo.setCodigo(txtCodigo.getText().trim());
                cultivo.setNombre(txtNombre.getText().trim());
                cultivo.setVariedad(txtVariedad.getText().trim());
                cultivo.setFechaSiembra(txtFechaSiembra.getText().trim());
                cultivo.setTipoCultivo(tipo);

                if (dao.insertar(cultivo)) {
                    JOptionPane.showMessageDialog(this, "Cultivo agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar el cultivo en la Base de Datos.\nVerifique que el código '" + cultivo.getCodigo() + "' no esté duplicado.", "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Asegúrese de ingresar un número entero válido en las casillas numéricas.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaCultivos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione el cultivo que desea eliminar de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String codigo = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el cultivo con código '" + codigo + "'?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (dao.eliminar(codigo)) {
                    JOptionPane.showMessageDialog(this, "Cultivo eliminado correctamente de la Base de Datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede eliminar el cultivo.\nVerifique que no esté asignado a ninguna labor agrícola activa.", "Restricción de Integridad", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        tablaCultivos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaCultivos.getSelectedRow() != -1) {
                int fila = tablaCultivos.getSelectedRow();
                
                txtCodigo.setText(obtenerValorCelda(fila, 0));
                txtNombre.setText(obtenerValorCelda(fila, 1));
                txtVariedad.setText(obtenerValorCelda(fila, 2));
                txtFechaSiembra.setText(obtenerValorCelda(fila, 3));
                
                String tipo = obtenerValorCelda(fila, 4);
                cbTipoCultivo.setSelectedItem(tipo);
                
                if ("Anual".equals(tipo)) {
                    txtDuracionCiclo.setText(obtenerValorCelda(fila, 5));
                    txtAniosProduccion.setText("");
                } else {
                    txtAniosProduccion.setText(obtenerValorCelda(fila, 6));
                    txtDuracionCiclo.setText("");
                }
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
        txtVariedad.setText("");
        txtFechaSiembra.setText("");
        txtDuracionCiclo.setText("");
        txtAniosProduccion.setText("");
        cbTipoCultivo.setSelectedIndex(0);
    }
}