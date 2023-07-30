/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ettkt
 */
public class sql_login extends conexionbd {

    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = getConexion();

    public boolean login(modLogin log) {
        String sql = "SELECT tu.usuario, tu.contrasenia, tu.fk_tipo_usuario, tu.fk_status ,tp.tipo_usuario "
                + "FROM tabla_usuarios AS tu "
                + "INNER JOIN tabla_tipo_usuario AS tp ON tu.fk_tipo_usuario=tp.id_tipo_usuario "
                + "WHERE tu.usuario=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, log.getUsuario());
            rs = ps.executeQuery();

            if (rs.next()) {

                if (log.getContrasenia().equals(rs.getString(2))) {
                    //log.setTipo(rs.getString(3));
                    log.setTipo(rs.getInt(3));
                    log.setStatus(rs.getInt(4));
                    log.setRol(rs.getString(5));

                    return true;
                } else {
                    return false;
                }
            }
            return false;

        } catch (SQLException ex) {
            Logger.getLogger(sql_login.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
}
