/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IG;
import Objetos.LaborAgricola;
import Objetos.Parcela;
import Objetos.Cultivo;
import Objetos.Responsable;
import Service.LaboresAgricolasDAO;
import Service.ParcelasDAO;
import Service.CultivosDAO;
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
public class PanelLaboresAgricolas extends JPanel {
    private JTextField txtCodigo, txtFecha, txtTipoLabor, txtDescripcion, txtCosto;
    private JComboBox<String> cbParcelas, cbCultivos, cbResponsables;
    private JButton btnAgregar, btnEliminar ;
    private JTable tablaLabores;
    private DefaultTableModel modeloTabla;
    private LaboresAgricolasDAO laboresDAO = new LaboresAgricolasDAO();
    private ParcelasDAO parcelasDAO = new ParcelasDAO();
    private CultivosDAO cultivosDAO = new CultivosDAO();
    private ResponsablesDAO responsablesDAO = new ResponsablesDAO();
    private List<Parcela> listaParcelas;
    private List<Cultivo> listaCultivos;
    private List<Responsable> listaResponsables;
    private List<LaborAgricola> listaLabores = new ArrayList<>();
    
      public PanelLaboresAgricolas() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initFormulario();
        initTabla();
        cargarDatosTabla();
        cargarCombos();
        configurarEventos();
    }
    
