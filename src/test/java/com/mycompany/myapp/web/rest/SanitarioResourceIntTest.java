package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Sanitario;
import com.mycompany.myapp.repository.SanitarioRepository;

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


/**
 * Test class for the SanitarioResource REST controller.
 *
 * @see SanitarioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SanitarioResourceIntTest {

    private static final String DEFAULT_MODELO = "AAAAA";
    private static final String UPDATED_MODELO = "BBBBB";
    private static final String DEFAULT_MEDIDAS = "AAAAA";
    private static final String UPDATED_MEDIDAS = "BBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    @Inject
    private SanitarioRepository sanitarioRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSanitarioMockMvc;

    private Sanitario sanitario;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SanitarioResource sanitarioResource = new SanitarioResource();
        ReflectionTestUtils.setField(sanitarioResource, "sanitarioRepository", sanitarioRepository);
        this.restSanitarioMockMvc = MockMvcBuilders.standaloneSetup(sanitarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sanitario = new Sanitario();
        sanitario.setModelo(DEFAULT_MODELO);
        sanitario.setMedidas(DEFAULT_MEDIDAS);
        sanitario.setCantidad(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createSanitario() throws Exception {
        int databaseSizeBeforeCreate = sanitarioRepository.findAll().size();

        // Create the Sanitario

        restSanitarioMockMvc.perform(post("/api/sanitarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sanitario)))
                .andExpect(status().isCreated());

        // Validate the Sanitario in the database
        List<Sanitario> sanitarios = sanitarioRepository.findAll();
        assertThat(sanitarios).hasSize(databaseSizeBeforeCreate + 1);
        Sanitario testSanitario = sanitarios.get(sanitarios.size() - 1);
        assertThat(testSanitario.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testSanitario.getMedidas()).isEqualTo(DEFAULT_MEDIDAS);
        assertThat(testSanitario.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllSanitarios() throws Exception {
        // Initialize the database
        sanitarioRepository.saveAndFlush(sanitario);

        // Get all the sanitarios
        restSanitarioMockMvc.perform(get("/api/sanitarios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sanitario.getId().intValue())))
                .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
                .andExpect(jsonPath("$.[*].medidas").value(hasItem(DEFAULT_MEDIDAS.toString())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));
    }

    @Test
    @Transactional
    public void getSanitario() throws Exception {
        // Initialize the database
        sanitarioRepository.saveAndFlush(sanitario);

        // Get the sanitario
        restSanitarioMockMvc.perform(get("/api/sanitarios/{id}", sanitario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sanitario.getId().intValue()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.medidas").value(DEFAULT_MEDIDAS.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingSanitario() throws Exception {
        // Get the sanitario
        restSanitarioMockMvc.perform(get("/api/sanitarios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSanitario() throws Exception {
        // Initialize the database
        sanitarioRepository.saveAndFlush(sanitario);

		int databaseSizeBeforeUpdate = sanitarioRepository.findAll().size();

        // Update the sanitario
        sanitario.setModelo(UPDATED_MODELO);
        sanitario.setMedidas(UPDATED_MEDIDAS);
        sanitario.setCantidad(UPDATED_CANTIDAD);

        restSanitarioMockMvc.perform(put("/api/sanitarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sanitario)))
                .andExpect(status().isOk());

        // Validate the Sanitario in the database
        List<Sanitario> sanitarios = sanitarioRepository.findAll();
        assertThat(sanitarios).hasSize(databaseSizeBeforeUpdate);
        Sanitario testSanitario = sanitarios.get(sanitarios.size() - 1);
        assertThat(testSanitario.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testSanitario.getMedidas()).isEqualTo(UPDATED_MEDIDAS);
        assertThat(testSanitario.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void deleteSanitario() throws Exception {
        // Initialize the database
        sanitarioRepository.saveAndFlush(sanitario);

		int databaseSizeBeforeDelete = sanitarioRepository.findAll().size();

        // Get the sanitario
        restSanitarioMockMvc.perform(delete("/api/sanitarios/{id}", sanitario.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Sanitario> sanitarios = sanitarioRepository.findAll();
        assertThat(sanitarios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
