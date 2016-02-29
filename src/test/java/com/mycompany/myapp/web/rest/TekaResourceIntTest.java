package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Teka;
import com.mycompany.myapp.repository.TekaRepository;

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
 * Test class for the TekaResource REST controller.
 *
 * @see TekaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TekaResourceIntTest {

    private static final String DEFAULT_TIPO = "AAAAA";
    private static final String UPDATED_TIPO = "BBBBB";
    private static final String DEFAULT_MODELO = "AAAAA";
    private static final String UPDATED_MODELO = "BBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    @Inject
    private TekaRepository tekaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTekaMockMvc;

    private Teka teka;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TekaResource tekaResource = new TekaResource();
        ReflectionTestUtils.setField(tekaResource, "tekaRepository", tekaRepository);
        this.restTekaMockMvc = MockMvcBuilders.standaloneSetup(tekaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        teka = new Teka();
        teka.setTipo(DEFAULT_TIPO);
        teka.setModelo(DEFAULT_MODELO);
        teka.setCantidad(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createTeka() throws Exception {
        int databaseSizeBeforeCreate = tekaRepository.findAll().size();

        // Create the Teka

        restTekaMockMvc.perform(post("/api/tekas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teka)))
                .andExpect(status().isCreated());

        // Validate the Teka in the database
        List<Teka> tekas = tekaRepository.findAll();
        assertThat(tekas).hasSize(databaseSizeBeforeCreate + 1);
        Teka testTeka = tekas.get(tekas.size() - 1);
        assertThat(testTeka.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTeka.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testTeka.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllTekas() throws Exception {
        // Initialize the database
        tekaRepository.saveAndFlush(teka);

        // Get all the tekas
        restTekaMockMvc.perform(get("/api/tekas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(teka.getId().intValue())))
                .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
                .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));
    }

    @Test
    @Transactional
    public void getTeka() throws Exception {
        // Initialize the database
        tekaRepository.saveAndFlush(teka);

        // Get the teka
        restTekaMockMvc.perform(get("/api/tekas/{id}", teka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(teka.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingTeka() throws Exception {
        // Get the teka
        restTekaMockMvc.perform(get("/api/tekas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeka() throws Exception {
        // Initialize the database
        tekaRepository.saveAndFlush(teka);

		int databaseSizeBeforeUpdate = tekaRepository.findAll().size();

        // Update the teka
        teka.setTipo(UPDATED_TIPO);
        teka.setModelo(UPDATED_MODELO);
        teka.setCantidad(UPDATED_CANTIDAD);

        restTekaMockMvc.perform(put("/api/tekas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teka)))
                .andExpect(status().isOk());

        // Validate the Teka in the database
        List<Teka> tekas = tekaRepository.findAll();
        assertThat(tekas).hasSize(databaseSizeBeforeUpdate);
        Teka testTeka = tekas.get(tekas.size() - 1);
        assertThat(testTeka.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTeka.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testTeka.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void deleteTeka() throws Exception {
        // Initialize the database
        tekaRepository.saveAndFlush(teka);

		int databaseSizeBeforeDelete = tekaRepository.findAll().size();

        // Get the teka
        restTekaMockMvc.perform(delete("/api/tekas/{id}", teka.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Teka> tekas = tekaRepository.findAll();
        assertThat(tekas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