private void initFormulario() {
        JPanel pnlDatos = new JPanel(new GridLayout(16, 1, 5, 5));
        pnlDatos.setBorder(BorderFactory.createTitledBorder("Registrar Nueva Labor Agrícola"));

        txtCodigo = new JTextField();
        cbParcelas = new JComboBox<>();
        cbCultivos = new JComboBox<>();
        cbResponsables = new JComboBox<>();
        txtFecha = new JTextField();
        txtTipoLabor = new JTextField();
        txtDescripcion = new JTextField();
        txtCosto = new JTextField();

        pnlDatos.add(new JLabel("Código de la Labor:"));
        pnlDatos.add(txtCodigo);
        pnlDatos.add(new JLabel("Seleccionar Parcela:"));
        pnlDatos.add(cbParcelas);
        pnlDatos.add(new JLabel("Seleccionar Cultivo:"));
        pnlDatos.add(cbCultivos);
        pnlDatos.add(new JLabel("Seleccionar Responsable asignado:"));
        pnlDatos.add(cbResponsables);
        pnlDatos.add(new JLabel("Fecha de Realización (DD/MM/AAAA):"));
        pnlDatos.add(txtFecha);
        pnlDatos.add(new JLabel("Tipo de Labor (Siembra, Riego, Cosecha, etc.):"));
        pnlDatos.add(txtTipoLabor);
        pnlDatos.add(new JLabel("Descripción detallada:"));
        pnlDatos.add(txtDescripcion);
        pnlDatos.add(new JLabel("Costo Estimado (Opcional):"));
        pnlDatos.add(txtCosto);

        JPanel pnlBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        btnAgregar = new JButton("Registrar");
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(192, 57, 43));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        
        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnEliminar);
        
        JPanel pnlIzquierdo = new JPanel(new BorderLayout(10, 10));
        pnlIzquierdo.add(pnlDatos, BorderLayout.CENTER);
        pnlIzquierdo.add(pnlBotones, BorderLayout.SOUTH);
        pnlIzquierdo.setPreferredSize(new Dimension(280, 0));

        add(pnlIzquierdo, BorderLayout.WEST);
    }

    private void initTabla() {
        JPanel pnlCabeceraTabla = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Historial de Labores Agrícolas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));
        pnlCabeceraTabla.add(lblTitulo, BorderLayout.WEST);

        String[] columnas = {"Código", "Parcela", "Cultivo", "Responsable", "Fecha", "Tipo Labor", "Costo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
         
        tablaLabores = new JTable(modeloTabla);
        tablaLabores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JPanel pnlDerecho = new JPanel(new BorderLayout(5, 5));
        pnlDerecho.add(pnlCabeceraTabla, BorderLayout.NORTH);
        pnlDerecho.add(new JScrollPane(tablaLabores), BorderLayout.CENTER);

        add(pnlDerecho, BorderLayout.CENTER);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        listaLabores = laboresDAO.seleccionar();
        for (LaborAgricola l : listaLabores) {
            String nomParcela = (l.getParcela() != null) ? l.getParcela().getCodigo() : "N/A";
            String nomCultivo = (l.getCultivo() != null) ? l.getCultivo().getNombre() : "N/A";
            String nomResp = (l.getResponsable() != null) ? l.getResponsable().getNombreCompleto() : "N/A";
            
            modeloTabla.addRow(new Object[]{
                l.getCodigo(),
                nomParcela,
                nomCultivo,
                nomResp,
                l.getFechaRealizacion(),
                l.getTipoLabor(),
                l.getCostoEstimado()
            });
        }
    }

    private void cargarCombos() {
        cbParcelas.removeAllItems();
        listaParcelas = parcelasDAO.seleccionar();
        for (Parcela p : listaParcelas) {
            cbParcelas.addItem(p.getCodigo() + " - " + p.getNombre());
        }

        cbCultivos.removeAllItems();
        listaCultivos = cultivosDAO.seleccionar();
        for (Cultivo c : listaCultivos) {
            cbCultivos.addItem(c.getCodigo() + " - " + c.getNombre());
        }

        cbResponsables.removeAllItems();
        listaResponsables = responsablesDAO.seleccionar();
        for (Responsable r : listaResponsables) {
            cbResponsables.addItem(r.getIdentificacion() + " - " + r.getNombreCompleto());
        }
    }

    private void configurarEventos() {
        btnAgregar.addActionListener(e -> {
            if (txtCodigo.getText().trim().isEmpty() || cbParcelas.getSelectedIndex() == -1 || 
                cbCultivos.getSelectedIndex() == -1 || cbResponsables.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Por favor complete el Código y seleccione todas las relaciones.");
                return;
            }

            try {
                LaborAgricola labor = new LaborAgricola();
                labor.setCodigo(txtCodigo.getText().trim());
                labor.setFechaRealizacion(txtFecha.getText().trim());
                labor.setTipoLabor(txtTipoLabor.getText().trim());
                labor.setDescripcion(txtDescripcion.getText().trim());

                if (!txtCosto.getText().trim().isEmpty()) {
                    labor.setCostoEstimado(Double.parseDouble(txtCosto.getText().trim()));
                } else {
                    labor.setCostoEstimado(0.0);
                }

                Parcela parcelaSeleccionada = listaParcelas.get(cbParcelas.getSelectedIndex());
                labor.setParcela(parcelaSeleccionada);

                Cultivo cultivoSeleccionado = listaCultivos.get(cbCultivos.getSelectedIndex());
                labor.setCultivo(cultivoSeleccionado);

                Responsable responsableSeleccionado = listaResponsables.get(cbResponsables.getSelectedIndex());
                labor.setResponsable(responsableSeleccionado);

                if (laboresDAO.insertar(labor)) {
                    JOptionPane.showMessageDialog(this, "Labor Agrícola registrada exitosamente.");
                    cargarDatosTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al insertar la labor en la Base de Datos.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El campo Costo debe recibir un valor decimal válido.");
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int fila = tablaLabores.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Por favor seleccione la labor agrícola que desea eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String codigo = modeloTabla.getValueAt(fila, 0).toString();
            int resp = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar la labor '" + codigo + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                if (laboresDAO.eliminar(codigo)) {
                    JOptionPane.showMessageDialog(this, "Labor agrícola eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al intentar eliminar la labor de la Base de Datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        tablaLabores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaLabores.getSelectedRow() != -1) {
                         int fila = tablaLabores.getSelectedRow();
                String codigoSel = modeloTabla.getValueAt(fila, 0).toString();
                
                txtCodigo.setText(codigoSel);
                txtFecha.setText(modeloTabla.getValueAt(fila, 4).toString());
                txtTipoLabor.setText(modeloTabla.getValueAt(fila, 5).toString());
                txtCosto.setText(modeloTabla.getValueAt(fila, 6).toString());
                
                for (LaborAgricola la : listaLabores) {
                    if (la.getCodigo().equals(codigoSel)) {
                        txtDescripcion.setText(la.getDescripcion());
                        setSeleccionComboCodigo(cbParcelas, la.getParcela() != null ? la.getParcela().getCodigo() : "");
                        setSeleccionComboCodigo(cbCultivos, la.getCultivo() != null ? la.getCultivo().getCodigo() : "");
                        setSeleccionComboCodigo(cbResponsables, la.getResponsable() != null ? la.getResponsable().getIdentificacion() : "");
                        break;
                    }
                }
            }
        });
    }
        private void setSeleccionComboCodigo(JComboBox<String> combo, String codigo) {
        if (codigo == null || codigo.isEmpty()) return;
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).startsWith(codigo)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtFecha.setText("");
        txtTipoLabor.setText("");
        txtDescripcion.setText("");
        txtCosto.setText("");
        if (cbParcelas.getItemCount() > 0) cbParcelas.setSelectedIndex(0);
        if (cbCultivos.getItemCount() > 0) cbCultivos.setSelectedIndex(0);
        if (cbResponsables.getItemCount() > 0) cbResponsables.setSelectedIndex(0);
    }
}
