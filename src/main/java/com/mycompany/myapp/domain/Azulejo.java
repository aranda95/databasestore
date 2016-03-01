package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Azulejo.
 */
@Entity
@Table(name = "azulejo")
public class Azulejo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "marca")
    private String marca;
    
    @Column(name = "m2")
    private Integer m2;
    
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

    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getm2() {
        return m2;
    }
    
    public void setm2(Integer m2) {
        this.m2 = m2;
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
        Azulejo azulejo = (Azulejo) o;
        if(azulejo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, azulejo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Azulejo{" +
            "id=" + id +
            ", marca='" + marca + "'" +
            ", m2='" + m2 + "'" +
            ", comentario='" + comentario + "'" +
            '}';
    }
}
