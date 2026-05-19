/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author moral
 */
public abstract class Cultivo  {

    private String codigo;
    private String nombre;
    private String variedad;
    private String fechaSiembra; 
    private String tipoCultivo;


    public Cultivo() {
    }

    public Cultivo(String codigo, String nombre, String variedad, String fechaSiembra, 
     String tipoCultivo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.variedad = variedad;
        this.fechaSiembra = fechaSiembra;
        this.tipoCultivo = tipoCultivo;
    }

    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public String getFechaSiembra() {
        return fechaSiembra;
    }

    public void setFechaSiembra(String fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }
        public String getTipoCultivo() {
        return tipoCultivo;
    }

    public void setTipoCultivo(String tipoCultivo) {
        this.tipoCultivo = tipoCultivo;
    }
    
    public String obtenerDescripcion() {
        return "cultivo{" + "codigo=" + codigo + ", nombre=" + nombre + 
               ", variedad=" + variedad + ", fechaSiembra=" + fechaSiembra + ", tipoCultivo=" + tipoCultivo + '}';
    }
}

