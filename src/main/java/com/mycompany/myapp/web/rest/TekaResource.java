package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Teka;
import com.mycompany.myapp.repository.TekaRepository;
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
 * REST controller for managing Teka.
 */
@RestController
@RequestMapping("/api")
public class TekaResource {

    private final Logger log = LoggerFactory.getLogger(TekaResource.class);
        
    @Inject
    private TekaRepository tekaRepository;
    
    /**
     * POST  /tekas -> Create a new teka.
     */
    @RequestMapping(value = "/tekas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teka> createTeka(@RequestBody Teka teka) throws URISyntaxException {
        log.debug("REST request to save Teka : {}", teka);
        if (teka.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("teka", "idexists", "A new teka cannot already have an ID")).body(null);
        }
        Teka result = tekaRepository.save(teka);
        return ResponseEntity.created(new URI("/api/tekas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("teka", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tekas -> Updates an existing teka.
     */
    @RequestMapping(value = "/tekas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teka> updateTeka(@RequestBody Teka teka) throws URISyntaxException {
        log.debug("REST request to update Teka : {}", teka);
        if (teka.getId() == null) {
            return createTeka(teka);
        }
        Teka result = tekaRepository.save(teka);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("teka", teka.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tekas -> get all the tekas.
     */
    @RequestMapping(value = "/tekas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Teka> getAllTekas() {
        log.debug("REST request to get all Tekas");
        return tekaRepository.findAll();
            }

    /**
     * GET  /tekas/:id -> get the "id" teka.
     */
    @RequestMapping(value = "/tekas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teka> getTeka(@PathVariable Long id) {
        log.debug("REST request to get Teka : {}", id);
        Teka teka = tekaRepository.findOne(id);
        return Optional.ofNullable(teka)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tekas/:id -> delete the "id" teka.
     */
    @RequestMapping(value = "/tekas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTeka(@PathVariable Long id) {
        log.debug("REST request to delete Teka : {}", id);
        tekaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("teka", id.toString())).build();
    }
}
