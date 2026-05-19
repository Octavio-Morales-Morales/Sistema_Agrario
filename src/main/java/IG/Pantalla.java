/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package IG;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author moral
 */
public class Pantalla extends javax.swing.JFrame {
     private JPanel pnlMenuLateral;
     private JPanel pnlContenidoPrincipal;
     private CardLayout tarjetas;
     
    public Pantalla() {
       setTitle("Sistema de Gestión Agraria");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(950, 600);
       setLocationRelativeTo(null);
       setLayout(new BorderLayout());
       
       initComponentsAgraria();
    }
    
    private void initComponentsAgraria() {
     pnlMenuLateral = new JPanel();
     pnlMenuLateral.setBackground(new Color(45, 52, 71));
     pnlMenuLateral.setPreferredSize(new Dimension(220, 0));
     pnlMenuLateral.setLayout(new GridLayout(10, 1, 0, 5)); 
      
     JButton btnParcelas = crearBotonMenu("Parcelas");
     JButton btnCultivos = crearBotonMenu("Cultivos");
     JButton btnResponsables = crearBotonMenu("Responsables");
     JButton btnLabores = crearBotonMenu("Nueva Labor");
     JButton btnListaLabores = crearBotonMenu("Ver Labores");
     JButton btnSalir = crearBotonMenu("Exit");
     btnSalir.setBackground(new Color(192, 57, 43));
      
     JLabel lblTitulo = new JLabel("GESTIÓN AGRARIA", JLabel.CENTER);
     lblTitulo.setForeground(Color.WHITE);
     lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
     pnlMenuLateral.add(lblTitulo);
     
     pnlMenuLateral.add(btnParcelas);
     pnlMenuLateral.add(btnCultivos);
     pnlMenuLateral.add(btnResponsables);
     pnlMenuLateral.add(btnLabores);
     pnlMenuLateral.add(btnListaLabores);
     pnlMenuLateral.add(btnSalir);
       
     tarjetas = new CardLayout();
     pnlContenidoPrincipal = new JPanel(tarjetas);
     pnlContenidoPrincipal.setBackground(Color.WHITE);
     
     pnlContenidoPrincipal.add(crearPanel("Bienvenido al Sistema Agrario", Color.LIGHT_GRAY), "Default");
     pnlContenidoPrincipal.add(new PanelParcelas(), "Parcelas");
     pnlContenidoPrincipal.add(new PanelCultivo(), "Cultivos");
     pnlContenidoPrincipal.add(new PanelResponsable(), "Responsables");
     pnlContenidoPrincipal.add(new PanelLaboresAgricolas(), "Labores");
     pnlContenidoPrincipal.add(new PanelGenerarInforme(), "Generar informe");
     
     tarjetas.show(pnlContenidoPrincipal, "Default");
     
     btnParcelas.addActionListener(e -> tarjetas.show(pnlContenidoPrincipal, "Parcelas"));
     btnCultivos.addActionListener(e -> tarjetas.show(pnlContenidoPrincipal, "Cultivos"));
     btnResponsables.addActionListener(e -> tarjetas.show(pnlContenidoPrincipal, "Responsables"));
     btnLabores.addActionListener(e -> tarjetas.show(pnlContenidoPrincipal, "Labores"));
     btnListaLabores.addActionListener(e -> tarjetas.show(pnlContenidoPrincipal, "Generar informe"));
     
     btnSalir.addActionListener(e -> { 
         int respuesta = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de que deseas salir de la aplicación?", 
                "Salir", JOptionPane.YES_NO_OPTION);
         if (respuesta == JOptionPane.YES_OPTION) {
             System.exit(0);
         }
     });
     
     add(pnlMenuLateral, BorderLayout.WEST);
     add(pnlContenidoPrincipal, BorderLayout.CENTER);
    }
    
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(60, 70, 92));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.PLAIN, 13));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return boton;
    }
    
    private JPanel crearPanel(String titulo, Color color) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(color);
        JLabel label = new JLabel("Estás en: " + titulo);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label);
        return panel;
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
