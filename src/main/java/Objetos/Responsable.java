/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author moral
 */
public class Responsable {
 protected String identificacion;
    protected String nombreCompleto;
    protected String correo;
    protected String telefono;
    protected String tipoResponsable;

    public Responsable(String identificacion, String nombreCompleto, String correo, String telefono, String tipoResponsable) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.tipoResponsable = tipoResponsable;
    }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getTipoResponsable() { return tipoResponsable; }
    public void setTipoResponsable(String tipoResponsable) { this.tipoResponsable = tipoResponsable; }
}
