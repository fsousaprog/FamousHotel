package com.alten.FamousHotel.service;

import com.alten.FamousHotel.entity.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.alten.FamousHotel.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Making good use of Spring boot, @SpringBootTest initializes the beans for you,
 * so you can just use them instead of initializing each one manually
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService service;

    @BeforeEach
    void setUp() {
        this.service.save(new Reservation(TEST_ID, TEST_CHECKIN, TEST_CHECKOUT, TEST_GUESTS));
    }

    @AfterEach
    void clean() throws Exception {
        if (this.service.findById(TEST_ID) != null) {
            this.service.delete(TEST_ID);
        }
    }

    @Test
    void findById() {
        Reservation reservation = this.service.findById(TEST_ID);
        assertNotNull(reservation);
        assertEquals(TEST_CHECKIN, reservation.getCheckin());
        assertEquals(TEST_CHECKOUT, reservation.getCheckout());
        assertEquals(TEST_GUESTS, reservation.getGuests());
    }

    @Test
    void save() {
        assertNotNull(this.service.findById(TEST_ID));
    }

    @Test
    void delete() {
        Exception exception = null;
        try {
            this.service.delete(TEST_ID);
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);
        assertNull(this.service.findById(TEST_ID));
    }

    @Test
    void getDatesAvailable() {
        assertTrue(this.service.getDatesAvailable().size() > 0);
    }

    @Test
    void getDatesOccupied() throws Exception {
        assertTrue(this.service.getDatesOccupied().size() > 0);
    }
}