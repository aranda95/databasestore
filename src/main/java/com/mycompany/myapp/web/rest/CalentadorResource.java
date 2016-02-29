package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Calentador;
import com.mycompany.myapp.repository.CalentadorRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Calentador.
 */
@RestController
@RequestMapping("/api")
public class CalentadorResource {

    private final Logger log = LoggerFactory.getLogger(CalentadorResource.class);
        
    @Inject
    private CalentadorRepository calentadorRepository;
    
    /**
     * POST  /calentadors -> Create a new calentador.
     */
    @RequestMapping(value = "/calentadors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Calentador> createCalentador(@RequestBody Calentador calentador) throws URISyntaxException {
        log.debug("REST request to save Calentador : {}", calentador);
        if (calentador.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("calentador", "idexists", "A new calentador cannot already have an ID")).body(null);
        }
        Calentador result = calentadorRepository.save(calentador);
        return ResponseEntity.created(new URI("/api/calentadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("calentador", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calentadors -> Updates an existing calentador.
     */
    @RequestMapping(value = "/calentadors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Calentador> updateCalentador(@RequestBody Calentador calentador) throws URISyntaxException {
        log.debug("REST request to update Calentador : {}", calentador);
        if (calentador.getId() == null) {
            return createCalentador(calentador);
        }
        Calentador result = calentadorRepository.save(calentador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("calentador", calentador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calentadors -> get all the calentadors.
     */
    @RequestMapping(value = "/calentadors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Calentador> getAllCalentadors() {
        log.debug("REST request to get all Calentadors");
        return calentadorRepository.findAll();
            }

    /**
     * GET  /calentadors/:id -> get the "id" calentador.
     */
    @RequestMapping(value = "/calentadors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Calentador> getCalentador(@PathVariable Long id) {
        log.debug("REST request to get Calentador : {}", id);
        Calentador calentador = calentadorRepository.findOne(id);
        return Optional.ofNullable(calentador)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /calentadors/:id -> delete the "id" calentador.
     */
    @RequestMapping(value = "/calentadors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCalentador(@PathVariable Long id) {
        log.debug("REST request to delete Calentador : {}", id);
        calentadorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("calentador", id.toString())).build();
    }
}
