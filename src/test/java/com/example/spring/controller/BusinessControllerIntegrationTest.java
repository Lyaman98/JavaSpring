package com.example.spring.controller;

import com.example.spring.Application;
import com.example.spring.service.dto.BusinessDTO;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

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
        ResponseEntity<BusinessDTO> dto = testRestTemplate.postForEntity(createURI("/api/business/save"), businessDTO, BusinessDTO.class);
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

    private String createURI(String uri) {
        return "http://localhost:" + localPort + uri;
    }

    @Test
    public void getAll() throws Exception{

        String result = "[{\"id\":1,\"name\":\"Facebook\",\"info\":\"Social Media\"}," +
                "{\"id\":2,\"name\":\"Twitter\",\"info\":\"Social networking system\"}," +
                "{\"id\":3,\"name\":\"Google\",\"info\":\"Search engine\"}]";

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(createURI("/api/business"),String.class);

        JSONAssert.assertEquals(result,responseEntity.getBody(),false);

    }
//
//    @Test
//    public void delete() throws Exception{
//
//       testRestTemplate.delete(createURI("/api/business/1"));
//        ResponseEntity<String> result = testRestTemplate.getForEntity(createURI("/api/business/1"), String.class);
//
//        assertNull(result);
//
//
//    }

}
