package com.alten.FamousHotel.control;

import com.alten.FamousHotel.entity.Reservation;
import com.alten.FamousHotel.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.alten.FamousHotel.util.Constants.MAX_STAY;

/**
 * Web Controller responsible for everything related to Reservations
 */
@RestController
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationService service;

    @GetMapping("/reservation")
    public List<Reservation> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<Reservation> findById(@PathVariable Long id) {
        Reservation reservation = this.service.findById(id);
        if (reservation != null) {
            log.info("Reservation found: " + reservation);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        }
        log.info("Reservation not found with id: " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/reservation")
    public ResponseEntity<String> save(@Validated @RequestBody Reservation reservation) {
        if (this.service.findById(reservation.getId()) != null) {
            log.info("Reservation already exists");
            return new ResponseEntity<>("Reservation already exists", HttpStatus.OK);
        }
        LocalDate checkin = reservation.getCheckin();
        LocalDate checkout = reservation.getCheckout();
        if (checkin.isAfter(checkout)) {
            log.info("Reservation not saved. Check-out before Check-in");
            return new ResponseEntity<>("Reservation not saved! Check-out is before Check-in", HttpStatus.PRECONDITION_FAILED);
        }
        if (ChronoUnit.DAYS.between(checkin, checkout) > MAX_STAY - 1) {
            log.info("Reservation not saved. Stay is longer than X days");
            return new ResponseEntity<>("Reservation not saved! Stay is longer than " + MAX_STAY + " days", HttpStatus.PRECONDITION_FAILED);
        }

        // Validate if there is a date already with a reservation
        List<LocalDate> datesOccupied = this.service.getDatesOccupied();
        List<LocalDate> conflictedDates = new ArrayList<>();
        while (checkin.isBefore(checkout.plusDays(1))) {
            if (datesOccupied.contains(checkin)) {
                conflictedDates.add(checkin);
            }
            checkin = checkin.plusDays(1);
        }

        if (conflictedDates.isEmpty()) {
            this.service.save(reservation);
            log.info("Reservation saved with success! " + reservation);
            return new ResponseEntity<>("Reservation saved with success", HttpStatus.OK);
        }
        Collections.sort(conflictedDates);
        log.info("Reservation not saved");
        return new ResponseEntity<>("Stay period already has reservation on: " + StringUtils.join(conflictedDates, ", "), HttpStatus.OK);
    }

    @PutMapping("/reservation")
    public ResponseEntity<String> update(@Validated @RequestBody Reservation reservation) {
        Reservation old = this.service.findById(reservation.getId());
        if (old != null) {
            this.service.save(reservation);
            log.info("Reservation updated with success");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        log.info("Reservation not found with id: " + reservation.getId());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Reservation reservation = this.service.findById(id);
        if (reservation != null) {
            this.service.delete(reservation);
            log.info("Reservation deleted with success! ID: " + id);
            return new ResponseEntity<>("Reservation deletes with success", HttpStatus.OK);
        }
        log.info("Reservation not found with id: " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get all available dates
     */
    @GetMapping("/availability")
    public ResponseEntity<List<LocalDate>> getAvailability() {
        return new ResponseEntity<>(this.service.getDatesAvailable(), HttpStatus.OK);
    }

}
