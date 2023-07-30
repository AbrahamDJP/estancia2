/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.vista_categorias_productos;
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
public class sql_categorias extends conexionbd {

    public boolean cargar(vista_categorias_productos view) {

        String campo = view.txtBuscar.getText();
        String where = "";

        if (!"".equals(campo)) {
            where = "WHERE categoria = '" + campo + "'";
        }

        try {

            DefaultTableModel model = new DefaultTableModel();
            view.jTproducto.setModel(model);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = getConexion();
            String sql = "SELECT * FROM tabla_categorias " + where;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantCol = rsMD.getColumnCount();

            model.addColumn("ID Categoria");
            model.addColumn("Categoria");

            int[] anchos = {100, 300};
            for (int x = 0; x < cantCol; x++) {
                view.jTproducto.getColumnModel().getColumn(x).setPreferredWidth(anchos[x]);
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

    public boolean registrar(categorias_productos cat) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO tabla_categorias (categoria) VALUES (?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cat.getCategoria());
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean editar(categorias_productos cat, String idcat, String newname) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        vista_categorias_productos view = new vista_categorias_productos();
        String sql = "UPDATE tabla_categorias SET categoria='"+newname+"' WHERE id_categoria_producto='"+idcat+"'";
        try {
            ps = con.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean eliminar(categorias_productos cat, String idcat) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        vista_categorias_productos view = new vista_categorias_productos();
        String sql = "DELETE FROM tabla_categorias WHERE id_categoria_producto='"+idcat+"'";
        try {
            ps = con.prepareStatement(sql);
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int categoriaExistente(String categoria) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT count(categoria) FROM tabla_categorias WHERE categoria = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, categoria);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 1;

        } catch (SQLException ex) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }

    }

}
