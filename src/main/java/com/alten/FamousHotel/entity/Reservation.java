package com.alten.FamousHotel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

import static com.alten.FamousHotel.util.Constants.DATE_FORMAT;

@Entity
public class Reservation {

    @Id
    private Long id;
    @JsonFormat(pattern = DATE_FORMAT)
    @Column(nullable = false)
    private LocalDate checkin;
    @JsonFormat(pattern = DATE_FORMAT)
    @Column(nullable = false)
    private LocalDate checkout;
    @Column(nullable = false)
    private int guests;

    protected Reservation() {
    }

    public Reservation(Long id, LocalDate checkin, LocalDate checkout, int guests) {
        this.id = id;
        this.checkin = checkin;
        this.checkout = checkout;
        this.guests = guests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    /**
     * For better readability when printing the bean
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format(
                "ID: %d, Check-in: %s, Check-out: %s, Guests: %d",
                this.id, this.checkin, this.checkout, this.guests);
    }

}
