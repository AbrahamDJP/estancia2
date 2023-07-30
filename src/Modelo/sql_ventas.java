/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.vista_ventas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ettkt
 */
public class sql_ventas extends conexionbd {

    PreparedStatement ps = null;
    ResultSet rs = null;
    conexionbd conn = new conexionbd();
    Connection con = getConexion();

    public boolean cargar(vista_ventas view) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            view.jTventas.setModel(model);
            String sql = "SELECT tv.id_venta, "
                    + "tp.codigo_producto, tp.descripcion_producto, "
                    + "tv.cantidad_producto_vendido, tu.usuario, "
                    + "tc.nombre_cliente, fecha_venta, cantidad_total "
                    + "FROM tabla_ventas AS tv "
                    + "INNER JOIN tabla_productos AS tp ON tv.fk_producto=tp.id_producto "
                    + "INNER JOIN tabla_usuarios AS tu ON tv.fk_usuario=tu.id_usuario "
                    + "INNER JOIN tabla_clientes AS tc ON tv.fk_cliente=tc.id_cliente ";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantCol = rsMD.getColumnCount();

            model.addColumn("ID");
            model.addColumn("Cod / Producto");
            model.addColumn("Descripcion del producto");
            model.addColumn("Uds / Vendidas");
            model.addColumn("Empleado");
            model.addColumn("Cliente");
            model.addColumn("Fecha");
            //model.addColumn("Valor Neto");
            model.addColumn("Valor Total");

