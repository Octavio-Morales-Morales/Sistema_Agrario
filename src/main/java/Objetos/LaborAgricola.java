/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author moral
 */
public class LaborAgricola {
private String codigo;
private Parcela parcela;
private Cultivo cultivo;
private Responsable responsable;
private String fechaRealizacion;
private String tipoLabor;
private String descripcion;
private Double costoEstimado;
 public LaborAgricola() {
    }
 public LaborAgricola(String codigo, Parcela parcela, Cultivo cultivo, Responsable responsable, 
        String fechaRealizacion, String tipoLabor, String descripcion, Double costoEstimado) {
        this.codigo = codigo;
        this.parcela = parcela;
        this.cultivo = cultivo;
        this.responsable = responsable;
        this.fechaRealizacion = fechaRealizacion;
        this.tipoLabor = tipoLabor;
        this.descripcion = descripcion;
        this.costoEstimado = costoEstimado;
    }
 public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public Cultivo getCultivo() {
        return cultivo;
    }

    public void setCultivo(Cultivo cultivo) {
        this.cultivo = cultivo;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public String getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getTipoLabor() {
        return tipoLabor;
    }

    public void setTipoLabor(String tipoLabor) {
        this.tipoLabor = tipoLabor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(Double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    
    @Override
    public String toString() {
        return "LaborAgricola{" +
                "codigo='" + codigo + '\'' +
                ", parcela=" + (parcela != null ? parcela.getCodigo() : "null") +
                ", cultivo=" + (cultivo != null ? cultivo.toString() : "null") +
                ", responsable=" + (responsable != null ? responsable.getIdentificacion() : "null") +
                ", fechaRealizacion='" + fechaRealizacion + '\'' +
                ", tipoLabor='" + tipoLabor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costoEstimado=" + costoEstimado +
                '}';
    }
}
 

