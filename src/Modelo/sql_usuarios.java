/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.vista_usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ettkt
 */
public class sql_usuarios extends conexionbd {

    public boolean cargar(vista_usuarios view) {

        String campo = view.txtBuscar.getText();
        String where = "";

        if (!"".equals(campo)) {
            where = "WHERE tu.usuario = '" + campo + "'";
        }

        try {

            DefaultTableModel model = new DefaultTableModel();
            view.jTusuarios.setModel(model);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = getConexion();
            String sql = "SELECT tu.id_usuario, tu.usuario, tu.nombre_usuario, "
                    + "tu.ap_usuario, tu.am_usuario, tu.correo_electronico, "
                    + "tu.sexo_usuario, tu.direccion_usuario, tp.tipo_usuario, "
                    + "ts.status_usuario "
                    + "FROM tabla_usuarios AS tu "
                    + "INNER JOIN tabla_tipo_usuario as tp ON tu.fk_tipo_usuario = tp.id_tipo_usuario "
                    + "INNER JOIN tabla_status_usuario as ts ON tu.fk_status = ts.id_status_usuario " + where;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantCol = rsMD.getColumnCount();

            model.addColumn("ID");
            model.addColumn("Usuario");
            //model.addColumn("Contrasenia");
            model.addColumn("Nombre");
            model.addColumn("Apellido Peterno");
            model.addColumn("Apellido Materno");
            model.addColumn("Correo Electronico");
            model.addColumn("Sexo");
            model.addColumn("Direccion");
            model.addColumn("Tipo");
            model.addColumn("Status");

            int[] anchos = {20, 50, 80, 80, 80, 100, 50, 80, 50, 30};
            for (int x = 0; x < cantCol; x++) {
                view.jTusuarios.getColumnModel().getColumn(x).setPreferredWidth(anchos[x]);
            }

            while (rs.next()) {
                Object[] filas = new Object[cantCol];

                for (int i = 0; i < cantCol; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                model.addRow(filas);

            }

            return true;
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
        return false;

    }

    public boolean cargarTiposUsuarios(vista_usuarios view) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        conexionbd conn = new conexionbd();
        Connection con = conn.getConexion();

        try {
            String sql = "SELECT * FROM tabla_tipo_usuario";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            view.cbxTiposUsuarios.addItem("Seleccione tipo de usuario");

            while (rs.next()) {
                view.cbxTiposUsuarios.addItem(rs.getString("tipo_usuario"));
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return false;
    }

    public boolean cargarStatusUsuarios(vista_usuarios view) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        conexionbd conn = new conexionbd();
        Connection con = conn.getConexion();

        try {
            String sql = "SELECT * FROM tabla_status_usuario";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            //view.cbxTiposUsuarios.addItem("Seleccione tipo de usuario");
            while (rs.next()) {
                view.cbxStatus.addItem(rs.getString("status_usuario"));
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return false;
    }

    public int usuarioExistente(String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT count(usuario) FROM tabla_usuarios WHERE usuario = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 1;

        } catch (SQLException ex) {
            return 1;
        }

    }

    public boolean registrar(usuarios usr) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO tabla_usuarios"
                + "(usuario, contrasenia, nombre_usuario, ap_usuario, am_usuario, correo_electronico, sexo_usuario, direccion_usuario, fk_tipo_usuario, fk_status) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, usr.getUsuario());
            ps.setString(2, usr.getContrasenia());
            ps.setString(3, usr.getNombre_usuario());
            ps.setString(4, usr.getAp_usuario());
            ps.setString(5, usr.getAm_usuario());
            ps.setString(6, usr.getCorreo_electronico());
            ps.setString(7, usr.getSexo());
            ps.setString(8, usr.getDireccion());
            ps.setInt(9, usr.getTipo());
            ps.setInt(10, usr.getStatus());

            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    public boolean editar(usuarios usr) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE tabla_usuarios SET usuario=?, contrasenia=?, nombre_usuario=?, "
                + "ap_usuario=?, am_usuario=?, correo_electronico=?, sexo_usuario=?, "
                + "direccion_usuario=?, fk_tipo_usuario=?, fk_status=? WHERE id_usuario=? ";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, usr.getUsuario());
            ps.setString(2, usr.getContrasenia());
            ps.setString(3, usr.getNombre_usuario());
            ps.setString(4, usr.getAp_usuario());
            ps.setString(5, usr.getAm_usuario());
            ps.setString(6, usr.getCorreo_electronico());
            ps.setString(7, usr.getSexo());
            ps.setString(8, usr.getDireccion());
            ps.setInt(9, usr.getTipo());
            ps.setInt(10, usr.getStatus());
            ps.setInt(11, usr.getId_usuario());

            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    public boolean eliminar(usuarios usr) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        //String sql = "DELETE FROM tabla_categorias WHERE id_categoria_producto='"+idcat+"'";
        String sql = "DELETE FROM tabla_usuarios WHERE id_usuario=? ";

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, usr.getId_usuario());

            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean cargaralClic(String idU, vista_usuarios view) {
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexionbd conn = new conexionbd();
            Connection con = conn.getConexion();
            
            ps = con.prepareStatement("SELECT tu.id_usuario, tu.usuario, tu.contrasenia, tu.nombre_usuario, "
                    + "tu.ap_usuario, tu.am_usuario, tu.correo_electronico, "
                    + "tu.sexo_usuario, tu.direccion_usuario, tp.tipo_usuario, "
                    + "ts.status_usuario "
                    + "FROM tabla_usuarios AS tu "
                    + "INNER JOIN tabla_tipo_usuario as tp ON tu.fk_tipo_usuario = tp.id_tipo_usuario "
                    + "INNER JOIN tabla_status_usuario as ts ON tu.fk_status = ts.id_status_usuario "
                    + "WHERE tu.id_usuario=?");
            ps.setString(1, idU);
            rs = ps.executeQuery();
            
            while(rs.next()){
                view.lvlidUsuario.setText(rs.getString("tu.id_usuario"));
                view.txtUsuarioU.setText(rs.getString("tu.usuario"));
                view.txtContraseniaU.setText(rs.getString("tu.contrasenia"));
                view.txtNombreU.setText(rs.getString("tu.nombre_usuario"));
                view.txtApU.setText(rs.getString("tu.ap_usuario"));
                view.txtAmU.setText(rs.getString("tu.am_usuario"));
                view.txtCorreoU.setText(rs.getString("tu.correo_electronico"));
                view.txtSexoU.setText(rs.getString("tu.sexo_usuario"));
                view.txtDireccionU.setText(rs.getString("tu.direccion_usuario"));
                view.cbxTiposUsuarios.setSelectedItem(rs.getString("tp.tipo_usuario"));
                view.cbxStatus.setSelectedItem(rs.getString("ts.status_usuario"));
                
            }

        } catch (Exception e) {
            System.out.println("No entra al try");
        }

        return false;

    }
}
