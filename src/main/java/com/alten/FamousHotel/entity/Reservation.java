package com.alten.FamousHotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Reservation {

    @Id
    private Long id;
    @Column(nullable = false)
    private String checkin;
    @Column(nullable = false)
    private String checkout;
    @Column(nullable = false)
    private int guests;

    protected  Reservation() {}

    public Reservation(Long id, String checkin, String checkout, int guests) {
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

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d, Check-in: %s, Checkout: %s, Guests: %d",
                this.id, this.checkin, this.checkout, this.guests);
    }

}
