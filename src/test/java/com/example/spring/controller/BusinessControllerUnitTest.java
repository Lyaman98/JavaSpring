package com.example.spring.controller;

import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BusinessController.class)
public class BusinessControllerUnitTest {

    @MockBean
    BusinessService businessService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void saveBusiness() throws Exception {
        BusinessDTO businessDTO = new BusinessDTO("Facebook", "Social Media");
        when(businessService.save(isNull())).thenReturn(businessDTO);

        String content = "{\n" +
                "\t\"name\": \"Facebook\",\n" +
                "\t\"info\" : \"Social Media\"\n" +
                "\t\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/business/save")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getBusinessById() throws Exception {

        BusinessDTO business = new BusinessDTO(1L, "Facebook", "Social Media");
        Optional<BusinessDTO> optional = Optional.of(business);
        when(businessService.getById(1L)).thenReturn(optional);

        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/business/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String result = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Facebook\",\n" +
                "    \"info\": \"Social Media\"\n" +
                "}";

        JSONAssert.assertEquals(result, mockMvcResult.getResponse().getContentAsString(), false);
    }

    //completed - works
    @Test
    public void getAllBusinnesses() throws Exception {
        BusinessDTO businessDTO1 = new BusinessDTO(1L,"Business1", "Info1");
        BusinessDTO businessDTO2 = new BusinessDTO(2L,"Business2", "Info2");
        BusinessDTO businessDTO3 = new BusinessDTO(3L,"Business3", "Info3");

        List<BusinessDTO> businessDTOS = new ArrayList<>();
        businessDTOS.add(businessDTO1);
        businessDTOS.add(businessDTO2);
        businessDTOS.add(businessDTO3);

        when(businessService.getAll()).thenReturn(businessDTOS);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/business/all")
                                        .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                .andReturn();

        String data = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Business1\",\n" +
                "        \"info\": \"Info1\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Business2\",\n" +
                "        \"info\": \"Info2\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"Business3\",\n" +
                "        \"info\": \"Info3\"\n" +
                "    }\n" +
                "]";
        JSONAssert.assertEquals(data, result.getResponse().getContentAsString(), false);
    }

    // ********** TODO: ******not worked*****
    @Test
    public void updateBusiness() throws Exception {
        BusinessDTO businessDTO = new BusinessDTO(1L, "Business1", "Info1");

        when(businessService.save(businessDTO)).thenReturn(businessDTO);
        when(businessService.getById(1L)).thenReturn(Optional.of(businessDTO));

        String content = "{\n" +
                "\t\"id\": 1,\n" +
                "\t\"name\": \"Business2\",\n" +
                "\t\"info\" : \"Info2\"\n" +
                "\t\n" +
                "}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/business/update")
                                              .content(content)
                                              .contentType(MediaType.APPLICATION_JSON))
                                  .andExpect(status().isOk())
                                  .andReturn();
        System.out.println("*******************************************************************************");
        System.out.println(">>Result: " + result.getResponse().getContentAsString());
        JSONAssert.assertEquals(content, result.getResponse().getContentAsString(), false);
    }

    //completed - works
    @Test
    public void deleteById() throws Exception {
        BusinessDTO businessDTO = new BusinessDTO(1L,"Business1", "Info1");
        when(businessService.getById(1L)).thenReturn(Optional.of(businessDTO));

        String content = objectToString(businessDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/business/delete/1"))
                                  .andExpect(status().isNoContent())
                                  .andReturn();
        JSONAssert.assertEquals(content, result.getResponse().getContentAsString(), false);
    }


    //*****************************************************************
    private String objectToString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    private <T> T parseFromJson(String json, Class<T> data)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, data);
    }
}
