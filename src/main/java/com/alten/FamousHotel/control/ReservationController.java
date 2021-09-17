package com.alten.FamousHotel.control;

import com.alten.FamousHotel.model.ReservationBean;
import com.alten.FamousHotel.entity.Reservation;
import com.alten.FamousHotel.service.ReservationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService service;

    @GetMapping("/datesOccupied")
    public @ResponseBody
    String getDatesOccupied() {
        JSONObject json = new JSONObject();
        this.service.findAll().forEach(res -> json.put(res.getCheckin().toString(), res.getCheckout().toString()));
        return json.toString();
    }

    @GetMapping("/getReservation")
    public @ResponseBody
    Reservation getReservationById(@RequestParam String id) {
        return this.service.findById(Long.parseLong(id));
    }

    @PostMapping("/placeReservation")
    public String saveReservation(@ModelAttribute("reservationBean")ReservationBean reservationBean,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("reservationBean", reservationBean);
        this.service.save(reservationBean);

        //TODO:Show alert of success or failure

        return "home";
    }

}
