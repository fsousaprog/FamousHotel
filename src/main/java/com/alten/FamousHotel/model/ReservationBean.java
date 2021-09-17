package com.alten.FamousHotel.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReservationBean {
    private String id;
    private String checkin;
    private String checkout;
    private String stay;
    private String guests;
}
