package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.EnCal;

/**
 * A Calentador.
 */
@Entity
@Table(name = "calentador")
public class Calentador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "modelo")
    private String modelo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gas")
    private EnCal Gas;
    
    @Column(name = "litros")
    private Integer litros;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "comentario")
    private String comentario;
    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public EnCal getGas() {
        return Gas;
    }
    
    public void setGas(EnCal Gas) {
        this.Gas = Gas;
    }

    public Integer getLitros() {
        return litros;
    }
    
    public void setLitros(Integer litros) {
        this.litros = litros;
    }

    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Calentador calentador = (Calentador) o;
        if(calentador.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, calentador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Calentador{" +
            "id=" + id +
            ", modelo='" + modelo + "'" +
            ", Gas='" + Gas + "'" +
            ", litros='" + litros + "'" +
            ", cantidad='" + cantidad + "'" +
            ", comentario='" + comentario + "'" +
            '}';
    }
}
