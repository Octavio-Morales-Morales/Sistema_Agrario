/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Objetos.Cultivo;
import Objetos.CultivoAnual;
import Objetos.CultivoPerenne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author moral
 */
public class CultivosDAO {

    public boolean insertar(Cultivo cultivo) {
        String sql = "INSERT INTO AA_CULTIVOS (CODIGO, NOMBRE, VARIEDAD, FECHA_SIEMBRA, TIPO_CULTIVO, DURACION_CICLO_DIAS, ANIOS_PRODUCCION) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cultivo.getCodigo());
            ps.setString(2, cultivo.getNombre());
            ps.setString(3, cultivo.getVariedad());
            ps.setString(4, cultivo.getFechaSiembra());
            ps.setString(5, cultivo.getTipoCultivo());
            
            if (cultivo instanceof CultivoAnual) {
                ps.setInt(6, ((CultivoAnual) cultivo).getDuracionCicloDias());
                ps.setNull(7, java.sql.Types.NUMERIC);
            } else if (cultivo instanceof CultivoPerenne) {
                ps.setNull(6, java.sql.Types.NUMERIC);
                ps.setInt(7, ((CultivoPerenne) cultivo).getAniosProduccion());
            }
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cultivo> seleccionar() {
        List<Cultivo> lista = new ArrayList<>();
        String sql = "SELECT CODIGO, NOMBRE, VARIEDAD, FECHA_SIEMBRA, TIPO_CULTIVO, DURACION_CICLO_DIAS, ANIOS_PRODUCCION FROM AA_CULTIVOS";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String tipo = rs.getString("TIPO_CULTIVO");
                String cod = rs.getString("CODIGO");
                String nom = rs.getString("NOMBRE");
                String var = rs.getString("VARIEDAD");
                String fecha = rs.getString("FECHA_SIEMBRA");
                
                if ("Anual".equalsIgnoreCase(tipo)) {
                    CultivoAnual ca = new CultivoAnual();
                    ca.setCodigo(cod);
                    ca.setNombre(nom);
                    ca.setVariedad(var);
                    ca.setFechaSiembra(fecha);
                    ca.setTipoCultivo(tipo);
                    int ciclo = rs.getInt("DURACION_CICLO_DIAS");
                    ca.setDuracionCicloDias(rs.wasNull() ? 0 : ciclo);
                    lista.add(ca);
                } else if ("Perenne".equalsIgnoreCase(tipo)) {
                    CultivoPerenne cp = new CultivoPerenne();
                    cp.setCodigo(cod);
                    cp.setNombre(nom);
                    cp.setVariedad(var);
                    cp.setFechaSiembra(fecha);
                    cp.setTipoCultivo(tipo);
                    int anios = rs.getInt("ANIOS_PRODUCCION");
                    cp.setAniosProduccion(rs.wasNull() ? 0 : anios);
                    lista.add(cp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
        public boolean eliminar(String codigo) {
        String sql = "DELETE FROM AA_CULTIVOS WHERE CODIGO = ?";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            if (e.getErrorCode() == 2292 || e.getMessage().contains("constraint")) {
                System.err.println("No se puede eliminar: El cultivo está asignado a una labor agrícola.");
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
