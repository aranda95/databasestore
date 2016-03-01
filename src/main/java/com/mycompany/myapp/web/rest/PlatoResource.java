package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Plato;
import com.mycompany.myapp.repository.PlatoRepository;
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
 * REST controller for managing Plato.
 */
@RestController
@RequestMapping("/api")
public class PlatoResource {

    private final Logger log = LoggerFactory.getLogger(PlatoResource.class);
        
    @Inject
    private PlatoRepository platoRepository;
    
    /**
     * POST  /platos -> Create a new plato.
     */
    @RequestMapping(value = "/platos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plato> createPlato(@RequestBody Plato plato) throws URISyntaxException {
        log.debug("REST request to save Plato : {}", plato);
        if (plato.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("plato", "idexists", "A new plato cannot already have an ID")).body(null);
        }
        Plato result = platoRepository.save(plato);
        return ResponseEntity.created(new URI("/api/platos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("plato", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /platos -> Updates an existing plato.
     */
    @RequestMapping(value = "/platos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plato> updatePlato(@RequestBody Plato plato) throws URISyntaxException {
        log.debug("REST request to update Plato : {}", plato);
        if (plato.getId() == null) {
            return createPlato(plato);
        }
        Plato result = platoRepository.save(plato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("plato", plato.getId().toString()))
            .body(result);
    }

    /**
     * GET  /platos -> get all the platos.
     */
    @RequestMapping(value = "/platos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Plato> getAllPlatos() {
        log.debug("REST request to get all Platos");
        return platoRepository.findAll();
            }

    /**
     * GET  /platos/:id -> get the "id" plato.
     */
    @RequestMapping(value = "/platos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plato> getPlato(@PathVariable Long id) {
        log.debug("REST request to get Plato : {}", id);
        Plato plato = platoRepository.findOne(id);
        return Optional.ofNullable(plato)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /platos/:id -> delete the "id" plato.
     */
    @RequestMapping(value = "/platos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePlato(@PathVariable Long id) {
        log.debug("REST request to delete Plato : {}", id);
        platoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("plato", id.toString())).build();
    }
}
