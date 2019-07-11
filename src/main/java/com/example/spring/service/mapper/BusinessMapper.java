package com.example.spring.service.mapper;

import com.example.spring.domain.Business;
import com.example.spring.service.dto.BusinessDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.swing.text.html.parser.Entity;

/**
 * Mapper for the entity Business and its DTO BusinessDTO.
 */

@Mapper(componentModel = "spring", uses = {Business.class})
public interface BusinessMapper  {

    BusinessDTO toDTO(Business business);

    @Mapping(target = "advertisements", ignore = false)
    Business toEntity(BusinessDTO businessDTO);
}
