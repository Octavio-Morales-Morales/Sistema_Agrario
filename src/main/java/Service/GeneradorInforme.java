/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Objetos.LaborAgricola;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author moral
 */
public class GeneradorInforme {
  public boolean guardarInformePlano(File archivoDestino, List<LaborAgricola> listaLabores) {
        if (listaLabores == null || listaLabores.isEmpty()) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoDestino))) {
            writer.write("INFORME DE LABORES AGRÍCOLAS");
            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();

            for (LaborAgricola labor : listaLabores) {
                String nombreParcela = (labor.getParcela() != null) ? labor.getParcela().getNombre() : "N/A";
                String nombreCultivo = (labor.getCultivo() != null) ? labor.getCultivo().getNombre() : "N/A";
                String nombreResponsable = (labor.getResponsable() != null) ? labor.getResponsable().getNombreCompleto() : "N/A";
                double costo = labor.getCostoEstimado();

                writer.write("Código: " + labor.getCodigo());
                writer.newLine();
                writer.write("Fecha: " + labor.getFechaRealizacion());
                writer.newLine();
                writer.write("Parcela: " + nombreParcela);
                writer.newLine();
                writer.write("Cultivo: " + nombreCultivo);
                writer.newLine();
                writer.write("Responsable: " + nombreResponsable);
                writer.newLine();
                writer.write("Tipo de labor: " + labor.getTipoLabor());
                writer.newLine();
                writer.write("Costo estimado: " + String.format("%.2f", costo));
                writer.newLine();
                writer.write("----------------------------------------");
                writer.newLine();
            }
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
