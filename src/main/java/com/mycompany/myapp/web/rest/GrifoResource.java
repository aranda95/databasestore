package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Grifo;
import com.mycompany.myapp.repository.GrifoRepository;
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
 * REST controller for managing Grifo.
 */
@RestController
@RequestMapping("/api")
public class GrifoResource {

    private final Logger log = LoggerFactory.getLogger(GrifoResource.class);
        
    @Inject
    private GrifoRepository grifoRepository;
    
    /**
     * POST  /grifos -> Create a new grifo.
     */
    @RequestMapping(value = "/grifos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grifo> createGrifo(@RequestBody Grifo grifo) throws URISyntaxException {
        log.debug("REST request to save Grifo : {}", grifo);
        if (grifo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("grifo", "idexists", "A new grifo cannot already have an ID")).body(null);
        }
        Grifo result = grifoRepository.save(grifo);
        return ResponseEntity.created(new URI("/api/grifos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("grifo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grifos -> Updates an existing grifo.
     */
    @RequestMapping(value = "/grifos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grifo> updateGrifo(@RequestBody Grifo grifo) throws URISyntaxException {
        log.debug("REST request to update Grifo : {}", grifo);
        if (grifo.getId() == null) {
            return createGrifo(grifo);
        }
        Grifo result = grifoRepository.save(grifo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("grifo", grifo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grifos -> get all the grifos.
     */
    @RequestMapping(value = "/grifos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Grifo> getAllGrifos() {
        log.debug("REST request to get all Grifos");
        return grifoRepository.findAll();
            }

    /**
     * GET  /grifos/:id -> get the "id" grifo.
     */
    @RequestMapping(value = "/grifos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grifo> getGrifo(@PathVariable Long id) {
        log.debug("REST request to get Grifo : {}", id);
        Grifo grifo = grifoRepository.findOne(id);
        return Optional.ofNullable(grifo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /grifos/:id -> delete the "id" grifo.
     */
    @RequestMapping(value = "/grifos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGrifo(@PathVariable Long id) {
        log.debug("REST request to delete Grifo : {}", id);
        grifoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("grifo", id.toString())).build();
    }
}
