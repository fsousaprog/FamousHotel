package com.alten.FamousHotel.util;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;

/**
 * Centralized values to reduce inconsistency
 */
public class Constants {

    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final Integer MIN_DAYS_ADVANCE = 1;
    public static final Integer MAX_DAYS_ADVANCE = 30;
    public static final Integer MAX_STAY = 3;


    public static final String PAGE_HOME = "home";
    public static final String PAGE_BOOKING = "booking";
    public static final String PAGE_MANAGE = "manageReservation";
    public static final String PAGE_RESULT = "result";

    public static final Long TEST_ID = 41345625598L;
    public static final LocalDate TEST_CHECKIN = LocalDate.now().plusDays(4);
    public static final LocalDate TEST_CHECKOUT = TEST_CHECKIN.plusDays(1);
    public static final int TEST_GUESTS = 2;


}
