package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sanitario;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sanitario entity.
 */
public interface SanitarioRepository extends JpaRepository<Sanitario,Long> {

}
