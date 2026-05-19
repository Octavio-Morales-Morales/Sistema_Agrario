/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Objetos.LaborAgricola;
import Objetos.Parcela;
import Objetos.Cultivo;
import Objetos.CultivoAnual;
import Objetos.CultivoPerenne;
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
public class LaboresAgricolasDAO {
    
    public boolean insertar(LaborAgricola labor) {
        String sql = "INSERT INTO AA_LABORES_AGRICOLAS (CODIGO, CODIGO_PARCELA, CODIGO_CULTIVO, IDENTIFICACION_RESPONSABLE, FECHA_REALIZACION, TIPO_LABOR, DESCRIPCION, COSTO_ESTIMADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, labor.getCodigo());
            ps.setString(2, labor.getParcela().getCodigo());
            ps.setString(3, labor.getCultivo().getCodigo());
            ps.setString(4, labor.getResponsable().getIdentificacion());
            ps.setString(5, labor.getFechaRealizacion());
            ps.setString(6, labor.getTipoLabor());
            ps.setString(7, labor.getDescripcion());
            ps.setDouble(8, labor.getCostoEstimado());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<LaborAgricola> seleccionar() {
        List<LaborAgricola> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT l.CODIGO, l.FECHA_REALIZACION, l.TIPO_LABOR, l.DESCRIPCION, l.COSTO_ESTIMADO, ");
        sql.append("       p.CODIGO AS P_COD, p.NOMBRE AS P_NOM, p.UBICACION, p.AREA, p.TIPO_SUELO, p.ESTADO, c.FECHA_SIEMBRA,");
        sql.append("       c.CODIGO AS C_COD, c.NOMBRE AS C_NOM, c.VARIEDAD, c.TIPO_CULTIVO, c.DURACION_CICLO_DIAS, c.ANIOS_PRODUCCION, ");
        sql.append("       r.IDENTIFICACION, r.NOMBRE_COMPLETO, r.CORREO, r.TELEFONO, r.TIPO_RESPONSABLE, r.NOMBRE_FINCA_ASOCIACION, r.ESPECIALIDAD_TECNICA ");
        sql.append("FROM AA_LABORES_AGRICOLAS l ");
        sql.append("JOIN AA_PARCELAS p ON l.CODIGO_PARCELA = p.CODIGO ");
        sql.append("JOIN AA_CULTIVOS c ON l.CODIGO_CULTIVO = c.CODIGO ");
        sql.append("JOIN AA_RESPONSABLES r ON l.IDENTIFICACION_RESPONSABLE = r.IDENTIFICACION");

        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                LaborAgricola labor = new LaborAgricola();
                labor.setCodigo(rs.getString("CODIGO"));
                labor.setFechaRealizacion(rs.getString("FECHA_REALIZACION"));
                labor.setTipoLabor(rs.getString("TIPO_LABOR"));
                labor.setDescripcion(rs.getString("DESCRIPCION"));
                
                double costo = rs.getDouble("COSTO_ESTIMADO");
                if (!rs.wasNull()) {
                    labor.setCostoEstimado(costo);
                }
                
                Parcela parcela = new Parcela();
                parcela.setCodigo(rs.getString("P_COD"));
                parcela.setNombre(rs.getString("P_NOM"));
                parcela.setUbicacion(rs.getString("UBICACION"));
                parcela.setArea(rs.getDouble("AREA"));
                parcela.setTipoSuelo(rs.getString("TIPO_SUELO"));
                parcela.setEstado(rs.getString("ESTADO"));
                labor.setParcela(parcela);
                
                Cultivo cultivo = null;
                String tipoCultivo = rs.getString("TIPO_CULTIVO");
                if ("Anual".equalsIgnoreCase(tipoCultivo)) {
                    CultivoAnual ca = new CultivoAnual();
                    ca.setCodigo(rs.getString("C_COD"));
                    ca.setNombre(rs.getString("C_NOM"));
                    ca.setVariedad(rs.getString("VARIEDAD"));
                    ca.setFechaSiembra(rs.getString("FECHA_SIEMBRA"));
                    ca.setTipoCultivo(tipoCultivo);
                    ca.setDuracionCicloDias(rs.getInt("DURACION_CICLO_DIAS"));
                    cultivo = ca;
                } else if ("Perenne".equalsIgnoreCase(tipoCultivo)) {
                    CultivoPerenne cp = new CultivoPerenne();
                    cp.setCodigo(rs.getString("C_COD"));
                    cp.setNombre(rs.getString("C_NOM"));
                    cp.setVariedad(rs.getString("VARIEDAD"));
                    cp.setFechaSiembra(rs.getString("FECHA_SIEMBRA"));
                    cp.setTipoCultivo(tipoCultivo);
                    cp.setAniosProduccion(rs.getInt("ANIOS_PRODUCCION"));
                    cultivo = cp;
                }
                labor.setCultivo(cultivo);
                
                String tipoResp = rs.getString("TIPO_RESPONSABLE");
                String id = rs.getString("IDENTIFICACION");
                String nom = rs.getString("NOMBRE_COMPLETO");
                String corr = rs.getString("CORREO");
                String tel = rs.getString("TELEFONO");
                Responsable responsable = null;
                
                if (tipoResp.contains("productor")) {
                    responsable = new Productor(id, nom, corr, tel, rs.getString("NOMBRE_FINCA_ASOCIACION"));
                } else if (tipoResp.contains("tecnico")) {
                    responsable = new TecnicoAgricola(id, nom, corr, tel, rs.getString("ESPECIALIDAD_TECNICA"));
                }
                labor.setResponsable(responsable);
                
                lista.add(labor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM AA_LABORES_AGRICOLAS WHERE CODIGO = ?";
        try (Connection con = ConexionBD.Conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
