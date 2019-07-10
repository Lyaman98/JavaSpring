package com.example.spring.service;


import com.example.spring.service.dto.AdvertisementDTO;
import com.example.spring.service.dto.BusinessDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BusinessService {

    BusinessDTO save(BusinessDTO businessDTO);
    Optional<BusinessDTO> getById(Long id);
    List<BusinessDTO> getAll();
    void deleteById(Long id);
    void delete(BusinessDTO businessDto);

}
