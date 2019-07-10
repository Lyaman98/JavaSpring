package com.example.spring.controller;

import com.example.spring.domain.Business;
import com.example.spring.repository.BusinessRepository;
import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
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

    @Test
    public void getAll() throws Exception{
        List<BusinessDTO> businessDTOList = Arrays.asList(new BusinessDTO(1L,"Facebook","Social Media"),
                new BusinessDTO(2L,"Google","Search engine"),
                new BusinessDTO(3L,"Twitter","Social networking system"));
        when(businessService.getAll()).thenReturn(businessDTOList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/business")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String result = "[{\"id\":1,\"name\":\"Facebook\",\"info\":\"Social Media\"}," +
                "{\"id\":2,\"name\":\"Google\",\"info\":\"Search engine\"}," +
                "{\"id\":3,\"name\":\"Twitter\",\"info\":\"Social networking system\"}]";

        JSONAssert.assertEquals(result,mvcResult.getResponse().getContentAsString(),false);
    }

}