            int[] anchos = {20, 100, 200, 50, 100, 100, 100, 50, 50};
            for (int x = 0; x < cantCol; x++) {
                view.jTventas.getColumnModel().getColumn(x).setPreferredWidth(anchos[x]);
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

    public boolean cargarProd(vista_ventas view) {

        String sql = "SELECT codigo_producto FROM tabla_productos ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                view.cbbxProducto.addItem(rs.getString("codigo_producto"));

            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cargarClientes(vista_ventas view) {
        String sql = "SELECT id_cliente, nombre_cliente, ap_cliente, am_cliente, direccion_cliente FROM tabla_clientes ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                view.cbbxCliente.addItem(rs.getString("id_cliente"));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ontenerDatosCliente(String id, vista_ventas view) {

        String sql = "SELECT nombre_cliente, ap_cliente, am_cliente, direccion_cliente "
                + "FROM tabla_clientes WHERE id_cliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                view.lbl_cliente.setText(rs.getString("nombre_cliente"));
                view.lblAp.setText(rs.getString("ap_cliente"));
                view.lbl_Am.setText(rs.getString("am_cliente"));
                view.lbl_Direccion.setText(rs.getString("direccion_cliente"));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean obtenerDatosProductos(String codigoProd, vista_ventas view) {
        String sql = "SELECT descripcion_producto, precio_venta_producto "
                + "FROM tabla_productos WHERE codigo_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, codigoProd);
            rs = ps.executeQuery();

            if (rs.next()) {
                //view.lbl_cliente.setText(rs.getString("nombre_cliente"));
                view.lblDescripcion.setText(rs.getString("descripcion_producto"));
                view.lblPrecio.setText(rs.getString("precio_venta_producto"));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int obtenerIdProd(String codigo) {
        String sql = "SELECT id_producto FROM tabla_productos WHERE codigo_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public int obtenerIdUsuario(String empleado) {
        String sql = "SELECT id_usuario FROM tabla_usuarios WHERE usuario = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, empleado);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public int stockDisponible(ventas vntas) {
        String sql = "SELECT stock_producto FROM tabla_productos WHERE id_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, vntas.getFk_producto());
            rs = ps.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt(1);
                return stock;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public boolean actualizarStockProd(ventas vntas) {
        System.out.println("Entra al metodo actulizar");
        String sql = "SELECT stock_producto FROM tabla_productos WHERE id_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, vntas.getFk_producto());
            System.out.println("id Producto" +vntas.getFk_producto());
            rs = ps.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt(1);
                //System.out.println("Almacen" +stock);
                if (stock >= vntas.getCantidad_producto_vendido()) {
                    int nuevoStock = stock - vntas.getCantidad_producto_vendido();
                    //System.out.println("nuevo Stock"+nuevoStock);
                    String sqlUpdStock = "UPDATE tabla_productos SET stock_producto = ? "
                            + "WHERE id_producto = ?";
                    try {
                        ps = con.prepareStatement(sqlUpdStock);
                        ps.setInt(1, nuevoStock);
                        /*System.out.println("try");
                        System.out.println("nuevo Stock " + nuevoStock);*/
                        ps.setInt(2, vntas.getFk_producto());
                        //System.out.println("id Prod "+ vntas.getFk_producto());
                        ps.execute();
                        return true;
                    } catch (Exception e) {
                    }
                } else {
                    return false;
                }
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean registrar(ventas vntas) {
        System.out.println("entra al metodo");
        String sql = "INSERT INTO tabla_ventas "
                + "(fk_producto, cantidad_producto_vendido, fk_usuario, fk_cliente, "
                + "fecha_venta,  cantidad_total) "
                + "VALUES (?, ?, ?, ?, current_timestamp(), ?);";
        try {
            System.out.println("Entra al try");
            ps = con.prepareStatement(sql);
            ps.setInt(1, vntas.getFk_producto());
            ps.setInt(2, vntas.getCantidad_producto_vendido());
            ps.setInt(3, vntas.getFk_usuario());
            ps.setInt(4, vntas.getFk_cliente());
            //ps.setDouble(5, vntas.getCantidad_neto());
            //ps.setInt(6, vntas.getImpuesto_venta());
            ps.setDouble(5, vntas.getCantidad_total());
            
            System.out.println(vntas.getFk_producto());
            System.out.println(vntas.getCantidad_producto_vendido());
            System.out.println(vntas.getFk_usuario());
            System.out.println(vntas.getFk_cliente());
            System.out.println(vntas.getCantidad_total());
            
            ps.execute();

            return true;
        } catch (Exception e) {
            Logger.getLogger(sql_categorias.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        
    }

    public int obtenerImpuestoVnta(int idvnta) {
        String sql = "SELECT impuesto_venta FROM tabla_ventas WHERE id_venta = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idvnta);
            rs = ps.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt(1);
                return stock;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public int obtenerIDusr(String nomb_usr) {
        String sql = "SELECT id_cliente FROM tabla_clientes WHERE nombre_cliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nomb_usr);
            rs = ps.executeQuery();
            if (rs.next()) {
                int idClient = rs.getInt(1);
                return idClient;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public boolean editar(ventas vntas) {
        String sql = "UPDATE tabla_ventas SET "
                + "fk_producto=?, cantidad_producto_vendido=?, fk_usuario=?, fk_cliente=?, "
                + "cantidad_neto=?, cantidad_total=? "
                + "WHERE id_venta=?";
                
        System.out.println("metodo editar");
                //+ "VALUES (?, ?, ?, ?, current_timestamp(), ?, ?, ?);";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, vntas.getFk_producto());
            ps.setInt(2, vntas.getCantidad_producto_vendido());
            ps.setInt(3, vntas.getFk_usuario());
            ps.setInt(4, vntas.getFk_cliente());
            ps.setDouble(5, vntas.getCantidad_neto());
            //ps.setInt(6, vntas.getImpuesto_venta());
            ps.setDouble(6, vntas.getCantidad_total());
            ps.setInt(7, vntas.getId_venta());
            /*System.out.println(vntas.getFk_producto());
            System.out.println(vntas.getCantidad_producto_vendido());
            System.out.println(vntas.getFk_usuario());
            System.out.println(vntas.getFk_cliente());
            System.out.println(vntas.getCantidad_neto());
            System.out.println(vntas.getImpuesto_venta());
            System.out.println(vntas.getCantidad_total());
            System.out.println(vntas.getId_venta());*/
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }
    
    public boolean eliminar(ventas vntas){
        String sql = "DELETE FROM tabla_ventas WHERE id_venta = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, vntas.getId_venta());
            ps.execute();
            return true;
        } catch (Exception e) {
            Logger.getLogger(sql_productos.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

}
