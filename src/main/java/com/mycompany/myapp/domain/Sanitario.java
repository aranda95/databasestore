package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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
    
    @Column(name = "medidas")
    private String medidas;
    
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

    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
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
            ", medidas='" + medidas + "'" +
            ", cantidad='" + cantidad + "'" +
            '}';
    }
}
