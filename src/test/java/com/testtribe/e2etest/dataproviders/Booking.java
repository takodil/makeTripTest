package com.testtribe.e2etest.dataproviders;

import com.testtribe.e2etest.models.BookingBO;
import com.testtribe.e2etest.models.GuestBO;
import com.testtribe.e2etest.models.SearchBO;
import org.testng.annotations.DataProvider;

public class Booking {
    @DataProvider(name = "validSearch")
    public static Object[][] validSearch() {
        SearchBO search = new SearchBO();
        search.setDepartureCity("Mumbai");
        // Number calculated from current date
        search.setCheckInDate(3);
        // Number calculated from current date
        search.setCheckOutDate(4);

        int[] childrenAge = new int[] {4,1,2};

        GuestBO product = new GuestBO();
        product.setAdultNum(2);
        product.setRoomNum(1);
        product.setChildrenAge(childrenAge);

        BookingBO booking = new BookingBO();
        booking.setMinPrice(2000);
        booking.setUserRating("4 & above");
        booking.setType("Work");
        booking.setSearch(search);
        booking.setGuest(product);

        return new Object[][]{{booking}};
    }
}
