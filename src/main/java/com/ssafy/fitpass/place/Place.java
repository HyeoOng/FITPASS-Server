package com.ssafy.fitpass.place;

public class Place {
    private int placeId;
    private String placeName;
    private String placeAddress;
    private int kakaoMapId;
    private double latitude, longitude;

    public int getKakaoMapId() { return kakaoMapId; }

    public void setKakaoMapId(int kakaoMapId) { this.kakaoMapId = kakaoMapId; }

    public int getPlaceId() {
        return placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
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

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
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
                ", placeAddress=" + placeAddress +
                ", latitude=" + latitude +
                ", logitude=" + longitude +
                '}';
    }
}
