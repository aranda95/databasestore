package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Producto;
import com.mycompany.myapp.repository.ProductoRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Producto.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);
        
    @Inject
    private ProductoRepository productoRepository;
    
    /**
     * POST  /productos -> Create a new producto.
     */
    @RequestMapping(value = "/productos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", producto);
        if (producto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("producto", "idexists", "A new producto cannot already have an ID")).body(null);
        }
        Producto result = productoRepository.save(producto);
        return ResponseEntity.created(new URI("/api/productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("producto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productos -> Updates an existing producto.
     */
    @RequestMapping(value = "/productos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", producto);
        if (producto.getId() == null) {
            return createProducto(producto);
        }
        Producto result = productoRepository.save(producto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("producto", producto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productos -> get all the productos.
     */
    @RequestMapping(value = "/productos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Producto> getAllProductos(@RequestParam(required = false) String filter) {
        if ("sanitario-is-null".equals(filter)) {
            log.debug("REST request to get all Productos where sanitario is null");
            return StreamSupport
                .stream(productoRepository.findAll().spliterator(), false)
                .filter(producto -> producto.getSanitario() == null)
                .collect(Collectors.toList());
        }
        if ("azulejo-is-null".equals(filter)) {
            log.debug("REST request to get all Productos where azulejo is null");
            return StreamSupport
                .stream(productoRepository.findAll().spliterator(), false)
                .filter(producto -> producto.getAzulejo() == null)
                .collect(Collectors.toList());
        }
        if ("plato-is-null".equals(filter)) {
            log.debug("REST request to get all Productos where plato is null");
            return StreamSupport
                .stream(productoRepository.findAll().spliterator(), false)
                .filter(producto -> producto.getPlato() == null)
                .collect(Collectors.toList());
        }
        if ("saco-is-null".equals(filter)) {
            log.debug("REST request to get all Productos where saco is null");
            return StreamSupport
                .stream(productoRepository.findAll().spliterator(), false)
                .filter(producto -> producto.getSaco() == null)
                .collect(Collectors.toList());
        }
        if ("grifo-is-null".equals(filter)) {
            log.debug("REST request to get all Productos where grifo is null");
            return StreamSupport
                .stream(productoRepository.findAll().spliterator(), false)
                .filter(producto -> producto.getGrifo() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Productos");
        return productoRepository.findAll();
            }

    /**
     * GET  /productos/:id -> get the "id" producto.
     */
    @RequestMapping(value = "/productos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        Producto producto = productoRepository.findOne(id);
        return Optional.ofNullable(producto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productos/:id -> delete the "id" producto.
     */
    @RequestMapping(value = "/productos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        productoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("producto", id.toString())).build();
    }
}
