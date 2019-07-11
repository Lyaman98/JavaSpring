package com.example.spring.service.impl;

import com.example.spring.domain.Advertisement;
import com.example.spring.repository.AdvertisementRepository;
import com.example.spring.service.AdvertisementService;
import com.example.spring.service.dto.AdvertisementDTO;
import com.example.spring.service.mapper.AdvertisementMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, AdvertisementMapper advertisementMapper) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementMapper = advertisementMapper;
    }

    //completed
    @Override
    public Optional<AdvertisementDTO> getById(Long id) {
        return advertisementRepository.findById(id)
                                      .map(advertisementMapper::toDTO);
    }

    //completed
    @Override
    public List<AdvertisementDTO> getAll() {
        List<Advertisement> advertisements = advertisementRepository.findAll();
        List<AdvertisementDTO> advertisementDTOS = new ArrayList<>();
        for(Advertisement advertisement : advertisements){
            advertisementDTOS.add(advertisementMapper.toDTO(advertisement));
        }
        return advertisementDTOS;
    }

}
