package com.testtribe.e2etest.models;

public class SearchBO {
    public String departureCity;
    public int checkInDate;
    public int checkOutDate;

    public void setCheckOutDate(int checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public void setCheckInDate(int checkInDate) {
        this.checkInDate = checkInDate;
    }

}
