package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Sanitario;
import com.mycompany.myapp.repository.SanitarioRepository;
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
 * REST controller for managing Sanitario.
 */
@RestController
@RequestMapping("/api")
public class SanitarioResource {

    private final Logger log = LoggerFactory.getLogger(SanitarioResource.class);
        
    @Inject
    private SanitarioRepository sanitarioRepository;
    
    /**
     * POST  /sanitarios -> Create a new sanitario.
     */
    @RequestMapping(value = "/sanitarios",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sanitario> createSanitario(@RequestBody Sanitario sanitario) throws URISyntaxException {
        log.debug("REST request to save Sanitario : {}", sanitario);
        if (sanitario.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sanitario", "idexists", "A new sanitario cannot already have an ID")).body(null);
        }
        Sanitario result = sanitarioRepository.save(sanitario);
        return ResponseEntity.created(new URI("/api/sanitarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sanitario", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sanitarios -> Updates an existing sanitario.
     */
    @RequestMapping(value = "/sanitarios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sanitario> updateSanitario(@RequestBody Sanitario sanitario) throws URISyntaxException {
        log.debug("REST request to update Sanitario : {}", sanitario);
        if (sanitario.getId() == null) {
            return createSanitario(sanitario);
        }
        Sanitario result = sanitarioRepository.save(sanitario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sanitario", sanitario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sanitarios -> get all the sanitarios.
     */
    @RequestMapping(value = "/sanitarios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Sanitario> getAllSanitarios() {
        log.debug("REST request to get all Sanitarios");
        return sanitarioRepository.findAll();
            }

    /**
     * GET  /sanitarios/:id -> get the "id" sanitario.
     */
    @RequestMapping(value = "/sanitarios/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sanitario> getSanitario(@PathVariable Long id) {
        log.debug("REST request to get Sanitario : {}", id);
        Sanitario sanitario = sanitarioRepository.findOne(id);
        return Optional.ofNullable(sanitario)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sanitarios/:id -> delete the "id" sanitario.
     */
    @RequestMapping(value = "/sanitarios/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSanitario(@PathVariable Long id) {
        log.debug("REST request to delete Sanitario : {}", id);
        sanitarioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sanitario", id.toString())).build();
    }
}
