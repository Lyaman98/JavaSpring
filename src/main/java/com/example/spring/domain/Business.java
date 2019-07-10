package com.example.spring.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String info;

    @OneToMany
    private Set<Advertisement> advertisements;

    public Business(Long id, String name, String info) {

        this.id = id;
        this.name = name;
        this.info = info;
    }

    public Business() {
    }

    public Long getId(){
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

    public void addAdvertisement(Advertisement advertisement){
        this.advertisements.add(advertisement);

    }
    public void removeAdvertisement(Advertisement advertisement){
        this.advertisements.remove(advertisement);
    }

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
