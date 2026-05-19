/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author moral
 */
public class TecnicoAgricola extends Responsable{
 private String especialidadTecnica;

    public TecnicoAgricola(String identificacion, String nombreCompleto, String correo, String telefono, String especialidadTecnica) {
        super(identificacion, nombreCompleto, correo, telefono, "Tecnico Agricola");
        this.especialidadTecnica = especialidadTecnica;
    }

    public String getEspecialidadTecnica() { return especialidadTecnica; }
    public void setEspecialidadTecnica(String especialidadTecnica) { this.especialidadTecnica = especialidadTecnica; }
}
