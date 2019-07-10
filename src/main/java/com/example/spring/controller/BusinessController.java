package com.example.spring.controller;


import com.example.spring.domain.Business;
import com.example.spring.exceptions.BadRequestException;
import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BusinessController {

    private BusinessService businessService;
    private Logger logger = LoggerFactory.getLogger(BusinessController.class);


    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping("/business/save")
    public ResponseEntity<BusinessDTO> saveBusiness(@RequestBody BusinessDTO businessDTO) {
        logger.debug("Rest request to save business");
        if (businessDTO.getId() != null){
            throw new BadRequestException("Business already exists");
        }
        businessService.save(businessDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/business/{id}")
    public ResponseEntity<Optional<BusinessDTO>> getBusinessById(@PathVariable Long id) {
        if (id == null){
            throw new BadRequestException("Enter valid id");
        }

        Optional<BusinessDTO> result = businessService.getById(id);

        return ResponseEntity.ok().body(result);

    }
    @GetMapping("/business")
    public ResponseEntity<List<BusinessDTO>> findAll(){
        List<BusinessDTO> result = businessService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/business")
    public ResponseEntity<BusinessDTO> update(@RequestBody BusinessDTO businessDTO){
        if (businessDTO.getId() == null){
            throw new BadRequestException("Id can't be null");
        }

        businessService.save(businessDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/business/{id}")
    public ResponseEntity<BusinessDTO> deleteById(@PathVariable Long id){
        if (id == null){
            throw new BadRequestException("Id can not be null");
        }
        businessService.delete(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Application Name - ", "JavaSpring");
        httpHeaders.add("Application Params - " , id.toString());

        return ResponseEntity.ok().headers(httpHeaders).build();
    }

}
