package com.alten.FamousHotel.service;

import com.alten.FamousHotel.entity.Reservation;
import com.alten.FamousHotel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;

    public List<Reservation> findAll() {
        return (List<Reservation>) repository.findAll();
    }

    public void save(Reservation reservation) {
        repository.save(reservation);
    }

    public void findById(Long id) {
        repository.findById(id);
    }
}
