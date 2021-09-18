package com.alten.FamousHotel.util;

import java.time.LocalDate;

/**
 * Centralized values to reduce inconsistency
 */
public class Constants {

    public static final String DATE_FORMAT = "yyyy/MM/dd";

    public static final String PAGE_HOME = "home";
    public static final String PAGE_BOOKING = "booking";
    public static final String PAGE_MANAGE = "manageReservation";
    public static final String PAGE_RESULT = "result";

    public static final Long TEST_ID = 41345625598L;
    public static final LocalDate TEST_CHECKIN = LocalDate.now().plusDays(4);
    public static final LocalDate TEST_CHECKOUT = TEST_CHECKIN.plusDays(1);


}
