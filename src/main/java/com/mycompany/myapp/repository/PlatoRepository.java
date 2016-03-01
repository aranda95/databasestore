package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plato;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plato entity.
 */
public interface PlatoRepository extends JpaRepository<Plato,Long> {

}
