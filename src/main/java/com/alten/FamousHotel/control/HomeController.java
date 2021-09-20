package com.alten.FamousHotel.control;

import com.alten.FamousHotel.entity.Reservation;
import com.alten.FamousHotel.service.ReservationService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.alten.FamousHotel.util.Constants.*;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.util.StringUtils.replace;
import static org.springframework.util.StringUtils.split;

/**
 * Web Controller responsible for handling the html pages
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ReservationController controller;
    @Autowired
    private ReservationService service;

    @GetMapping
    public String showHome(Model model) {
        return PAGE_HOME;
    }

    @GetMapping("booking")
    public String showBooking(Model model) {
        model.addAttribute("restrictions", this.getRestrictions().getBody());
        model.addAttribute("reservation", new Reservation(null, null, null, 0));
        return PAGE_BOOKING;
    }

    /**
     * @param reservationID id
     * @param model         model
     * @return web page
     */
    @PostMapping("manageReservation")
    public String showManageReservation(@RequestParam Long reservationID, Model model) {
        ResponseEntity<Reservation> response = this.controller.findById(reservationID);
        if (response != null && response.getBody() != null) {
            model.addAttribute("restrictions", this.getRestrictions().getBody());
            model.addAttribute("reservation", response.getBody());
            return PAGE_MANAGE;
        }
        model.addAttribute("result", "No reservation was found for the ID: " + reservationID);
        model.addAttribute("reservation", new Reservation(reservationID, null, null, 0));
        return PAGE_RESULT;
    }

    @GetMapping("result")
    public String showResult(Model model) {
        return PAGE_RESULT;
    }

    @PostMapping("result")
    public String showResultPost(Model model) {
        return PAGE_RESULT;
    }

    @PutMapping("result")
    public String showResultPut(Model model) {
        return PAGE_RESULT;
    }

    @DeleteMapping("result")
    public String showResultDelete(Model model) {
        return PAGE_RESULT;
    }

    /**
     * Creates a new reservation
     *
     * @param reservation reservation
     * @param stay        checkin and checkout
     * @param model       model
     * @return result page
     */
    @PostMapping("/placeReservation")
    public String placeReservation(@ModelAttribute("reservation") Reservation reservation,
                                   @RequestParam String stay, Model model) {
        this.populateStay(reservation, stay);
        ResponseEntity<String> response = this.controller.save(reservation);
        model.addAttribute("reservation", reservation);
        model.addAttribute("result", response.getBody());
        return PAGE_RESULT;
    }


    /**
     * Used by the manageReservation page, it serves to keep the page with only one form,
     * and it either updates or delete the reservation.
     *
     * @param reservation reservation
     * @param stay        checkin and checkout
     * @param update      update button value
     * @param delete      delete button value
     * @param model       model
     * @return result page
     */
    @PostMapping("/processReservation")
    public String processReservation(@ModelAttribute("reservation") Reservation reservation,
                                     String stay, String update, String delete, Model model) {
        log.info("Requisition is being processed..");
        if (update != null) {
            this.populateStay(reservation, stay);
            ResponseEntity<String> response = this.controller.update(reservation);
            model.addAttribute("reservation", reservation);
            model.addAttribute("result", response.getBody());
            return PAGE_RESULT;
        }
        if (delete != null) {
            ResponseEntity<String> response = this.controller.delete(reservation.getId());
            model.addAttribute("reservation", reservation);
            model.addAttribute("result", response.getBody());
            return PAGE_RESULT;
        }
        model.addAttribute("result", "No modification was made to reservation");
        model.addAttribute("reservation", reservation);

        return PAGE_RESULT;
    }

    /**
     * Gets all the date restrictions centralized
     *
     * @return json
     */
    @GetMapping("/restrictions")
    public ResponseEntity<JSONObject> getRestrictions() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        JSONObject json = new JSONObject();
        json.put("dateFormat", DATE_FORMAT);
        json.put("maxSpan", MAX_STAY - 1);
        json.put("minAdvance", MIN_DAYS_ADVANCE);
        json.put("maxAdvance", MAX_DAYS_ADVANCE);
        List<String> formattedDates = new ArrayList<>();
        this.service.getDatesOccupied().forEach(date -> formattedDates.add(date.format(formatter)));
        json.put("datesOccupied", formattedDates);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * Populates checkin and checkout with the stay value that comes from the input in the html
     *
     * @param reservation reservation
     * @param stay        check-in and check-out
     */
    private void populateStay(Reservation reservation, String stay) {
        DateTimeFormatter formatter = ofPattern(DATE_FORMAT);
        String[] checkinOut = split(stay, " - ");
        assert checkinOut != null;
        reservation.setCheckin(parse(replace(checkinOut[0], "-", "/"), formatter));
        reservation.setCheckout(parse(replace(checkinOut[1], "-", "/"), formatter));
    }

}
