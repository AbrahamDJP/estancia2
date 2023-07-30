/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.vista_clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ettkt
 */
public class sql_clientes extends conexionbd {

    PreparedStatement ps = null;
    ResultSet rs = null;
    conexionbd conn = new conexionbd();
    Connection con = getConexion();

    public boolean cargar(vista_clientes view) {
        
        String campo = view.txtBuscar.getText();
        String where = "";

        if (!"".equals(campo)) {
            where = "WHERE nombre_cliente = '" + campo + "'";
        }
        
        try {

            DefaultTableModel model = new DefaultTableModel();
            view.jTclientes.setModel(model);
            String sql = "SELECT id_cliente, nombre_cliente, ap_cliente, am_cliente, "
                    + "sexo_cliente, direccion_cliente, telefono_cliente, correo_cliente "
                    + "FROM tabla_clientes " + where;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantCol = rsMD.getColumnCount();

            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Apellido Paterno");
            model.addColumn("Apellido Materno");
            model.addColumn("Sexo");
            model.addColumn("Direccion");
            model.addColumn("Telefono");
            model.addColumn("Correo");

            int[] anchos = {20, 100, 150, 150, 100, 200, 200, 200};
            for (int x = 0; x < cantCol; x++) {
                view.jTclientes.getColumnModel().getColumn(x).setPreferredWidth(anchos[x]);
            }
            while (rs.next()) {
                Object[] filas = new Object[cantCol];

                for (int i = 0; i < cantCol; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                model.addRow(filas);
            }
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public boolean guardar(clientes clnts) {
        String sql = "INSERT INTO tabla_clientes (nombre_cliente, ap_cliente, "
                + "am_cliente, sexo_cliente, direccion_cliente, telefono_cliente, correo_cliente) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, clnts.getNombre_cliente());
            ps.setString(2, clnts.getAp_cliente());
            ps.setString(3, clnts.getAm_cliente());
            ps.setString(4, clnts.getSexo_cliente());
            ps.setString(5, clnts.getDireccion_cliente());
            ps.setString(6, clnts.getTelefono());
            ps.setString(7, clnts.getCorreo());
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
//    public int obtenerID(String idCliente){
//        String sql = "SELECT id_cliente FROM tabla_clientes WHERE nombre_cliente = ?";
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setString(1, idCliente);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                int idClient = rs.getInt(1);
//                return idClient;
//            }
//        } catch (Exception e) {
//        }
//        return 0;
//    }
    
    public boolean editar(clientes clnts){
        String sql = "UPDATE tabla_clientes SET "
                + "nombre_cliente=?, ap_cliente=?, am_cliente=?, sexo_cliente=?, "
                + "direccion_cliente=?, telefono_cliente=?, correo_cliente=? "
                + "WHERE id_cliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, clnts.getNombre_cliente());
            ps.setString(2, clnts.getAp_cliente());
            ps.setString(3, clnts.getAm_cliente());
            ps.setString(4, clnts.getSexo_cliente());
            ps.setString(5, clnts.getDireccion_cliente());
            ps.setString(6, clnts.getTelefono());
            ps.setString(7, clnts.getCorreo());
            ps.setInt(8, clnts.getId_cliente());
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean eliminar (clientes clnts){
        String sql = "DELETE FROM tabla_clientes WHERE id_cliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, clnts.getId_cliente());
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
    
     */
}
