package com.example.spring.controller;

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
@RequestMapping("/api/business")
public class BusinessController {

    private BusinessService businessService;
    private Logger logger = LoggerFactory.getLogger(BusinessController.class);


    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping
    public ResponseEntity<BusinessDTO> saveBusiness(@RequestBody BusinessDTO businessDTO) {
        logger.debug("Rest request to save business");
        if (businessDTO.getId() != null){
            throw new BadRequestException("Business already exists");
        }
        businessDTO = businessService.save(businessDTO);
        return new ResponseEntity<>(businessDTO,HttpStatus.CREATED);
    }

    //get according to id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<BusinessDTO>> getBusinessById(@PathVariable Long id) {
        if (id == null){
            throw new BadRequestException("Enter valid id");
        }
        Optional<BusinessDTO> result = businessService.getById(id);
        return ResponseEntity.ok().body(result);
    }

    //get all
    @GetMapping
    public ResponseEntity<List<BusinessDTO>> getAllBusinnesses(){
        return new ResponseEntity<List<BusinessDTO>>(businessService.getAll(), HttpStatus.OK);
    }

    //updating business without id path -> it requires {"id" : value, "name":value, "info": value}
    @PutMapping
    public ResponseEntity<BusinessDTO> updateBusiness(@RequestBody BusinessDTO businessDTO){
        Long id = businessDTO.getId();
        if(id == null) {
            throw new BadRequestException("Id not defined");
        }
//        Optional<BusinessDTO> result = businessService.getById(id);
//        if(!result.isPresent()) {
//            throw new BadRequestException("Business not found with given id: " + id);
//        }
        businessService.getById(id).orElseThrow(()->new BadRequestException("Business not found with given id: " + id));
        businessDTO = businessService.save(businessDTO);
        return new ResponseEntity<>(businessDTO, HttpStatus.OK);
    }

    //delete business according to id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BusinessDTO> deleteById(@PathVariable Long id){
        Optional<BusinessDTO> businessDTO = businessService.getById(id);
        if(!businessDTO.isPresent())
            throw new BadRequestException("Business not found with id: " + id) ;

        businessService.deleteById(id);
        return new ResponseEntity<>(businessDTO.get(), HttpStatus.OK);
    }

}
