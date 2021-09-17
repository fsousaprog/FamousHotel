package com.alten.FamousHotel.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String showHome(Model model) {
        return "home";
    }

    @GetMapping("booking")
    public String showBooking(Model model) {
        return "booking";
    }

    @GetMapping("manageReservation")
    public String showManageReservation(Model model) {
        return "manageReservation";
    }

}
