package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Calentador;
import com.mycompany.myapp.repository.CalentadorRepository;

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
 * Test class for the CalentadorResource REST controller.
 *
 * @see CalentadorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CalentadorResourceIntTest {

    private static final String DEFAULT_MODELO = "AAAAA";
    private static final String UPDATED_MODELO = "BBBBB";
    private static final String DEFAULT_GAS = "AAAAA";
    private static final String UPDATED_GAS = "BBBBB";

    private static final Integer DEFAULT_LITROS = 1;
    private static final Integer UPDATED_LITROS = 2;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    @Inject
    private CalentadorRepository calentadorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCalentadorMockMvc;

    private Calentador calentador;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalentadorResource calentadorResource = new CalentadorResource();
        ReflectionTestUtils.setField(calentadorResource, "calentadorRepository", calentadorRepository);
        this.restCalentadorMockMvc = MockMvcBuilders.standaloneSetup(calentadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        calentador = new Calentador();
        calentador.setModelo(DEFAULT_MODELO);
        calentador.setGas(DEFAULT_GAS);
        calentador.setLitros(DEFAULT_LITROS);
        calentador.setCantidad(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createCalentador() throws Exception {
        int databaseSizeBeforeCreate = calentadorRepository.findAll().size();

        // Create the Calentador

        restCalentadorMockMvc.perform(post("/api/calentadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(calentador)))
                .andExpect(status().isCreated());

        // Validate the Calentador in the database
        List<Calentador> calentadors = calentadorRepository.findAll();
        assertThat(calentadors).hasSize(databaseSizeBeforeCreate + 1);
        Calentador testCalentador = calentadors.get(calentadors.size() - 1);
        assertThat(testCalentador.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testCalentador.getGas()).isEqualTo(DEFAULT_GAS);
        assertThat(testCalentador.getLitros()).isEqualTo(DEFAULT_LITROS);
        assertThat(testCalentador.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllCalentadors() throws Exception {
        // Initialize the database
        calentadorRepository.saveAndFlush(calentador);

        // Get all the calentadors
        restCalentadorMockMvc.perform(get("/api/calentadors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(calentador.getId().intValue())))
                .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
                .andExpect(jsonPath("$.[*].gas").value(hasItem(DEFAULT_GAS.toString())))
                .andExpect(jsonPath("$.[*].litros").value(hasItem(DEFAULT_LITROS)))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));
    }

    @Test
    @Transactional
    public void getCalentador() throws Exception {
        // Initialize the database
        calentadorRepository.saveAndFlush(calentador);

        // Get the calentador
        restCalentadorMockMvc.perform(get("/api/calentadors/{id}", calentador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(calentador.getId().intValue()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.gas").value(DEFAULT_GAS.toString()))
            .andExpect(jsonPath("$.litros").value(DEFAULT_LITROS))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingCalentador() throws Exception {
        // Get the calentador
        restCalentadorMockMvc.perform(get("/api/calentadors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalentador() throws Exception {
        // Initialize the database
        calentadorRepository.saveAndFlush(calentador);

		int databaseSizeBeforeUpdate = calentadorRepository.findAll().size();

        // Update the calentador
        calentador.setModelo(UPDATED_MODELO);
        calentador.setGas(UPDATED_GAS);
        calentador.setLitros(UPDATED_LITROS);
        calentador.setCantidad(UPDATED_CANTIDAD);

        restCalentadorMockMvc.perform(put("/api/calentadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(calentador)))
                .andExpect(status().isOk());

        // Validate the Calentador in the database
        List<Calentador> calentadors = calentadorRepository.findAll();
        assertThat(calentadors).hasSize(databaseSizeBeforeUpdate);
        Calentador testCalentador = calentadors.get(calentadors.size() - 1);
        assertThat(testCalentador.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testCalentador.getGas()).isEqualTo(UPDATED_GAS);
        assertThat(testCalentador.getLitros()).isEqualTo(UPDATED_LITROS);
        assertThat(testCalentador.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void deleteCalentador() throws Exception {
        // Initialize the database
        calentadorRepository.saveAndFlush(calentador);

		int databaseSizeBeforeDelete = calentadorRepository.findAll().size();

        // Get the calentador
        restCalentadorMockMvc.perform(delete("/api/calentadors/{id}", calentador.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Calentador> calentadors = calentadorRepository.findAll();
        assertThat(calentadors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
