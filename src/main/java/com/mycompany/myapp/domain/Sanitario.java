package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.EnSan;

/**
 * A Sanitario.
 */
@Entity
@Table(name = "sanitario")
public class Sanitario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "modelo")
    private String modelo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "salida")
    private EnSan salida;
    
    @Column(name = "medidas")
    private String medidas;
    
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

    public EnSan getSalida() {
        return salida;
    }
    
    public void setSalida(EnSan salida) {
        this.salida = salida;
    }

    public String getMedidas() {
        return medidas;
    }
    
    public void setMedidas(String medidas) {
        this.medidas = medidas;
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
        Sanitario sanitario = (Sanitario) o;
        if(sanitario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sanitario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sanitario{" +
            "id=" + id +
            ", modelo='" + modelo + "'" +
            ", salida='" + salida + "'" +
            ", medidas='" + medidas + "'" +
            ", cantidad='" + cantidad + "'" +
            ", comentario='" + comentario + "'" +
            '}';
    }
}
