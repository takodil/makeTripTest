package com.testtribe.e2etest.models;

public class BookingBO {
    public double minPrice;
    public String type;
    public String userRating;
    public SearchBO search;
    public GuestBO guest;

    public void setType(String type) {
        this.type = type;
    }

    public void setGuest(GuestBO guest) {
        this.guest = guest;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public void setSearch(SearchBO search) {
        this.search = search;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

}
