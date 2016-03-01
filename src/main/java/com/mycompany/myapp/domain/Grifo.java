package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Seccion;

/**
 * A Grifo.
 */
@Entity
@Table(name = "grifo")
public class Grifo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "seccion")
    private Seccion seccion;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "comentario")
    private String comentario;
    
    @OneToOne
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seccion getSeccion() {
        return seccion;
    }
    
    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
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
        Grifo grifo = (Grifo) o;
        if(grifo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, grifo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Grifo{" +
            "id=" + id +
            ", seccion='" + seccion + "'" +
            ", cantidad='" + cantidad + "'" +
            ", comentario='" + comentario + "'" +
            '}';
    }
}
