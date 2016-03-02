package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<Calentador> calentadors = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<Teka> tekas = new HashSet<>();

    @OneToOne(mappedBy = "producto")
    @JsonIgnore
    private Sanitario sanitario;

    @OneToOne(mappedBy = "producto")
    @JsonIgnore
    private Azulejo azulejo;

    @OneToOne(mappedBy = "producto")
    @JsonIgnore
    private Plato plato;

    @OneToOne(mappedBy = "producto")
    @JsonIgnore
    private Saco saco;

    @OneToOne(mappedBy = "producto")
    @JsonIgnore
    private Grifo grifo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Calentador> getCalentadors() {
        return calentadors;
    }

    public void setCalentadors(Set<Calentador> calentadors) {
        this.calentadors = calentadors;
    }

    public Set<Teka> getTekas() {
        return tekas;
    }

    public void setTekas(Set<Teka> tekas) {
        this.tekas = tekas;
    }

    public Sanitario getSanitario() {
        return sanitario;
    }

    public void setSanitario(Sanitario sanitario) {
        this.sanitario = sanitario;
    }

    public Azulejo getAzulejo() {
        return azulejo;
    }

    public void setAzulejo(Azulejo azulejo) {
        this.azulejo = azulejo;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Saco getSaco() {
        return saco;
    }

    public void setSaco(Saco saco) {
        this.saco = saco;
    }

    public Grifo getGrifo() {
        return grifo;
    }

    public void setGrifo(Grifo grifo) {
        this.grifo = grifo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producto producto = (Producto) o;
        if(producto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + id +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
