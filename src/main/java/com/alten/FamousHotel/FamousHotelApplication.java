package com.alten.FamousHotel;

import com.alten.FamousHotel.entity.Reservation;
import com.alten.FamousHotel.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.alten.FamousHotel.util.Constants.*;

@SpringBootApplication
public class FamousHotelApplication {

    private static final Logger log = LoggerFactory.getLogger(FamousHotelApplication.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static void main(String[] args) {
        SpringApplication.run(FamousHotelApplication.class, args);
    }

    /**
     * Execute as soon as the application goes up. Adds some reservations to simulate reserves already placed
     */
    @Bean
    public CommandLineRunner populateMockDatabase(ReservationService service) {
        return (args) -> {
            // save a few reservations
            service.save(new Reservation(TEST_ID, TEST_CHECKIN.format(formatter), TEST_CHECKOUT.format(formatter), 2));
            LocalDate checkin = TEST_CHECKOUT.plusDays(3);
            LocalDate checkout = checkin.plusDays(2);
            service.save(new Reservation(41345625345L, checkin.format(formatter), checkout.format(formatter), 1));
            checkin = checkout.plusDays(8);
            checkout = checkin;
            service.save(new Reservation(41345625567L, checkin.format(formatter), checkout.format(formatter), 3));

            log.info("Mock reservations saved in Database:");
            log.info("-------------------------------");
            service.findAll().forEach(res -> log.info(res.toString()));
        };
    }

}
