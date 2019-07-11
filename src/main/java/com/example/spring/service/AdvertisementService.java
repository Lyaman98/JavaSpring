package com.example.spring.service;

import com.example.spring.service.dto.AdvertisementDTO;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    Optional<AdvertisementDTO> getById(Long id);
    List<AdvertisementDTO> getAll();

}
