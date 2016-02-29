package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Teka;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Teka entity.
 */
public interface TekaRepository extends JpaRepository<Teka,Long> {

}
