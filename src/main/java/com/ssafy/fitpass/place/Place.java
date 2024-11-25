package com.ssafy.fitpass.place;

import com.ssafy.fitpass.exception.InputException;

public class Place {
    private int placeId;
    private String placeName;
    private String placeAddress;
    private int kakaoMapId;
    private double latitude, longitude;

    public int getKakaoMapId() { return kakaoMapId; }

    public void setKakaoMapId(int kakaoMapId) {
        if (kakaoMapId < 0){
            throw new InputException("kakao Map 오류.. 잠시 후 다시 시도해주세요.");
        }
        this.kakaoMapId = kakaoMapId;
    }

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
        if (placeId < 0){
            throw new InputException("kakao Map 오류.. 잠시 후 다시 시도해주세요.");
        }
        this.placeId = placeId;
    }

    public void setPlaceName(String placeName) {
        if (placeName.equals("") || placeName.isEmpty()){
            throw new InputException("kakao Map 오류.. 잠시 후 다시 시도해주세요.");
        }
        this.placeName = placeName;
    }

    public void setPlaceAddress(String placeAddress) {
        if (placeAddress.equals("") || placeAddress.isEmpty()){
            throw new InputException("kakao Map 오류.. 잠시 후 다시 시도해주세요.");
        }
        this.placeAddress = placeAddress;
    }

    public void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90){
            throw new InputException("kakao Map 오류.. 잠시 후 다시 시도해주세요.");
        }
        this.latitude = latitude;
    }

    public void setLongitude(double logitude) {
        if (logitude < -180 || logitude > 180){
            throw new InputException("kakao Map 오류.. 잠시 후 다시 시도해주세요.");
        }
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
