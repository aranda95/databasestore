package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.EnGrif;

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
    @Column(name = "tipo")
    private EnGrif Tipo;
    
    @Column(name = "referencia")
    private String Referencia;
    
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

    public EnGrif getTipo() {
        return Tipo;
    }
    
    public void setTipo(EnGrif Tipo) {
        this.Tipo = Tipo;
    }

    public String getReferencia() {
        return Referencia;
    }
    
    public void setReferencia(String Referencia) {
        this.Referencia = Referencia;
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
            ", Tipo='" + Tipo + "'" +
            ", Referencia='" + Referencia + "'" +
            ", cantidad='" + cantidad + "'" +
            ", comentario='" + comentario + "'" +
            '}';
    }
}
