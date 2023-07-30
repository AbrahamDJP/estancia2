/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ettkt
 */
public class conexionbd {
    
    private final String base="abarrotes_luz";//nombre de la base de datos
    private final String user="root";
    private final String pass="";
    private final String url="jdbc:mysql://localhost:3306/" + base;
    private Connection con=null;
    
    public Connection getConexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/abarrotes_luz","root","");
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(conexionbd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
}
