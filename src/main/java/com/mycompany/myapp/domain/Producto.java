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

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<Sanitario> sanitarios = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<Azulejo> azulejos = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<Plato> platos = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<Saco> sacos = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<Grifo> grifos = new HashSet<>();

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

    public Set<Sanitario> getSanitarios() {
        return sanitarios;
    }

    public void setSanitarios(Set<Sanitario> sanitarios) {
        this.sanitarios = sanitarios;
    }

    public Set<Azulejo> getAzulejos() {
        return azulejos;
    }

    public void setAzulejos(Set<Azulejo> azulejos) {
        this.azulejos = azulejos;
    }

    public Set<Plato> getPlatos() {
        return platos;
    }

    public void setPlatos(Set<Plato> platos) {
        this.platos = platos;
    }

    public Set<Saco> getSacos() {
        return sacos;
    }

    public void setSacos(Set<Saco> sacos) {
        this.sacos = sacos;
    }

    public Set<Grifo> getGrifos() {
        return grifos;
    }

    public void setGrifos(Set<Grifo> grifos) {
        this.grifos = grifos;
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
