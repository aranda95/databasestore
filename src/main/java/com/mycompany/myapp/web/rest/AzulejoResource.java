package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Azulejo;
import com.mycompany.myapp.repository.AzulejoRepository;
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
 * REST controller for managing Azulejo.
 */
@RestController
@RequestMapping("/api")
public class AzulejoResource {

    private final Logger log = LoggerFactory.getLogger(AzulejoResource.class);
        
    @Inject
    private AzulejoRepository azulejoRepository;
    
    /**
     * POST  /azulejos -> Create a new azulejo.
     */
    @RequestMapping(value = "/azulejos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Azulejo> createAzulejo(@RequestBody Azulejo azulejo) throws URISyntaxException {
        log.debug("REST request to save Azulejo : {}", azulejo);
        if (azulejo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("azulejo", "idexists", "A new azulejo cannot already have an ID")).body(null);
        }
        Azulejo result = azulejoRepository.save(azulejo);
        return ResponseEntity.created(new URI("/api/azulejos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("azulejo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /azulejos -> Updates an existing azulejo.
     */
    @RequestMapping(value = "/azulejos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Azulejo> updateAzulejo(@RequestBody Azulejo azulejo) throws URISyntaxException {
        log.debug("REST request to update Azulejo : {}", azulejo);
        if (azulejo.getId() == null) {
            return createAzulejo(azulejo);
        }
        Azulejo result = azulejoRepository.save(azulejo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("azulejo", azulejo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /azulejos -> get all the azulejos.
     */
    @RequestMapping(value = "/azulejos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Azulejo> getAllAzulejos() {
        log.debug("REST request to get all Azulejos");
        return azulejoRepository.findAll();
            }

    /**
     * GET  /azulejos/:id -> get the "id" azulejo.
     */
    @RequestMapping(value = "/azulejos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Azulejo> getAzulejo(@PathVariable Long id) {
        log.debug("REST request to get Azulejo : {}", id);
        Azulejo azulejo = azulejoRepository.findOne(id);
        return Optional.ofNullable(azulejo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /azulejos/:id -> delete the "id" azulejo.
     */
    @RequestMapping(value = "/azulejos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAzulejo(@PathVariable Long id) {
        log.debug("REST request to delete Azulejo : {}", id);
        azulejoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("azulejo", id.toString())).build();
    }
}
