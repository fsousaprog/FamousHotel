package com.alten.FamousHotel.service;

import com.alten.FamousHotel.model.ReservationBean;
import com.alten.FamousHotel.entity.Reservation;
import com.alten.FamousHotel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;

    public List<Reservation> findAll() {
        return (List<Reservation>) this.repository.findAll();
    }

    public void save(Reservation reservation) {
        this.repository.save(reservation);
    }

    public void save(ReservationBean bean) {
        String[] checkinOut = StringUtils.split(bean.getStay(), " - ");
        this.repository.save(new Reservation(
                Long.parseLong(bean.getId()), checkinOut[0], checkinOut[1], Integer.parseInt(bean.getGuests())));
    }

    public Reservation findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }
}
