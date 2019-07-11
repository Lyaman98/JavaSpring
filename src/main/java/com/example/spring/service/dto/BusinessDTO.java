package com.example.spring.service.dto;

import com.example.spring.domain.Advertisement;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Set;

public class BusinessDTO implements Serializable {

    private Long id;
    private String name;
    private String info;

    @JsonIgnore
    private Set<Advertisement> advertisements;

    public BusinessDTO(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public BusinessDTO(Long id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
    }

    public BusinessDTO() {
    }

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    @Override
    public String toString() {
        return "BusinessDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
