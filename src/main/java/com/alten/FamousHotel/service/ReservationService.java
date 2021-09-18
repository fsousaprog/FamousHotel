package com.alten.FamousHotel.service;

import com.alten.FamousHotel.entity.Reservation;
import com.alten.FamousHotel.model.ReservationDTO;
import com.alten.FamousHotel.repository.ReservationRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for intermediation between the persistence and the system, to avoid giving access to the database for just anyone
 * There is a bean method for any entity method, so entities don't go to the front
 */
@Service
@Transactional
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository repository;

    public List<Reservation> findAll() {
        return (List<Reservation>) this.repository.findAll();
    }

    public List<ReservationDTO> findAllBean() {
        List<ReservationDTO> beans = new ArrayList<>();
        this.repository.findAll().forEach(
                res -> beans.add(new ReservationDTO(res.getId(), res.getCheckin(), res.getCheckout(), res.getGuests())));
        log.info("Total of items from findAll: " + beans.size());
        return beans;
    }

    public Reservation findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public ReservationDTO findByIdBean(Long id) {
        Reservation reservation = this.repository.findById(id).orElse(null);
        if (reservation != null) {
            ReservationDTO bean = new ReservationDTO(
                    reservation.getId(), reservation.getCheckin(), reservation.getCheckout(), reservation.getGuests());
            bean.updateStay();
            log.info("Reservation found with findById");
            return bean;
        }
        log.warn("No reservation was found with the ID: " + id);
        return null;
    }

    public void save(Reservation reservation) {
        this.repository.save(reservation);
        log.info("Reservation saved with success!");
    }

    public void save(@NotNull ReservationDTO bean) {
        bean.updateStay();
        this.repository.save(new Reservation(
                Long.parseLong(bean.getId()), bean.getCheckin(), bean.getCheckout(), Integer.parseInt(bean.getGuests())));
        log.info("Reservation saved with success!");
    }

    public void delete(Reservation reservation) {
        this.repository.delete(reservation);
        log.info("Reservation deleted!");
    }

    public void delete(@NotNull ReservationDTO bean) {
        bean.updateStay();
        this.repository.delete(new Reservation(
                Long.parseLong(bean.getId()), bean.getCheckin(), bean.getCheckout(), Integer.parseInt(bean.getGuests())));
        log.info("Reservation deleted!");
    }

    public void delete(Long id) throws Exception {
        Reservation reservation = this.repository.findById(id).orElse(null);
        if (reservation != null) {
            this.repository.delete(reservation);
            log.info("Reservation deleted!");
        } else {
            log.error("Reservation not found! ID: " + id);
            throw new Exception("Reservation not found! ID: " + id);
        }
    }

}
