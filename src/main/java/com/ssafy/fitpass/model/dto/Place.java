package com.ssafy.fitpass.model.dto;

public class Place {
    private int placeId;
    private String placeName;
    private int regionCode;
    private double latitude, logitude;

    public int getPlaceId() {
        return placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public int getRegionCode() {
        return regionCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLogitude() {
        return logitude;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setRegionCode(int regionCode) {
        this.regionCode = regionCode;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLogitude(double logitude) {
        this.logitude = logitude;
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeId=" + placeId +
                ", placeName='" + placeName + '\'' +
                ", regionCode=" + regionCode +
                ", latitude=" + latitude +
                ", logitude=" + logitude +
                '}';
    }
}
