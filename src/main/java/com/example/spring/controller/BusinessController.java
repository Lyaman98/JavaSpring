package com.example.spring.controller;

import com.example.spring.domain.Advertisement;
import com.example.spring.domain.Business;
import com.example.spring.exceptions.BadRequestException;
import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.AdvertisementDTO;
import com.example.spring.service.dto.BusinessDTO;
import com.example.spring.service.mapper.AdvertisementMapper;
import com.example.spring.service.mapper.BusinessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;
    private final Logger logger = LoggerFactory.getLogger(BusinessController.class);
    private final BusinessMapper businessMapper;

    private final AdvertisementMapper advertisementMapper;

    public BusinessController(BusinessService businessService, BusinessMapper businessMapper,
                              AdvertisementMapper advertisementMapper) {
        this.businessService = businessService;
        this.businessMapper = businessMapper;
        this.advertisementMapper = advertisementMapper;
    }

    @PostMapping(value = "/save")
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
    @GetMapping(value = "/all")
    public ResponseEntity<List<BusinessDTO>> getAllBusinnesses(){
        return new ResponseEntity<List<BusinessDTO>>(businessService.getAll(), HttpStatus.OK);
    }

    //updating business without id path -> it requires {"id" : value, "name":value, "info": value}
    @PutMapping(value = "/update")
    public ResponseEntity<BusinessDTO> updateBusiness(@RequestBody BusinessDTO businessDTO){
        Long id = businessDTO.getId();
        if(id == null) throw  new BadRequestException("Id not defined");

        Optional<BusinessDTO> bDTO = businessService.getById(id);
        if(!bDTO.isPresent()) throw new BadRequestException("Business not found with given id to update: " + id);

        return new ResponseEntity<>(businessService.save(businessDTO), HttpStatus.OK);
    }

    //delete business according to id
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BusinessDTO> deleteById(@PathVariable Long id){
        Optional<BusinessDTO> businessDTO = businessService.getById(id);
        if(!businessDTO.isPresent())
            throw new BadRequestException("Business not found with id to delete: " + id) ;

        businessService.deleteById(id);
        return new ResponseEntity<>(businessDTO.get(), HttpStatus.NO_CONTENT);
    }

    //********************relationship with advertisement********************

    @GetMapping(value = "/{bId}/advertisement/all")
    public ResponseEntity<Set<AdvertisementDTO>> getAdvertisementOfBusinessById(@PathVariable Long bId) {
        Optional<BusinessDTO> businessDTO = businessService.getById(bId);
        if (!businessDTO.isPresent())
            throw new BadRequestException("Business not found");

        Business business = businessMapper.toEntity(businessDTO.get());

        Set<AdvertisementDTO> advertisementDTOS = new HashSet<>();
        Set<Advertisement> advertisements = business.getAdvertisements();

        logger.info(">>Advertisements: " + advertisements);

        if(advertisements == null)
            throw new BadRequestException("Advertisement of given Business not found");

        advertisements.forEach((b)-> advertisementDTOS.add(advertisementMapper.toDTO(b)));

        return new ResponseEntity<>(advertisementDTOS, HttpStatus.OK);
    }


    @PostMapping(value = "/{bId}/advertisement/save")
    public ResponseEntity<AdvertisementDTO> addAdvertisementForBusiness(@RequestBody AdvertisementDTO advertisementDTO,
                                                                        @PathVariable Long bId){
        Optional<BusinessDTO> businessDTO = businessService.getById(bId);
        if(!businessDTO.isPresent())
            throw new BadRequestException("Business not found to add new ads");

        businessService.addAdvertisement(bId, advertisementDTO);

        return new ResponseEntity<>(advertisementDTO, HttpStatus.CREATED);
    }

}
