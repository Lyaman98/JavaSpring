package com.example.spring.controller;

import com.example.spring.Application;
import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(value = "test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusinessControllerIntegrationTest {

    @LocalServerPort
    private int localPort;

    private MockMvc mockMvc;

    @Autowired
    BusinessService businessService;


    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    private BusinessDTO businessDTO;

    @Before
    public void setup() {
        businessDTO = new BusinessDTO(1L, "Google", "Social Media");
        mockMvc = MockMvcBuilders.standaloneSetup(new BusinessController(businessService)).build();
    }

    //test to save mock Business object
    @Test
    public void save() {

        ResponseEntity<BusinessDTO> dto = testRestTemplate.postForEntity(createURI("/api/business"),
                businessDTO, BusinessDTO.class);
        assertThat(dto.getStatusCode(), equalTo(HttpStatus.CREATED));
    }


    //test to get mock Business by id
    @Test
    public void getBusinessById() throws JSONException {

        String result = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Facebook\",\n" +
                "    \"info\": \"Social Media\"\n" +
                "}";

        ResponseEntity<String> dto = testRestTemplate.getForEntity(createURI("/api/business/1"), String.class);
        //JSONAssert converts your string into JSON object and the compares
        JSONAssert.assertEquals(result, dto.getBody(), false);
    }

    @Test
    public void getBusinesses() throws Exception {

        //https://restfulapi.net/json-jsonpath/
        //you can use jsonPath to check the fields

        mockMvc.perform(get("/api/business"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id").value(hasItem(businessDTO.getId().intValue())));
    }

    //TODO: *****not worked*******
    @Test
    public void getAll() throws JSONException {

        ResponseEntity<String> result = testRestTemplate.getForEntity(createURI("/api/business"), String.class);
        String content = "[{\"id\":1,\"name\":\"Facebook\",\"info\":\"Social Media\"}]";

        assertEquals(200, result.getStatusCodeValue());
        JSONAssert.assertEquals(content, result.getBody(), false);

        //will compare by reference (will use default equals method)

//        ResponseEntity<List<BusinessDTO>> response = testRestTemplate.exchange(createURI("/api/business"),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<BusinessDTO>>() {
//                });
//
//        BusinessDTO businessDTO = new BusinessDTO(1L, "Facebook", "Social Media");
//        List<BusinessDTO> businessDTOList = new ArrayList<>();
//        businessDTOList.add(businessDTO);
//
//
//        Assert.assertEquals(200, response.getStatusCodeValue());
//        Assert.assertEquals(businessDTOList,response.getBody());
    }

    @Test
    public void findAll() throws Exception {

    }

    //TODO: ******not worked********
    @Test
    public void updateBusiness() {
        BusinessDTO request = new BusinessDTO(1L, "Youtube", "Social Media");

        ResponseEntity<BusinessDTO> response = testRestTemplate.exchange(createURI("/api/business"),
                HttpMethod.PUT, new HttpEntity<>(request), BusinessDTO.class);

        assertEquals(request, response.getBody());
    }

    //completed - works
    @Test
    public void deleteBusiness() {

        ResponseEntity<BusinessDTO> response = testRestTemplate.exchange(createURI("/api/business/1"),
                HttpMethod.DELETE, null, BusinessDTO.class);

        BusinessDTO businessDTO = new BusinessDTO(1L, "Facebook", "Social Media");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(String.valueOf(1L), String.valueOf(response.getBody().getId()));
        assertEquals("Facebook", response.getBody().getName());
        assertEquals("Social Media", response.getBody().getInfo());
    }

    //***********************************
    private String createURI(String uri) {
        return "http://localhost:" + localPort + uri;
    }
}
