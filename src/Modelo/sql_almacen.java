/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.vista_almacen;
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
public class sql_almacen extends conexionbd {

    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private conexionbd conn = new conexionbd();
    private Connection con = conn.getConexion();

    public boolean cargar(vista_almacen view) {

        String campo = view.txtBuscar.getText();
        String where = "";

        if (!"".equals(campo)) {
            where = "WHERE tp.codigo_producto = '" + campo + "'";
        }
        try {

            DefaultTableModel model = new DefaultTableModel();
            view.jTalmacen.setModel(model);

            PreparedStatement ps = null;
            ResultSet rs = null;
            //Connection con = getConexion();
            Connection con = getConexion();
            String sql = "SELECT ta.id_registro_almacen, \n"
                    + "tp.codigo_producto, tp.descripcion_producto, \n"
                    + "ta.cantidad_registro_producto, ta.fecha_registro_producto\n"
                    + "FROM tabla_almacen AS ta\n"
                    + "INNER JOIN tabla_productos AS tp ON ta.fk_producto = tp.id_producto " + where;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantCol = rsMD.getColumnCount();

            model.addColumn("ID");
            model.addColumn("Codigo del Producto");
            model.addColumn("Descripcion del producto");
            model.addColumn("Cantidad");
            model.addColumn("Fecha");

            int[] anchos = {100, 400, 500, 200, 400};
            for (int x = 0; x < cantCol; x++) {
                view.jTalmacen.getColumnModel().getColumn(x).setPreferredWidth(anchos[x]);
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

    public int regExistente(String categoria) {

        String sql = "SELECT COUNT(tp.codigo_producto) "
                + "FROM tabla_almacen AS ta "
                + "INNER JOIN tabla_productos AS tp ON ta.fk_producto = tp.id_producto "
                + "WHERE tp.codigo_producto=? ";

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

    public int obtenerId(String codigo) {

        String sql = "SELECT id_producto FROM tabla_productos WHERE codigo_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            //System.out.println("Entra al try /n");

            if (rs.next()) {
                //System.out.println("entra al if del try");
                //System.out.println(rs.getInt(1));
                int idprod = rs.getInt(1);
                System.out.println("El valor de id es: " + idprod);
                return 1;
            }

            return 0;
        } catch (Exception e) {
        }
        return 0;
    }

    public boolean obtenerDatosProd(String codProd, vista_almacen view) {

        String sql = "SELECT descripcion_producto FROM tabla_productos "
                + "WHERE codigo_producto = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, codProd);
            rs = ps.executeQuery();

            if (rs.next()) {
                view.lblDescripcionProd.setText(rs.getString("descripcion_producto"));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean registrar(String codProd, String cantProd) {
        String sql = "SELECT id_producto FROM tabla_productos WHERE codigo_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, codProd);
            rs = ps.executeQuery();

            if (rs.next()) {
                int idprod = rs.getInt(1);
                int nuevoCantProd = Integer.parseInt(cantProd);
                String sqlInsertar = "INSERT INTO tabla_almacen "
                        + "(fk_producto, cantidad_registro_producto, fecha_registro_producto) "
                        + "VALUES (?, ?, current_timestamp());";
                try {
                    ps = con.prepareStatement(sqlInsertar);
                    ps.setInt(1, idprod);
                    ps.setInt(2, nuevoCantProd);
                    ps.execute();
                    return true;

                } catch (Exception e) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean cargarProd(vista_almacen view) {

        try {
            String sql = "SELECT codigo_producto, descripcion_producto FROM tabla_productos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                view.cbxCodProd.addItem(rs.getString("codigo_producto"));
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return false;
    }

    public boolean editar(String id, String codProd, String cantProd) {
        String sql = "SELECT id_producto FROM tabla_productos WHERE codigo_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, codProd);
            rs = ps.executeQuery();
            //System.out.println("Entra al try /n");

            if (rs.next()) {
                //System.out.println("entra al if del try");
                //System.out.println(rs.getInt(1));
                int nuevoID = Integer.parseInt(id);
                int idprod = rs.getInt(1);
                int nuevoCantProd = Integer.parseInt(cantProd);
                System.out.println("El valor de id es: " + idprod);
                System.out.println(cantProd);
                System.out.println(nuevoID);
                //return true;
                String sqlInsertar = "UPDATE tabla_almacen SET "
                        + "fk_producto=? ,cantidad_registro_producto=?, fecha_registro_producto=current_timestamp() "
                        + "WHERE id_registro_almacen=?";
                try {
                    //System.out.println("Entra al try ");
                    ps = con.prepareStatement(sqlInsertar);
                    ps.setInt(1, idprod);
                    ps.setInt(2, nuevoCantProd);
                    ps.setInt(3, nuevoID);
                    ps.execute();
                    return true;

                } catch (Exception e) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean eliminar(almacen alma) {
        String sql = "DELETE FROM tabla_almacen WHERE id_registro_almacen = ?";

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, alma.getId_reg_almacen());

            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean actualizarStock(String codPro, String cantPro, int operacion) {
        int stock = 0;
        String sql = "SELECT stock_producto FROM tabla_productos WHERE codigo_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, codPro);
            rs = ps.executeQuery();
            if (rs.next()) {
                stock = rs.getInt(1);//stock del producto                
            }
        } catch (Exception e) {
        }
        int nuevaCantidad = Integer.parseInt(cantPro);

        switch (operacion) {
            case 1:
                int StockRegistrar = stock + nuevaCantidad;
                String sqlInsertar = "UPDATE tabla_productos SET "
                        + "stock_producto=?  "
                        + "WHERE codigo_producto=?";
                //System.out.println(nuevoStock);
                //System.out.println(codPro);
                try {
                    ps = con.prepareStatement(sqlInsertar);
                    ps.setInt(1, StockRegistrar);
                    ps.setString(2, codPro);
                    ps.execute();
                } catch (Exception e) {
                }
                break;
            case 2:                
                int StockEditar = stock - nuevaCantidad;
                String sqlEditar = "UPDATE tabla_productos SET "
                        + "stock_producto=?  "
                        + "WHERE codigo_producto=?";
                System.out.println(StockEditar);
                System.out.println(codPro);
                try {
                    System.out.println("Entra al try ");
                    ps = con.prepareStatement(sqlEditar);
                    ps.setInt(1, StockEditar);
                    ps.setString(2, codPro);
                    ps.execute();
                } catch (Exception e) {
                }
                break;
        }

        return true;
    }

}
