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
public class clientes {

    private int id_cliente;
    private String nombre_cliente;
    private String ap_cliente;
    private String am_cliente;
    private String sexo_cliente;
    private String direccion_cliente;
    private String telefono;
    private String correo;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getAp_cliente() {
        return ap_cliente;
    }

    public void setAp_cliente(String ap_cliente) {
        this.ap_cliente = ap_cliente;
    }

    public String getAm_cliente() {
        return am_cliente;
    }

    public void setAm_cliente(String am_cliente) {
        this.am_cliente = am_cliente;
    }

    public String getSexo_cliente() {
        return sexo_cliente;
    }

    public void setSexo_cliente(String sexo_cliente) {
        this.sexo_cliente = sexo_cliente;
    }

    public String getDireccion_cliente() {
        return direccion_cliente;
    }

    public void setDireccion_cliente(String direccion_cliente) {
        this.direccion_cliente = direccion_cliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
