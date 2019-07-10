package com.example.spring.controller;

import com.example.spring.exceptions.BadRequestException;
import com.example.spring.service.AdvertisementService;
import com.example.spring.service.dto.AdvertisementDTO;
import com.example.spring.service.mapper.AdvertisementMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final AdvertisementMapper advertisementMapper;

    public AdvertisementController(AdvertisementService advertisementService, AdvertisementMapper advertisementMapper) {
        this.advertisementService = advertisementService;
        this.advertisementMapper = advertisementMapper;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<AdvertisementDTO>> getAll(){
        return new ResponseEntity<>(advertisementService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisementById(@PathVariable Long id){
        Optional<AdvertisementDTO> advertisementDTO = advertisementService.getById(id);
        if(!advertisementDTO.isPresent())
            throw new BadRequestException("Advertisement not found with given id");

        return new ResponseEntity<>(advertisementDTO.get(), HttpStatus.OK);
    }

}
