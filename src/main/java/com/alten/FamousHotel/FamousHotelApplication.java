package com.alten.FamousHotel;

import com.alten.FamousHotel.entity.Reservation;
import com.alten.FamousHotel.repository.ReservationRepository;
import com.alten.FamousHotel.service.ReservationService;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class FamousHotelApplication {

	private static final Logger log = LoggerFactory.getLogger(FamousHotelApplication.class);

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
			LocalDate checkin = LocalDate.now();
			LocalDate checkout = checkin.plusDays(2);
			service.save(new Reservation(41345625598L, checkin.toString(), checkout.toString(), 2));
			checkin = checkout.plusDays(3);
			checkout = checkin.plusDays(3);
			service.save(new Reservation(41345625345L, checkin.toString(), checkout.toString(), 1));
			checkin = checkout.plusDays(8);
			checkout = checkin.plusDays(1);
			service.save(new Reservation(41345625567L, checkin.toString(), checkout.toString(), 3));

			log.info("Mock reservations saved in Database:");
			log.info("-------------------------------");
			service.findAll().forEach(res -> log.info(res.toString()));
		};
	}

}
