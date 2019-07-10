package com.example.spring.controller;

import com.example.spring.Application;
import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles(value = "test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusinessControllerIntegrationTest {

    @LocalServerPort
    private int localPort;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    //test to save mock Business object
    @Test
    public void save() {

        BusinessDTO businessDTO = new BusinessDTO("Facebook", "Social Media");
        ResponseEntity<BusinessDTO> dto = testRestTemplate.postForEntity(createURI("/api/business/save"),
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

    //TODO: *****not worked*******
    @Test
    public void getAll() throws JSONException {

        ResponseEntity<List<BusinessDTO>> response = testRestTemplate.exchange(createURI("/api/business/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BusinessDTO>>() {
                });

        BusinessDTO businessDTO = new BusinessDTO(1L, "Facebook", "Social Media");
        List<BusinessDTO> businessDTOList = new ArrayList<>();
        businessDTOList.add(businessDTO);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(businessDTOList, response.getBody());
    }

    //TODO: ******not worked********
    @Test
    public void updateBusiness(){
        BusinessDTO request = new BusinessDTO(1L, "Youtube", "Social Media");

        ResponseEntity<BusinessDTO> response = testRestTemplate.exchange(createURI("/api/business/update"),
                HttpMethod.PUT, new HttpEntity<>(request), BusinessDTO.class);

        Assert.assertEquals(request, response.getBody());
    }

    //completed - works
    @Test
    public void deleteBusiness(){

        ResponseEntity<BusinessDTO> response = testRestTemplate.exchange(createURI("/api/business/delete/1"),
                HttpMethod.DELETE, null, BusinessDTO.class);

        BusinessDTO businessDTO = new BusinessDTO(1L, "Facebook", "Social Media");

        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertEquals(String.valueOf(1L), String.valueOf(response.getBody().getId()));
        Assert.assertEquals("Facebook", response.getBody().getName());
        Assert.assertEquals("Social Media", response.getBody().getInfo());
    }

    //***********************************
    private String createURI(String uri) {
        return "http://localhost:" + localPort + uri;
    }
}
