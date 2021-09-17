package com.alten.FamousHotel.control;

import com.alten.FamousHotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService service;

    @GetMapping("/datesOccupied")
    public String getDatesOccupied(Model model) {
        Map<String, String> datesOccupied = new HashMap<>();
        service.findAll().forEach(res -> datesOccupied.put(res.getCheckin(), res.getCheckout()));
        model.addAttribute("occupation", datesOccupied);
        return "booking";
    }

}
