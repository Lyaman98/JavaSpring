package com.example.spring.service.impl;

import com.example.spring.domain.Advertisement;
import com.example.spring.domain.Business;
import com.example.spring.exceptions.BadRequestException;
import com.example.spring.service.BusinessService;
import com.example.spring.service.dto.AdvertisementDTO;
import com.example.spring.service.dto.BusinessDTO;
import com.example.spring.repository.AdvertisementRepository;
import com.example.spring.repository.BusinessRepository;
import com.example.spring.service.mapper.AdvertisementMapper;
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
    private AdvertisementMapper advertisementMapper;
    private Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);

    public BusinessServiceImpl(BusinessRepository businessRepository, AdvertisementRepository advertisementRepository, BusinessMapper businessMapper, AdvertisementMapper advertisementMapper) {
        this.businessRepository = businessRepository;
        this.advertisementRepository = advertisementRepository;
        this.businessMapper = businessMapper;
        this.advertisementMapper = advertisementMapper;
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
        List<BusinessDTO> businessDTOS = new ArrayList<>();
        for(Business business : businesses){
            if(business != null){
                businessDTOS.add(businessMapper.toDTO(business));
            }
        }
        return businessDTOS;
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

    //******new methods for advertisement creation, removing
    @Override
    public void addAdvertisement(Long businessId, AdvertisementDTO advertisementDTO) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(()-> {throw new BadRequestException("Business not found with given id: " + businessId);});

        business.addAdvertisement(advertisementMapper.toEntity(advertisementDTO));
        businessRepository.save(business);
    }

    @Override
    public void removeAdvertisement(Long businessId, Long advertisementId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(()-> {throw new BadRequestException("Business not found with given id: " + businessId);});

        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(()->{throw new BadRequestException("Advertisement not found with given id:");});
        //remove advertisement
        business.removeAdvertisement(advertisement);

        businessRepository.save(business);
    }
}
