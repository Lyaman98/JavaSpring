package com.example.spring.service.dto;

import com.example.spring.domain.Business;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class AdvertisementDTO implements Serializable {

    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    private Business business;

    public AdvertisementDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public AdvertisementDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public AdvertisementDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "AdvertisementDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +'}';
    }
}
