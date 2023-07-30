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
public class ventas {
    
    private int id_venta;
    private int fk_producto;
    private int cantidad_producto_vendido;
    private int fk_usuario;
    private int fk_cliente;
    private String fecha_venta;
    private double cantidad_neto;    
    private double cantidad_total;

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getFk_producto() {
        return fk_producto;
    }

    public void setFk_producto(int fk_producto) {
        this.fk_producto = fk_producto;
    }

    public int getCantidad_producto_vendido() {
        return cantidad_producto_vendido;
    }

    public void setCantidad_producto_vendido(int cantidad_producto_vendido) {
        this.cantidad_producto_vendido = cantidad_producto_vendido;
    }

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public int getFk_cliente() {
        return fk_cliente;
    }

    public void setFk_cliente(int fk_cliente) {
        this.fk_cliente = fk_cliente;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public double getCantidad_neto() {
        return cantidad_neto;
    }

    public void setCantidad_neto(double cantidad_neto) {
        this.cantidad_neto = cantidad_neto;
    }

    public double getCantidad_total() {
        return cantidad_total;
    }

    public void setCantidad_total(double cantidad_total) {
        this.cantidad_total = cantidad_total;
    }
    
    
    
}
