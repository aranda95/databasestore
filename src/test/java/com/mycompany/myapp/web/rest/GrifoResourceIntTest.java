package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Grifo;
import com.mycompany.myapp.repository.GrifoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Seccion;

/**
 * Test class for the GrifoResource REST controller.
 *
 * @see GrifoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GrifoResourceIntTest {

    
    private static final Seccion DEFAULT_SECCION = Seccion.banera;
    private static final Seccion UPDATED_SECCION = Seccion.columnas;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;
    private static final String DEFAULT_COMENTARIO = "AAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBB";

    @Inject
    private GrifoRepository grifoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGrifoMockMvc;

    private Grifo grifo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GrifoResource grifoResource = new GrifoResource();
        ReflectionTestUtils.setField(grifoResource, "grifoRepository", grifoRepository);
        this.restGrifoMockMvc = MockMvcBuilders.standaloneSetup(grifoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        grifo = new Grifo();
        grifo.setSeccion(DEFAULT_SECCION);
        grifo.setCantidad(DEFAULT_CANTIDAD);
        grifo.setComentario(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createGrifo() throws Exception {
        int databaseSizeBeforeCreate = grifoRepository.findAll().size();

        // Create the Grifo

        restGrifoMockMvc.perform(post("/api/grifos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grifo)))
                .andExpect(status().isCreated());

        // Validate the Grifo in the database
        List<Grifo> grifos = grifoRepository.findAll();
        assertThat(grifos).hasSize(databaseSizeBeforeCreate + 1);
        Grifo testGrifo = grifos.get(grifos.size() - 1);
        assertThat(testGrifo.getSeccion()).isEqualTo(DEFAULT_SECCION);
        assertThat(testGrifo.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testGrifo.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllGrifos() throws Exception {
        // Initialize the database
        grifoRepository.saveAndFlush(grifo);

        // Get all the grifos
        restGrifoMockMvc.perform(get("/api/grifos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(grifo.getId().intValue())))
                .andExpect(jsonPath("$.[*].seccion").value(hasItem(DEFAULT_SECCION.toString())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
                .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())));
    }

    @Test
    @Transactional
    public void getGrifo() throws Exception {
        // Initialize the database
        grifoRepository.saveAndFlush(grifo);

        // Get the grifo
        restGrifoMockMvc.perform(get("/api/grifos/{id}", grifo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(grifo.getId().intValue()))
            .andExpect(jsonPath("$.seccion").value(DEFAULT_SECCION.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGrifo() throws Exception {
        // Get the grifo
        restGrifoMockMvc.perform(get("/api/grifos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrifo() throws Exception {
        // Initialize the database
        grifoRepository.saveAndFlush(grifo);

		int databaseSizeBeforeUpdate = grifoRepository.findAll().size();

        // Update the grifo
        grifo.setSeccion(UPDATED_SECCION);
        grifo.setCantidad(UPDATED_CANTIDAD);
        grifo.setComentario(UPDATED_COMENTARIO);

        restGrifoMockMvc.perform(put("/api/grifos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grifo)))
                .andExpect(status().isOk());

        // Validate the Grifo in the database
        List<Grifo> grifos = grifoRepository.findAll();
        assertThat(grifos).hasSize(databaseSizeBeforeUpdate);
        Grifo testGrifo = grifos.get(grifos.size() - 1);
        assertThat(testGrifo.getSeccion()).isEqualTo(UPDATED_SECCION);
        assertThat(testGrifo.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testGrifo.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void deleteGrifo() throws Exception {
        // Initialize the database
        grifoRepository.saveAndFlush(grifo);

		int databaseSizeBeforeDelete = grifoRepository.findAll().size();

        // Get the grifo
        restGrifoMockMvc.perform(delete("/api/grifos/{id}", grifo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Grifo> grifos = grifoRepository.findAll();
        assertThat(grifos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
