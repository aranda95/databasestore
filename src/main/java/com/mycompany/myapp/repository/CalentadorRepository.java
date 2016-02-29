package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Calentador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Calentador entity.
 */
public interface CalentadorRepository extends JpaRepository<Calentador,Long> {

}
