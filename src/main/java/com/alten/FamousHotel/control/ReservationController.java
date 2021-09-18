package com.alten.FamousHotel.control;

import com.alten.FamousHotel.model.ReservationDTO;
import com.alten.FamousHotel.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static com.alten.FamousHotel.util.Constants.*;
import static java.lang.Long.parseLong;
import static java.time.LocalDate.parse;
import static org.thymeleaf.util.StringUtils.join;

/**
 * Web Controller responsible for everything related to Reservation
 */
@Controller
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    private ReservationService service;

    /**
     * Gets a list with all the dates that already have a reservation
     *
     * @return string of a list
     */
    @GetMapping("/datesOccupied")
    public @ResponseBody
    String getDatesOccupied() {
        Set<String> dates = new HashSet<>();
        log.info("Fetching dates that already have a reservation");
        //Gets every date between check-in and check-out
        for (ReservationDTO res : this.service.findAllBean()) {
            LocalDate start = parse(res.getCheckin(), formatter);
            LocalDate end = parse(res.getCheckout(), formatter);
            while (start.isBefore(end)) {
                dates.add(start.format(formatter));
                start = start.plusDays(1);
            }
            dates.add(end.format(formatter));
        }

        return join(dates, ",");
    }

    @PostMapping("/getReservation")
    public String getReservationById(@RequestParam String reservationID, Model model) {
        ReservationDTO bean = this.service.findByIdBean(parseLong(reservationID));
        if (bean != null) {
            model.addAttribute("reservationBean", bean);
            return PAGE_MANAGE;
        }
        model.addAttribute("result", "No reservation was found for the ID: " + reservationID);
        model.addAttribute("reservationBean", new ReservationDTO());
        return PAGE_RESULT;
    }

    /**
     * Creates a new reservation
     *
     * @param reservationDTO reservationBean
     * @param model          model
     * @return result page
     */
    @PostMapping("/placeReservation")
    public String placeReservation(@ModelAttribute("reservationBean") ReservationDTO reservationDTO, Model model) {
        ReservationDTO existingBean = this.service.findByIdBean(parseLong(reservationDTO.getId()));
        if (existingBean == null) {
            return this.saveReservation(reservationDTO, model);
        }
        model.addAttribute("reservationBean", existingBean);
        model.addAttribute("result", "There is already a reservation with this ID.\n See reservation below");

        return PAGE_RESULT;
    }

    /**
     * Either creates a new reservation or updates an existing one
     *
     * @param reservationDTO reservationBean
     * @param model          model
     * @return result page
     */
    @PostMapping("/saveReservation")
    public String saveReservation(@ModelAttribute("reservationBean") ReservationDTO reservationDTO, Model model) {
        reservationDTO.updateStay();
        String result = "Congratulations, your reserve has been placed!\n We look forward to see you";
        try {
            this.service.save(reservationDTO);
        } catch (Exception e) {
            result = "Sorry, but there was an error trying to place your reservation\n " + e.getMessage();
        }
        model.addAttribute("reservationBean", reservationDTO);
        model.addAttribute("result", result);

        return PAGE_RESULT;
    }

    @DeleteMapping("/deleteReservation")
    public String deleteReservation(@RequestBody ReservationDTO bean, Model model) {
        String result = "Reservation deleted with success!\n Hope you come back soon";
        try {
            this.service.delete(parseLong(bean.getId()));
        } catch (Exception e) {
            result = "Something wrong happened, and your Reservation wasn't deleted: " + e.getMessage();
        }
        model.addAttribute("result", result);
        model.addAttribute("reservationBean", new ReservationDTO(parseLong(bean.getId()), null, null, 0));

        return PAGE_RESULT;
    }

    /**
     * Used by the manageReservation page, it serves to keep the page with only one form,
     * and it either updates or delete the reservation.
     *
     * @param reservationDTO reservationBean
     * @param update         update button value
     * @param delete         delete button value
     * @param model          model
     * @return result page
     */
    @PostMapping("/processReservation")
    public String processReservation(@ModelAttribute("reservationBean") ReservationDTO reservationDTO,
                                     String update, String delete, Model model) {
        log.info("Requisition is being processed..");
        if (update != null) {
            return this.saveReservation(reservationDTO, model);
        }
        if (delete != null) {
            return this.deleteReservation(reservationDTO, model);
        }
        model.addAttribute("result", "No modification was made to reservation");
        model.addAttribute("reservationBean", reservationDTO);

        return PAGE_RESULT;
    }

}
