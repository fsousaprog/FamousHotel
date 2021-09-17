package com.alten.FamousHotel.repository;

import com.alten.FamousHotel.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

}
