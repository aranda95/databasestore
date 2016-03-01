package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Saco;
import com.mycompany.myapp.repository.SacoRepository;
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
 * REST controller for managing Saco.
 */
@RestController
@RequestMapping("/api")
public class SacoResource {

    private final Logger log = LoggerFactory.getLogger(SacoResource.class);
        
    @Inject
    private SacoRepository sacoRepository;
    
    /**
     * POST  /sacos -> Create a new saco.
     */
    @RequestMapping(value = "/sacos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Saco> createSaco(@RequestBody Saco saco) throws URISyntaxException {
        log.debug("REST request to save Saco : {}", saco);
        if (saco.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("saco", "idexists", "A new saco cannot already have an ID")).body(null);
        }
        Saco result = sacoRepository.save(saco);
        return ResponseEntity.created(new URI("/api/sacos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("saco", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sacos -> Updates an existing saco.
     */
    @RequestMapping(value = "/sacos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Saco> updateSaco(@RequestBody Saco saco) throws URISyntaxException {
        log.debug("REST request to update Saco : {}", saco);
        if (saco.getId() == null) {
            return createSaco(saco);
        }
        Saco result = sacoRepository.save(saco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("saco", saco.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sacos -> get all the sacos.
     */
    @RequestMapping(value = "/sacos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Saco> getAllSacos() {
        log.debug("REST request to get all Sacos");
        return sacoRepository.findAll();
            }

    /**
     * GET  /sacos/:id -> get the "id" saco.
     */
    @RequestMapping(value = "/sacos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Saco> getSaco(@PathVariable Long id) {
        log.debug("REST request to get Saco : {}", id);
        Saco saco = sacoRepository.findOne(id);
        return Optional.ofNullable(saco)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sacos/:id -> delete the "id" saco.
     */
    @RequestMapping(value = "/sacos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSaco(@PathVariable Long id) {
        log.debug("REST request to delete Saco : {}", id);
        sacoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("saco", id.toString())).build();
    }
}
