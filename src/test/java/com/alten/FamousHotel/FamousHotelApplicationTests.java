package com.alten.FamousHotel;

import com.alten.FamousHotel.control.ReservationController;
import com.alten.FamousHotel.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FamousHotelApplicationTests {

	@Autowired
	private ReservationController reservationController;
	@Autowired
	private ReservationService reservationService;

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(this.reservationController);
		assertNotNull(this.reservationService);
	}
}
