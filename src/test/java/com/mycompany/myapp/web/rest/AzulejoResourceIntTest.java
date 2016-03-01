package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Azulejo;
import com.mycompany.myapp.repository.AzulejoRepository;

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
 * Test class for the AzulejoResource REST controller.
 *
 * @see AzulejoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AzulejoResourceIntTest {

    private static final String DEFAULT_MARCA = "AAAAA";
    private static final String UPDATED_MARCA = "BBBBB";

    private static final Integer DEFAULT_M2 = 1;
    private static final Integer UPDATED_M2 = 2;
    private static final String DEFAULT_DESCRIPCION = "AAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBB";

    @Inject
    private AzulejoRepository azulejoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAzulejoMockMvc;

    private Azulejo azulejo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AzulejoResource azulejoResource = new AzulejoResource();
        ReflectionTestUtils.setField(azulejoResource, "azulejoRepository", azulejoRepository);
        this.restAzulejoMockMvc = MockMvcBuilders.standaloneSetup(azulejoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        azulejo = new Azulejo();
        azulejo.setMarca(DEFAULT_MARCA);
        azulejo.setm2(DEFAULT_M2);
        azulejo.setDescripcion(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createAzulejo() throws Exception {
        int databaseSizeBeforeCreate = azulejoRepository.findAll().size();

        // Create the Azulejo

        restAzulejoMockMvc.perform(post("/api/azulejos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(azulejo)))
                .andExpect(status().isCreated());

        // Validate the Azulejo in the database
        List<Azulejo> azulejos = azulejoRepository.findAll();
        assertThat(azulejos).hasSize(databaseSizeBeforeCreate + 1);
        Azulejo testAzulejo = azulejos.get(azulejos.size() - 1);
        assertThat(testAzulejo.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testAzulejo.getm2()).isEqualTo(DEFAULT_M2);
        assertThat(testAzulejo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAzulejos() throws Exception {
        // Initialize the database
        azulejoRepository.saveAndFlush(azulejo);

        // Get all the azulejos
        restAzulejoMockMvc.perform(get("/api/azulejos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(azulejo.getId().intValue())))
                .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
                .andExpect(jsonPath("$.[*].m2").value(hasItem(DEFAULT_M2)))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getAzulejo() throws Exception {
        // Initialize the database
        azulejoRepository.saveAndFlush(azulejo);

        // Get the azulejo
        restAzulejoMockMvc.perform(get("/api/azulejos/{id}", azulejo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(azulejo.getId().intValue()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()))
            .andExpect(jsonPath("$.m2").value(DEFAULT_M2))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAzulejo() throws Exception {
        // Get the azulejo
        restAzulejoMockMvc.perform(get("/api/azulejos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAzulejo() throws Exception {
        // Initialize the database
        azulejoRepository.saveAndFlush(azulejo);

		int databaseSizeBeforeUpdate = azulejoRepository.findAll().size();

        // Update the azulejo
        azulejo.setMarca(UPDATED_MARCA);
        azulejo.setm2(UPDATED_M2);
        azulejo.setDescripcion(UPDATED_DESCRIPCION);

        restAzulejoMockMvc.perform(put("/api/azulejos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(azulejo)))
                .andExpect(status().isOk());

        // Validate the Azulejo in the database
        List<Azulejo> azulejos = azulejoRepository.findAll();
        assertThat(azulejos).hasSize(databaseSizeBeforeUpdate);
        Azulejo testAzulejo = azulejos.get(azulejos.size() - 1);
        assertThat(testAzulejo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testAzulejo.getm2()).isEqualTo(UPDATED_M2);
        assertThat(testAzulejo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void deleteAzulejo() throws Exception {
        // Initialize the database
        azulejoRepository.saveAndFlush(azulejo);

		int databaseSizeBeforeDelete = azulejoRepository.findAll().size();

        // Get the azulejo
        restAzulejoMockMvc.perform(delete("/api/azulejos/{id}", azulejo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Azulejo> azulejos = azulejoRepository.findAll();
        assertThat(azulejos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
