package com.alten.FamousHotel.control;

import com.alten.FamousHotel.model.ReservationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.alten.FamousHotel.util.Constants.*;

/**
 * Web Controller responsible for directing to the right pages
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String showHome(Model model) {
        return PAGE_HOME;
    }

    @GetMapping("booking")
    public String showBooking(Model model) {
        model.addAttribute("reservationBean", new ReservationDTO());
        return PAGE_BOOKING;
    }

    @GetMapping("manageReservation")
    public String showManageReservation(Model model) {
        model.addAttribute("reservationBean", new ReservationDTO());
        return PAGE_MANAGE;
    }

    @GetMapping("result")
    public String showResultGet(Model model) {
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

}
