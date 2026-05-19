/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author moral
 */
public class CultivoPerenne extends Cultivo {
  private Integer aniosProduccion;  
  public CultivoPerenne() {
     super();
    }
      public CultivoPerenne(String codigo, String nombre, String variedad, String fechaSiembra, 
           String tipoCultivo, Integer aniosProduccion) {
        super(codigo, nombre, variedad, null, "Perenne");
        this.aniosProduccion = aniosProduccion;
    }
          public Integer getAniosProduccion() {
        return aniosProduccion;
    }

    public void setAniosProduccion(Integer aniosProduccion) {
        this.aniosProduccion = aniosProduccion;
    }
    @Override
    public String toString() {
        return super.toString() + " -> CultivoPerenne{" + "aniosProduccion=" + aniosProduccion + '}';
    }
}
