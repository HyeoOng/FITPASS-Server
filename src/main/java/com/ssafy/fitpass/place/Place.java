package com.ssafy.fitpass.place;

public class Place {
    private int placeId;
    private String placeName;
    private int regionCode;
    private double latitude, longitude;

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

    public double getLongitude() {
        return longitude;
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

    public void setLongitude(double logitude) {
        this.longitude = logitude;
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeId=" + placeId +
                ", placeName='" + placeName + '\'' +
                ", regionCode=" + regionCode +
                ", latitude=" + latitude +
                ", logitude=" + longitude +
                '}';
    }
}
