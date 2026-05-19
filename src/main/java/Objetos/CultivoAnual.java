/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author moral
 */
public class CultivoAnual extends Cultivo{
    private Integer duracionCicloDias;
    public CultivoAnual(){
      super();
    }
    public CultivoAnual(String codigo, String nombre, String variedad, String fechaSiembra, 
    String tipoCultivo, Integer duracionCicloDias) {
        
        super(codigo, nombre, variedad, null, "Anual");
        this.duracionCicloDias = duracionCicloDias;
        
    }
    public Integer getDuracionCicloDias() {
        return duracionCicloDias;
    }

    public void setDuracionCicloDias(Integer duracionCicloDias) {
        this.duracionCicloDias = duracionCicloDias;
    }

    @Override
    public String toString() {
        return super.toString() + " -> CultivoAnual{" + "duracionCicloDias=" + duracionCicloDias + '}';
    }
}
