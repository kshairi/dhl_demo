package com.dhl.demo.model;

import java.util.List;

public class PostcodeDistanceRequest {

    public static final String MILES = "MILES";
    public static final String KM = "KM";
    public static final String METERS = "METERS";
    public static final String YARDS = "YARDS";
    public static final String FEET = "FEET";
    public static final String NAUTICAL_MILES = "NAUTICAL_MILES";

    public static final String RADIUS_KM = "6371";
    public static final String RADIUS_MILES = "3963";
    public static final String RADIUS_METERS = "6371000";
    public static final String RADIUS_YARDS = "6969600";
    public static final String RADIUS_FEET = "20925524";
    public static final String RADIUS_NAUTICAL_MILES = "3440";



    private List<String> postcodes;

    private String unitOfMeasure;

    public List<String> getPostcodes() {
        return postcodes;
    }

    public void setPostcodes(List<String> postcodes) {
        this.postcodes = postcodes;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
