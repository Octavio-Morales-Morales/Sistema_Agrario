/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Objetos.Responsable;
import Objetos.Productor;
import Objetos.TecnicoAgricola;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author moral
 */
public class ResponsablesDAO {
    public boolean insertar(Responsable responsable) {
        String sql = "INSERT INTO AA_RESPONSABLES (IDENTIFICACION, NOMBRE_COMPLETO, CORREO, TELEFONO, TIPO_RESPONSABLE, NOMBRE_FINCA_ASOCIACION, ESPECIALIDAD_TECNICA) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, responsable.getIdentificacion());
            ps.setString(2, responsable.getNombreCompleto());
            ps.setString(3, responsable.getCorreo());
            ps.setString(4, responsable.getTelefono());
            
            String tipo = responsable.getTipoResponsable();
            if (tipo != null && tipo.toLowerCase().contains("tecnico")) {
                ps.setString(5, "Tecnico"); 
            } else {
                ps.setString(5, "Productor");
            }
            
            if (responsable instanceof Productor) {
                ps.setString(6, ((Productor) responsable).getNombreFincaAsociacion());
                ps.setNull(7, java.sql.Types.VARCHAR);
            } else if (responsable instanceof TecnicoAgricola) {
                ps.setNull(6, java.sql.Types.VARCHAR);
                ps.setString(7, ((TecnicoAgricola) responsable).getEspecialidadTecnica());
            }
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Responsable> seleccionar() {
        List<Responsable> lista = new ArrayList<>();
        String sql = "SELECT IDENTIFICACION, NOMBRE_COMPLETO, CORREO, TELEFONO, TIPO_RESPONSABLE, NOMBRE_FINCA_ASOCIACION, ESPECIALIDAD_TECNICA FROM AA_RESPONSABLES";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                
                String tipoRaw = rs.getString("TIPO_RESPONSABLE");
                String tipo = (tipoRaw != null) ? tipoRaw.trim() : "";
                String id = rs.getString("IDENTIFICACION");
                String nombre = rs.getString("NOMBRE_COMPLETO");
                String correo = rs.getString("CORREO");
                String telf = rs.getString("TELEFONO");
                
                if (tipo.toLowerCase().contains("productor")) {
                    String finca = rs.getString("NOMBRE_FINCA_ASOCIACION");
                    lista.add(new Productor(id, nombre, correo, telf, finca));
                } else if (tipo.toLowerCase().contains("tecnico")) {
                    String especialidad = rs.getString("ESPECIALIDAD_TECNICA");
                    lista.add(new TecnicoAgricola(id, nombre, correo, telf, especialidad));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
   public List<String> obtenerFincasUnicas() {
        List<String> fincas = new ArrayList<>();
        String sql = "SELECT DISTINCT NOMBRE_FINCA_ASOCIACION FROM AA_RESPONSABLES WHERE NOMBRE_FINCA_ASOCIACION IS NOT NULL";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                fincas.add(rs.getString("NOMBRE_FINCA_ASOCIACION").trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fincas;
    }
    public List<String> obtenerEspecialidadesUnicas() {
        List<String> especialidades = new ArrayList<>();
        String sql = "SELECT DISTINCT ESPECIALIDAD_TECNICA FROM AA_RESPONSABLES WHERE ESPECIALIDAD_TECNICA IS NOT NULL";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                especialidades.add(rs.getString("ESPECIALIDAD_TECNICA").trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return especialidades;
    }
        public boolean eliminar(String identificacion) {
        String sql = "DELETE FROM AA_RESPONSABLES WHERE IDENTIFICACION = ?";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, identificacion);
            return ps.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            if (e.getErrorCode() == 2292 || e.getMessage().contains("constraint")) {
                System.err.println("No se puede eliminar: El responsable tiene labores asignadas en el historial.");
            } else {
                e.printStackTrace();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}