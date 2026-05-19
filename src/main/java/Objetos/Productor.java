/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author moral
 */
public class Productor extends Responsable {
    private String nombreFincaAsociacion;

    public Productor(String identificacion, String nombreCompleto, String correo, String telefono, String nombreFincaAsociacion) {
        super(identificacion, nombreCompleto, correo, telefono, "Productor");
        this.nombreFincaAsociacion = nombreFincaAsociacion;
    }

    public String getNombreFincaAsociacion() { return nombreFincaAsociacion; }
    public void setNombreFincaAsociacion(String nombreFincaAsociacion) { this.nombreFincaAsociacion = nombreFincaAsociacion; }
}
