package com.testtribe.e2etest.models;

public class BookingBO {
    public double minPrice;
    public String type;
    public String userRating;
    public SearchBO search;
    public GuestBO product;

    public void setType(String type) {
        this.type = type;
    }

    public void setProduct(GuestBO product) {
        this.product = product;
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
