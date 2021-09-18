package com.alten.FamousHotel.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import static org.thymeleaf.util.StringUtils.isEmpty;
import static org.thymeleaf.util.StringUtils.split;

/**
 * DTO to handle entity data instead of the entity itself
 */
@Data
@Component
public class ReservationDTO {
    private String id;
    private String checkin;
    private String checkout;
    private String stay;
    private String guests;

    public ReservationDTO() {
    }

    public ReservationDTO(Long id, String checkin, String checkout, int guests) {
        this.id = id + "";
        this.checkin = checkin;
        this.checkout = checkout;
        this.guests = guests + "";
    }

    /**
     * Updates the checkin and checkout attributes with the stay attribute value, and vice-versa
     */
    public void updateStay() {
        if (!isEmpty(this.stay)) {
            String[] checkinOut = split(this.stay, " - ");
            this.checkin = checkinOut[0];
            this.checkout = checkinOut[1];
        } else if (!isEmpty(this.checkin) && !isEmpty(this.checkout)) {
            this.stay = this.checkin + " - " + this.checkout;
        }
    }

    /**
     * For better readability when printing the bean
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format(
                "ID: %s, Check-in: %s, Check-out: %s, Guests: %s",
                this.id, this.checkin, this.checkout, this.guests);
    }
}
