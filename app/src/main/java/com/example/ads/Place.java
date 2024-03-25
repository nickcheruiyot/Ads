package com.example.ads;

public class Place {

    private String name;
    private double latitude;
    private double longitude;
    private String vicinity;
    private String fileUrl;

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Place(String name, double latitude, double longitude, String vicinity, String fileUrl) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vicinity = vicinity;
        this.fileUrl = fileUrl;
    }

    public Place(String name, double latitude, double longitude, String vicinity) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vicinity = vicinity;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getVicinity() {
        return vicinity;
    }
}

