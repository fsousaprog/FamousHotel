package com.alten.FamousHotel.repository;

import com.alten.FamousHotel.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The JpaRepository facilitates the usage of the Database.
 * It already contains all basic methods, and it's dynamically adjusted based on the attributes names inside the class.
 * It is still possible to implement custom queries to match your needs.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
