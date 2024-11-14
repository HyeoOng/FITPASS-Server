package com.ssafy.fitpass.model.dto;

public class Sport {
    private int sportCode;
    private String sportName;

    public Sport() {
    }

    public Sport(String sportName) {
        setSportName(sportName);
    }

    public Sport(int sportCode, String sportName) {
        setSportCode(sportCode);
        setSportName(sportName);
    }

    // getter와 setter
    public int getSportCode() {
        return sportCode;
    }

    public void setSportCode(int sportCode) {
        this.sportCode = sportCode;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "sportCode=" + sportCode +
                ", sportName='" + sportName + '\'' +
                '}';
    }
}
