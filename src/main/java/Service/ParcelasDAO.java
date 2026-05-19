/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Objetos.Parcela;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author moral
 */
public class ParcelasDAO {
  public boolean insertar(Parcela parcela) {
        String sql = "INSERT INTO AA_PARCELAS (CODIGO, NOMBRE, UBICACION, AREA, TIPO_SUELO, ESTADO) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, parcela.getCodigo());
            ps.setString(2, parcela.getNombre());
            ps.setString(3, parcela.getUbicacion());
            ps.setDouble(4, parcela.getArea());
            ps.setString(5, parcela.getTipoSuelo());
            ps.setString(6, parcela.getEstado());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Parcela> seleccionar() {
        List<Parcela> lista = new ArrayList<>();
        String sql = "SELECT CODIGO, NOMBRE, UBICACION, AREA, TIPO_SUELO, ESTADO FROM AA_PARCELAS";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Parcela parcela = new Parcela();
                parcela.setCodigo(rs.getString("CODIGO"));
                parcela.setNombre(rs.getString("NOMBRE"));
                parcela.setUbicacion(rs.getString("UBICACION"));
                parcela.setArea(rs.getDouble("AREA"));
                parcela.setTipoSuelo(rs.getString("TIPO_SUELO"));
                parcela.setEstado(rs.getString("ESTADO"));
                lista.add(parcela);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
        public boolean eliminar(String codigo) {
        String sql = "DELETE FROM AA_PARCELAS WHERE CODIGO = ?";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            if (e.getErrorCode() == 2292 || e.getMessage().contains("constraint")) {
                System.err.println("No se puede eliminar: La parcela está vinculada a un historial de labores.");
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