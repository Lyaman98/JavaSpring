package com.example.spring.domain;

import javax.persistence.*;
import java.util.Objects;
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

    public Business(String name, String info) {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Business business = (Business) o;
        return id.equals(business.id) &&
                Objects.equals(name, business.name) &&
                Objects.equals(info, business.info) &&
                Objects.equals(advertisements, business.advertisements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
