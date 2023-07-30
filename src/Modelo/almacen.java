/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author ettkt
 */
public class almacen {
    
    private int id_reg_almacen;
    private int fk_producto;
    private int cant_reg_producto;
    private String fecha;

    public int getId_reg_almacen() {
        return id_reg_almacen;
    }

    public void setId_reg_almacen(int id_reg_almacen) {
        this.id_reg_almacen = id_reg_almacen;
    }

    public int getFk_producto() {
        return fk_producto;
    }

    public void setFk_producto(int fk_producto) {
        this.fk_producto = fk_producto;
    }

    public int getCant_reg_producto() {
        return cant_reg_producto;
    }

    public void setCant_reg_producto(int cant_reg_producto) {
        this.cant_reg_producto = cant_reg_producto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
