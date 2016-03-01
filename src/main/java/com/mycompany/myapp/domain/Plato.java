package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Plato.
 */
@Entity
@Table(name = "plato")
public class Plato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "marca")
    private String marca;
    
    @Column(name = "color")
    private String color;
    
    @Column(name = "medidas")
    private String medidas;
    
    @Column(name = "comentario")
    private String comentario;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @OneToOne
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }

    public String getMedidas() {
        return medidas;
    }
    
    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
        Plato plato = (Plato) o;
        if(plato.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, plato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Plato{" +
            "id=" + id +
            ", marca='" + marca + "'" +
            ", color='" + color + "'" +
            ", medidas='" + medidas + "'" +
            ", comentario='" + comentario + "'" +
            ", cantidad='" + cantidad + "'" +
            '}';
    }
}
