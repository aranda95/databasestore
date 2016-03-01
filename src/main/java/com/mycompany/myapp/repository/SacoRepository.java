package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Saco;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Saco entity.
 */
public interface SacoRepository extends JpaRepository<Saco,Long> {

}
