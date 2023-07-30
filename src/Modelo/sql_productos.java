/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.vista_productos;
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
public class sql_productos extends conexionbd {

    PreparedStatement ps = null;
    ResultSet rs = null;
    conexionbd conn = new conexionbd();
    Connection con = getConexion();

    public boolean cargar(vista_productos view) {
        String campo = view.txtBuscar.getText();
        String where = "";

        if (!"".equals(campo)) {
            where = "WHERE tp.codigo_producto = '" + campo + "'";
        }
        try {
            DefaultTableModel model = new DefaultTableModel();
            view.jTproductos.setModel(model);

            String sql = "SELECT tp.id_producto, tp.codigo_producto, tp.descripcion_producto, "
                    + "tc.categoria, tp.stock_producto, tp.precio_compra_producto, "
                    + "tp.precio_venta_producto "
                    + "FROM tabla_productos AS tp "
                    + "INNER JOIN tabla_categorias AS tc ON tp.fk_categoria = tc.id_categoria_producto " + where;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantCol = rsMD.getColumnCount();

            model.addColumn("ID");
            model.addColumn("Codigo");
            model.addColumn("Descripcion");
            model.addColumn("Categoria");
            model.addColumn("U/ Stock");
            model.addColumn("Presio/Compra");
            model.addColumn("Presio/Venta");

            int[] anchos = {20, 100, 150, 80, 50, 50, 50};
            for (int x = 0; x < cantCol; x++) {
                view.jTproductos.getColumnModel().getColumn(x).setPreferredWidth(anchos[x]);
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
            System.err.println(e.toString());
            return false;
        }
    }

    public int productoExistente(String producto) {
        String sql = "SELECT count(codigo_producto) FROM tabla_productos WHERE codigo_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, producto);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 1;

        } catch (SQLException ex) {
            return 1;
        }

    }

    public boolean cargarcbxCat(vista_productos view) {
        try {
            String sql = "SELECT * FROM tabla_categorias";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                view.cbbxCategorias.addItem(rs.getString("categoria"));
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
    }

    public boolean registrar(productos prod) {
        String sql = "INSERT INTO tabla_productos "
                + "(codigo_producto, descripcion_producto, fk_categoria, "
                + "stock_producto, precio_compra_producto, precio_venta_producto) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getCodigo_producto());
            ps.setString(2, prod.getDescripcion_producto());
            ps.setInt(3, prod.getFk_categoria());
            ps.setInt(4, prod.getStock());
            ps.setDouble(5, prod.getPresio_compra());
            ps.setDouble(6, prod.getPresio_venta());
            ps.execute();
            return true;
        } catch (Exception e) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public int obtenerCat(String categoria) {

        String sql = "SELECT id_categoria_producto FROM tabla_categorias WHERE categoria = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, categoria);
            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }

            return 0;

        } catch (SQLException ex) {
            return 0;
        }

    }

    public boolean editar(productos prod) {
        
        String sql = "UPDATE tabla_productos SET "
                + "codigo_producto=?, descripcion_producto=?, fk_categoria=?, "
                + "stock_producto=?, precio_compra_producto=?, precio_venta_producto=? "
                + "WHERE id_producto=?";
        try {
            
            System.out.println(prod.getCodigo_producto());
            System.out.println(prod.getDescripcion_producto());
            System.out.println(prod.getFk_categoria());
            System.out.println(prod.getStock());
            System.out.println(prod.getPresio_compra());
            System.out.println(prod.getPresio_venta());
            System.out.println(prod.getIdProducto());
            
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getCodigo_producto());
            ps.setString(2, prod.getDescripcion_producto());
            ps.setInt(3, prod.getFk_categoria());
            ps.setInt(4, prod.getStock());
            ps.setDouble(5, prod.getPresio_compra());
            ps.setDouble(6, prod.getPresio_venta());
            ps.setInt(7, prod.getIdProducto());
            ps.execute();
            return true;
        } catch (Exception e) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    public boolean eliminar(productos prod){
        String sql = "DELETE FROM tabla_productos WHERE id_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, prod.getIdProducto());
            ps.execute();
            return true;
        } catch (Exception e) {
            Logger.getLogger(sql_productos.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        
    }
    
}
