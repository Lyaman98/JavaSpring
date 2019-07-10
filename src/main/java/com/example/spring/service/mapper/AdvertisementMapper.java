package com.example.spring.service.mapper;

import com.example.spring.domain.Advertisement;
import com.example.spring.service.dto.AdvertisementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {Advertisement.class})
public interface AdvertisementMapper {

    AdvertisementDTO toDTO(Advertisement advertisement);

    @Mapping(target = "business", ignore = false)
    Advertisement toEntity(AdvertisementDTO advertisementDTO);
}
