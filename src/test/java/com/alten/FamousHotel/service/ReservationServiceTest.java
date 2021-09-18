package com.alten.FamousHotel.service;

import com.alten.FamousHotel.model.ReservationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.alten.FamousHotel.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Making good use of Spring boot, @SpringBootTest initializes the beans for you,
 * so you can just use them instead of initializing each one manually in a @BeforeEach setUp()
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReservationServiceTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    private ReservationService service;

    @Test
    @DisplayName("Tests if the 3 reservations were created at the initialization (FamousHotelApplication.java)")
    void findAllBean() {
        List<ReservationDTO> list = this.service.findAllBean();
        assertEquals(3, list.size());
    }

    @Test
    void findByIdBean() {
        ReservationDTO bean = this.service.findByIdBean(TEST_ID);
        assertNotNull(bean);
        assertEquals(TEST_CHECKIN.format(formatter), bean.getCheckin());
        assertEquals(TEST_CHECKOUT.format(formatter), bean.getCheckout());
    }

    @Test
    void delete() {
        Exception exception = null;
        try {
            this.service.delete(41345625598L);
        } catch (Exception e) {
            exception = e;
        }
        assertNull(exception);
        assertNull(this.service.findById(41345625598L));
    }

}