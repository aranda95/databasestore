package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Plato;
import com.mycompany.myapp.repository.PlatoRepository;

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
 * Test class for the PlatoResource REST controller.
 *
 * @see PlatoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlatoResourceIntTest {

    private static final String DEFAULT_MARCA = "AAAAA";
    private static final String UPDATED_MARCA = "BBBBB";
    private static final String DEFAULT_COLOR = "AAAAA";
    private static final String UPDATED_COLOR = "BBBBB";
    private static final String DEFAULT_MEDIDAS = "AAAAA";
    private static final String UPDATED_MEDIDAS = "BBBBB";
    private static final String DEFAULT_COMENTARIO = "AAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    @Inject
    private PlatoRepository platoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPlatoMockMvc;

    private Plato plato;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlatoResource platoResource = new PlatoResource();
        ReflectionTestUtils.setField(platoResource, "platoRepository", platoRepository);
        this.restPlatoMockMvc = MockMvcBuilders.standaloneSetup(platoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        plato = new Plato();
        plato.setMarca(DEFAULT_MARCA);
        plato.setColor(DEFAULT_COLOR);
        plato.setMedidas(DEFAULT_MEDIDAS);
        plato.setComentario(DEFAULT_COMENTARIO);
        plato.setCantidad(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createPlato() throws Exception {
        int databaseSizeBeforeCreate = platoRepository.findAll().size();

        // Create the Plato

        restPlatoMockMvc.perform(post("/api/platos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plato)))
                .andExpect(status().isCreated());

        // Validate the Plato in the database
        List<Plato> platos = platoRepository.findAll();
        assertThat(platos).hasSize(databaseSizeBeforeCreate + 1);
        Plato testPlato = platos.get(platos.size() - 1);
        assertThat(testPlato.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testPlato.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testPlato.getMedidas()).isEqualTo(DEFAULT_MEDIDAS);
        assertThat(testPlato.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testPlato.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllPlatos() throws Exception {
        // Initialize the database
        platoRepository.saveAndFlush(plato);

        // Get all the platos
        restPlatoMockMvc.perform(get("/api/platos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(plato.getId().intValue())))
                .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
                .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
                .andExpect(jsonPath("$.[*].medidas").value(hasItem(DEFAULT_MEDIDAS.toString())))
                .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));
    }

    @Test
    @Transactional
    public void getPlato() throws Exception {
        // Initialize the database
        platoRepository.saveAndFlush(plato);

        // Get the plato
        restPlatoMockMvc.perform(get("/api/platos/{id}", plato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(plato.getId().intValue()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.medidas").value(DEFAULT_MEDIDAS.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingPlato() throws Exception {
        // Get the plato
        restPlatoMockMvc.perform(get("/api/platos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlato() throws Exception {
        // Initialize the database
        platoRepository.saveAndFlush(plato);

		int databaseSizeBeforeUpdate = platoRepository.findAll().size();

        // Update the plato
        plato.setMarca(UPDATED_MARCA);
        plato.setColor(UPDATED_COLOR);
        plato.setMedidas(UPDATED_MEDIDAS);
        plato.setComentario(UPDATED_COMENTARIO);
        plato.setCantidad(UPDATED_CANTIDAD);

        restPlatoMockMvc.perform(put("/api/platos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plato)))
                .andExpect(status().isOk());

        // Validate the Plato in the database
        List<Plato> platos = platoRepository.findAll();
        assertThat(platos).hasSize(databaseSizeBeforeUpdate);
        Plato testPlato = platos.get(platos.size() - 1);
        assertThat(testPlato.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testPlato.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testPlato.getMedidas()).isEqualTo(UPDATED_MEDIDAS);
        assertThat(testPlato.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testPlato.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void deletePlato() throws Exception {
        // Initialize the database
        platoRepository.saveAndFlush(plato);

		int databaseSizeBeforeDelete = platoRepository.findAll().size();

        // Get the plato
        restPlatoMockMvc.perform(delete("/api/platos/{id}", plato.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plato> platos = platoRepository.findAll();
        assertThat(platos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
