package com.example.spring.service.impl;

import com.example.spring.domain.Business;
import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.BusinessDTO;
import com.example.spring.repository.AdvertisementRepository;
import com.example.spring.repository.BusinessRepository;
import com.example.spring.service.mapper.BusinessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private BusinessRepository businessRepository;
    private AdvertisementRepository advertisementRepository;
    private BusinessMapper businessMapper;
    private Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);

    public BusinessServiceImpl(BusinessRepository businessRepository, AdvertisementRepository advertisementRepository, BusinessMapper businessMapper) {
        this.businessRepository = businessRepository;
        this.advertisementRepository = advertisementRepository;
        this.businessMapper = businessMapper;
    }


    @Override
    public BusinessDTO save(BusinessDTO businessDTO) {
        logger.debug("Saving business");
        Business business = businessMapper.toEntity(businessDTO);
        businessRepository.save(business);
        return businessMapper.toDTO(business);
    }

    @Override
    public Optional<BusinessDTO> getById(Long id) {
        return businessRepository.findById(id)
                .map(businessMapper::toDTO);
    }


    @Override
    public List<BusinessDTO> getAll() {
        List<Business> businesses = businessRepository.findAll();

        //could write this in mapper

//        List<BusinessDTO> businessDTOS = new ArrayList<>();
//        for(Business business : businesses){
//            if(business != null){
//                businessDTOS.add(businessMapper.toDTO(business));
//            }
//        }
//        return businessDTOS;

        return businessMapper.listToDTO(businesses);
    }

    @Override
    public void deleteById(Long id) {
        businessRepository.deleteById(id);
    }


    @Override
    public void delete(BusinessDTO businessDto) {
        Business business = businessMapper.toEntity(businessDto);
        businessRepository.delete(business);
    }
}
