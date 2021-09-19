//package com.alten.FamousHotel.control;
//
//import com.alten.FamousHotel.entity.Reservation;
//import com.alten.FamousHotel.service.ReservationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import static com.alten.FamousHotel.util.Constants.*;
//import static java.lang.Long.parseLong;
//
///**
// * Web Controller responsible for handling the html pages
// */
//@Controller
//@RequestMapping("/")
//public class HomeController {
//
//    @Autowired
//    private ReservationService service;
//
//    @GetMapping
//    public String showHome(Model model) {
//        return PAGE_HOME;
//    }
//
//    @GetMapping("booking")
//    public String showBooking(Model model) {
//        model.addAttribute("reservation", null);
//        return PAGE_BOOKING;
//    }
//
//    @GetMapping("manageReservation")
//    public String showManageReservation(Model model) {
//        Reservation bean = this.service.findByIdBean(reservationID);
//        if (bean != null) {
//            model.addAttribute("reservation", bean);
//            return PAGE_MANAGE;
//        }
//        model.addAttribute("result", "No reservation was found for the ID: " + reservationID);
//        model.addAttribute("reservation", new ReservationDTO());
//        return PAGE_RESULT;
//    }
//
//    /**
//     * Creates a new reservation
//     *
//     * @param reservationDTO reservation
//     * @param model          model
//     * @return result page
//     */
//    @PostMapping("/placeReservation")
//    public String placeReservation(@ModelAttribute("reservation") ReservationDTO reservationDTO, Model model) {
//        ReservationDTO existingBean = this.service.findByIdBean(parseLong(reservationDTO.getId()));
//        if (existingBean == null) {
//            return this.saveReservation(reservationDTO, model);
//        }
//        model.addAttribute("reservation", existingBean);
//        model.addAttribute("result", "There is already a reservation with this ID.\n See reservation below");
//
//        return PAGE_RESULT;
//    }
//
//    /**
//     * Either creates a new reservation or updates an existing one
//     *
//     * @param reservationDTO reservation
//     * @param model          model
//     * @return result page
//     */
//    @PostMapping("/saveReservation")
//    public String saveReservation(@Validated @RequestBody Reservation reservation) {
//        String result = "Congratulations, your reserve has been placed!\n We look forward to see you";
//        try {
//            this.service.save(reservationDTO);
//        } catch (Exception e) {
//            result = "Sorry, but there was an error trying to place your reservation\n " + e.getMessage();
//        }
//        model.addAttribute("reservation", reservationDTO);
//        model.addAttribute("result", result);
//
//        return PAGE_RESULT;
//    }
//
//    @DeleteMapping("/deleteReservation")
//    public String deleteReservation(@RequestBody ReservationDTO bean, Model model) {
//        String result = "Reservation deleted with success!\n Hope you come back soon";
//        try {
//            this.service.delete(parseLong(bean.getId()));
//        } catch (Exception e) {
//            result = "Something wrong happened, and your Reservation wasn't deleted: " + e.getMessage();
//        }
//        model.addAttribute("result", result);
//        model.addAttribute("reservation", new ReservationDTO(parseLong(bean.getId()), null, null, 0));
//
//        return PAGE_RESULT;
//    }
//
//    /**
//     * Used by the manageReservation page, it serves to keep the page with only one form,
//     * and it either updates or delete the reservation.
//     *
//     * @param reservation reservation
//     * @param update         update button value
//     * @param delete         delete button value
//     * @param model          model
//     * @return result page
//     */
//    @PostMapping("/processReservation")
//    public String processReservation(@ModelAttribute("reservation") Reservation reservation,
//                                     String update, String delete, Model model) {
//        log.info("Requisition is being processed..");
//        if (update != null) {
//            return this.saveReservation(reservation, model);
//        }
//        if (delete != null) {
//            return this.deleteReservation(reservation, model);
//        }
//        model.addAttribute("result", "No modification was made to reservation");
//        model.addAttribute("reservation", reservation);
//
//        return PAGE_RESULT;
//    }
//
//}
