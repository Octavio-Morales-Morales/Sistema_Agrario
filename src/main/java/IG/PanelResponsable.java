/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IG;

import javax.swing.JPanel;
import Objetos.Responsable;
import Objetos.Productor;
import Objetos.TecnicoAgricola;
import Service.ResponsablesDAO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author moral
 */
public class PanelResponsable extends JPanel{

    private JTextField txtIdentificacion, txtNombre, txtCorreo, txtTelefono;
    private JComboBox<String> cbTipoResponsable, cbFinca, cbEspecialidad;;
    private JButton btnAgregar, btnEliminar;
    private JTable tablaResponsables;
    private DefaultTableModel modeloTabla;
    private ResponsablesDAO dao = new ResponsablesDAO();
    private List<Responsable> lista = new ArrayList<>();

    public PanelResponsable() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initFormulario();
        initTabla();
        configurarEventos();
        cargarDatosTabla();
        cargarCombosDesdeBD();
    }

    private void initFormulario() {
        JPanel pnlDatos = new JPanel(new GridLayout(14, 1, 5, 5));
        pnlDatos.setBorder(BorderFactory.createTitledBorder("Gestión de Responsables"));

        txtIdentificacion = new JTextField();
        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtTelefono = new JTextField();
        cbTipoResponsable = new JComboBox<>(new String[]{"Productor", "Tecnico Agricola"});
        cbFinca = new JComboBox<>();
        cbEspecialidad = new JComboBox<>();

        cbEspecialidad.setEnabled(false);
        cbEspecialidad.setBackground(Color.LIGHT_GRAY);

        pnlDatos.add(new JLabel("Identificación (Cédula/ID):"));
        pnlDatos.add(txtIdentificacion);
        pnlDatos.add(new JLabel("Nombre Completo:"));
        pnlDatos.add(txtNombre);
        pnlDatos.add(new JLabel("Correo Electrónico:"));
        pnlDatos.add(txtCorreo);
        pnlDatos.add(new JLabel("Teléfono:"));
        pnlDatos.add(txtTelefono);
        pnlDatos.add(new JLabel("Tipo de Responsable:"));
        pnlDatos.add(cbTipoResponsable);
        pnlDatos.add(new JLabel("Nombre Finca / Asociación (Solo Productor):"));
        pnlDatos.add(cbFinca);
        pnlDatos.add(new JLabel("Especialidad Técnica (Solo Técnico):"));
        pnlDatos.add(cbEspecialidad);

        JPanel pnlBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        btnAgregar = new JButton("Agregar Responsable");
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(192, 57, 43));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        
        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnEliminar);
        
        JPanel pnlIzquierdo = new JPanel(new BorderLayout(10, 10));
        pnlIzquierdo.add(pnlDatos, BorderLayout.CENTER);
        pnlIzquierdo.add(pnlBotones, BorderLayout.SOUTH);
        pnlIzquierdo.setPreferredSize(new Dimension(270, 0));

        add(pnlIzquierdo, BorderLayout.WEST);
    }

    private void initTabla() {
        JPanel pnlCabeceraTabla = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Responsables Registrados en el Sistema");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));
        pnlCabeceraTabla.add(lblTitulo, BorderLayout.WEST);

        String[] columnas = {"Identificación", "Nombre", "Correo", "Teléfono", "Tipo", "Finca/Asoc.", "Especialidad"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResponsables = new JTable(modeloTabla);
        

        JPanel pnlDerecho = new JPanel(new BorderLayout(5, 5));
        pnlDerecho.add(pnlCabeceraTabla, BorderLayout.NORTH);
        pnlDerecho.add(new JScrollPane(tablaResponsables), BorderLayout.CENTER);

        add(pnlDerecho, BorderLayout.CENTER);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        lista = dao.seleccionar();
        for (Responsable r : lista) {
            String finca = "-";
            String especialidad = "-";

            if (r instanceof Productor) {
                finca = ((Productor) r).getNombreFincaAsociacion();
            } else if (r instanceof TecnicoAgricola) {
                especialidad = ((TecnicoAgricola) r).getEspecialidadTecnica();
            }

            modeloTabla.addRow(new Object[]{
                r.getIdentificacion(),
                r.getNombreCompleto(),
                r.getCorreo(),
                r.getTelefono(),
                r.getTipoResponsable(),
                finca,
                especialidad
            });
        }
    }
     private void cargarCombosDesdeBD() {
        cbFinca.removeAllItems();
        cbFinca.addItem("");
        List<String> fincas = dao.obtenerFincasUnicas();
        for (String f : fincas) {
            cbFinca.addItem(f);
        }

        cbEspecialidad.removeAllItems();
        cbEspecialidad.addItem(""); 
        List<String> especialidades = dao.obtenerEspecialidadesUnicas();
        for (String e : especialidades) {
            cbEspecialidad.addItem(e);
        }
    }

    private void configurarEventos() {
       
        cbTipoResponsable.addActionListener(e -> {
            String seleccion = cbTipoResponsable.getSelectedItem().toString();
            if ("Productor".equals(seleccion)) {
                cbFinca.setEnabled(true);
                cbFinca.setBackground(Color.WHITE);
                cbEspecialidad.setEnabled(false);
                cbEspecialidad.setBackground(Color.LIGHT_GRAY);
                cbEspecialidad.setSelectedIndex(0);
            } else {
                cbFinca.setEnabled(false);
                cbFinca.setBackground(Color.LIGHT_GRAY);
                cbFinca.setSelectedIndex(0);
                cbEspecialidad.setEnabled(true);
                cbEspecialidad.setBackground(Color.WHITE);
            }
        });

        btnAgregar.addActionListener(e -> {
            String id = txtIdentificacion.getText().trim();
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String tel = txtTelefono.getText().trim();
            
            String tipo = cbTipoResponsable.getSelectedItem().toString();
            if (id.isEmpty() || nombre.isEmpty() || correo.isEmpty() || tel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos básicos obrigatorios.");
                return;
            }
            Responsable responsable;

            if ("Productor".equals(tipo)) {
                String finca =  cbFinca.getSelectedItem() != null ? cbFinca.getSelectedItem().toString().trim() : "";
                if (finca.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Para un Productor, el campo 'Nombre Finca / Asociación' es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
                    cbFinca.requestFocus();
                    return;
                }
                responsable = new Productor(id, nombre, correo, tel, finca);
                
            } else { 
                String especialidad = cbEspecialidad.getSelectedItem() != null ? cbEspecialidad.getSelectedItem().toString().trim() : "";
                if (especialidad.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Para un Técnico Agrícola, el campo 'Especialidad Técnica' es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                responsable = new TecnicoAgricola(id, nombre, correo, tel, especialidad);
            }

            if (dao.insertar(responsable)) {
                JOptionPane.showMessageDialog(this, "Responsable registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosTabla();
                limpiarCampos();
                cargarCombosDesdeBD();
            } else {
                JOptionPane.showMessageDialog(this, "Error de base de datos al guardar el responsable.\nVerifique que la Identificación no esté duplicada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaResponsables.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione el Responsable que desea eliminar de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String identificacion  = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el Responsable con código '" + identificacion  + "'?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (dao.eliminar(identificacion )) {
                    JOptionPane.showMessageDialog(this, "Responsable eliminado correctamente de la Base de Datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosTabla();
                    limpiarCampos();
                    cargarCombosDesdeBD(); 
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede eliminar el Responsable.\nVerifique que no esté asignado a ninguna labor agrícola activa.", "Restricción de Integridad", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        tablaResponsables.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaResponsables.getSelectedRow() != -1) {
                int fila = tablaResponsables.getSelectedRow();
                txtIdentificacion.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtCorreo.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
                
             String tipo = obtenerValorCelda(fila, 4);
                if (tipo.toLowerCase().contains("productor")) {
                    cbTipoResponsable.setSelectedIndex(0);
                    setSeleccionComboSegura(cbFinca, obtenerValorCelda(fila, 5));
                    cbEspecialidad.setSelectedIndex(0);
                } else {
                    cbTipoResponsable.setSelectedIndex(1);
                    cbFinca.setSelectedIndex(0);
                    setSeleccionComboSegura(cbEspecialidad, obtenerValorCelda(fila, 6));
                }
            }
        });
        
    }
        private void setSeleccionComboSegura(JComboBox<String> combo, String valor) {
        if (valor.isEmpty() || "-".equals(valor)) {
            combo.setSelectedIndex(0);
            return;
        }
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).equalsIgnoreCase(valor)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        combo.addItem(valor);
        combo.setSelectedItem(valor);
    }
        private String obtenerValorCelda(int fila, int columna) {
        Object valor = modeloTabla.getValueAt(fila, columna);
        return (valor == null || "-".equals(valor.toString())) ? "" : valor.toString();
    }    

    private void limpiarCampos() {
        txtIdentificacion.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        cbFinca.setSelectedIndex(0);
        cbEspecialidad.setSelectedIndex(0);
        cbTipoResponsable.setSelectedIndex(0);
    }
}    

