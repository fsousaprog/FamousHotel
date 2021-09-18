package com.alten.FamousHotel.control;

import com.alten.FamousHotel.model.ReservationDTO;
import com.alten.FamousHotel.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.alten.FamousHotel.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final String checkinTest = LocalDate.now().plusDays(1).format(formatter);
    private final String checkoutTest = LocalDate.now().plusDays(2).format(formatter);

    @Autowired
    private ReservationController controller;
    @Autowired
    private ReservationService service;

    /**
     * Validate the dates reserved when the application starts
     */
    @Test
    void getDatesOccupied() {
        assertEquals(65, this.controller.getDatesOccupied().length());
    }

    @Test
    void getReservationById() {
        Model model = mock(Model.class);
        //Success
        String response = this.controller.getReservationById(String.valueOf(TEST_ID), model);
        assertEquals(PAGE_MANAGE, response);

        //Reservation ID doesn't exist
        response = this.controller.getReservationById("46594", model);
        assertEquals(PAGE_RESULT, response);
    }

    @Test
    void placeReservation() {
        Model model = mock(Model.class);
        ReservationDTO bean = new ReservationDTO(123456L, this.checkinTest, this.checkoutTest, 1);
        //Doesn't exists
        this.controller.placeReservation(bean, model);
        assertNotNull(this.service.findByIdBean(123456L));
    }

    @Test
    void saveReservation() {
        //Update
        ReservationDTO bean = new ReservationDTO(TEST_ID, this.checkinTest, this.checkoutTest, 5);
        this.controller.saveReservation(bean, mock(Model.class));
        ReservationDTO newBean = this.service.findByIdBean(TEST_ID);
        assertEquals(this.checkinTest, newBean.getCheckin());
        assertEquals(this.checkoutTest, newBean.getCheckout());
    }

    @Test
    void deleteReservation() {
        ReservationDTO bean = new ReservationDTO(TEST_ID, null, null, 0);
        this.controller.deleteReservation(bean, mock(Model.class));
        assertNull(this.service.findByIdBean(TEST_ID));
    }

    @Test
    void processReservation() {
        //Update
        ReservationDTO bean = new ReservationDTO(TEST_ID, this.checkinTest, this.checkoutTest, 5);
        this.controller.processReservation(bean, "update", null, mock(Model.class));
        ReservationDTO newBean = this.service.findByIdBean(TEST_ID);
        assertEquals(this.checkinTest, newBean.getCheckin());
        assertEquals(this.checkoutTest, newBean.getCheckout());

        //Delete
        this.controller.processReservation(newBean, null, "delete", mock(Model.class));
        assertNull(this.service.findByIdBean(TEST_ID));
    }

}