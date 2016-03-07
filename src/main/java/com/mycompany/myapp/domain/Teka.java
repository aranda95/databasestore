package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.EnTek;

/**
 * A Teka.
 */
@Entity
@Table(name = "teka")
public class Teka implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private EnTek Tipo;
    
    @Column(name = "modelo")
    private String modelo;
    
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

    public EnTek getTipo() {
        return Tipo;
    }
    
    public void setTipo(EnTek Tipo) {
        this.Tipo = Tipo;
    }

    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
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
        Teka teka = (Teka) o;
        if(teka.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, teka.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Teka{" +
            "id=" + id +
            ", Tipo='" + Tipo + "'" +
            ", modelo='" + modelo + "'" +
            ", cantidad='" + cantidad + "'" +
            ", comentario='" + comentario + "'" +
            '}';
    }
}
