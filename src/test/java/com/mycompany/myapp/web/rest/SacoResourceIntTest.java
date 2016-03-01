package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Saco;
import com.mycompany.myapp.repository.SacoRepository;

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

import com.mycompany.myapp.domain.enumeration.Modelo;

/**
 * Test class for the SacoResource REST controller.
 *
 * @see SacoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SacoResourceIntTest {

    
    private static final Modelo DEFAULT_MODELO = Modelo.minicontainer;
    private static final Modelo UPDATED_MODELO = Modelo.runa;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;
    private static final String DEFAULT_COMENTARIO = "AAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBB";

    @Inject
    private SacoRepository sacoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSacoMockMvc;

    private Saco saco;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SacoResource sacoResource = new SacoResource();
        ReflectionTestUtils.setField(sacoResource, "sacoRepository", sacoRepository);
        this.restSacoMockMvc = MockMvcBuilders.standaloneSetup(sacoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        saco = new Saco();
        saco.setModelo(DEFAULT_MODELO);
        saco.setCantidad(DEFAULT_CANTIDAD);
        saco.setComentario(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createSaco() throws Exception {
        int databaseSizeBeforeCreate = sacoRepository.findAll().size();

        // Create the Saco

        restSacoMockMvc.perform(post("/api/sacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(saco)))
                .andExpect(status().isCreated());

        // Validate the Saco in the database
        List<Saco> sacos = sacoRepository.findAll();
        assertThat(sacos).hasSize(databaseSizeBeforeCreate + 1);
        Saco testSaco = sacos.get(sacos.size() - 1);
        assertThat(testSaco.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testSaco.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testSaco.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllSacos() throws Exception {
        // Initialize the database
        sacoRepository.saveAndFlush(saco);

        // Get all the sacos
        restSacoMockMvc.perform(get("/api/sacos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(saco.getId().intValue())))
                .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
                .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())));
    }

    @Test
    @Transactional
    public void getSaco() throws Exception {
        // Initialize the database
        sacoRepository.saveAndFlush(saco);

        // Get the saco
        restSacoMockMvc.perform(get("/api/sacos/{id}", saco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(saco.getId().intValue()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSaco() throws Exception {
        // Get the saco
        restSacoMockMvc.perform(get("/api/sacos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaco() throws Exception {
        // Initialize the database
        sacoRepository.saveAndFlush(saco);

		int databaseSizeBeforeUpdate = sacoRepository.findAll().size();

        // Update the saco
        saco.setModelo(UPDATED_MODELO);
        saco.setCantidad(UPDATED_CANTIDAD);
        saco.setComentario(UPDATED_COMENTARIO);

        restSacoMockMvc.perform(put("/api/sacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(saco)))
                .andExpect(status().isOk());

        // Validate the Saco in the database
        List<Saco> sacos = sacoRepository.findAll();
        assertThat(sacos).hasSize(databaseSizeBeforeUpdate);
        Saco testSaco = sacos.get(sacos.size() - 1);
        assertThat(testSaco.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testSaco.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testSaco.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void deleteSaco() throws Exception {
        // Initialize the database
        sacoRepository.saveAndFlush(saco);

		int databaseSizeBeforeDelete = sacoRepository.findAll().size();

        // Get the saco
        restSacoMockMvc.perform(delete("/api/sacos/{id}", saco.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Saco> sacos = sacoRepository.findAll();
        assertThat(sacos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
