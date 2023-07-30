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
public class productos {
    
    int idProducto;
    String codigo_producto;
    String descripcion_producto;
    int fk_categoria;
    int stock;
    double presio_compra;
    double presio_venta;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getDescripcion_producto() {
        return descripcion_producto;
    }

    public void setDescripcion_producto(String descripcion_producto) {
        this.descripcion_producto = descripcion_producto;
    }

    public int getFk_categoria() {
        return fk_categoria;
    }

    public void setFk_categoria(int fk_categoria) {
        this.fk_categoria = fk_categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPresio_compra() {
        return presio_compra;
    }

    public void setPresio_compra(double presio_compra) {
        this.presio_compra = presio_compra;
    }

    public double getPresio_venta() {
        return presio_venta;
    }

    public void setPresio_venta(double presio_venta) {
        this.presio_venta = presio_venta;
    }
    
    

    
    
}
