package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Azulejo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Azulejo entity.
 */
public interface AzulejoRepository extends JpaRepository<Azulejo,Long> {

}
