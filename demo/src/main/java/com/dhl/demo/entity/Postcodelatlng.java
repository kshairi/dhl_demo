package com.dhl.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "postcodelatlng", indexes = { @Index(name = "idx_postcodelatlng_postcode", columnList = "postcode", unique = true) })
public class Postcodelatlng {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String postcode;
    private String latitude;
    private String longitude;


    public Postcodelatlng() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
