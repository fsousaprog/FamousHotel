package com.alten.FamousHotel.service;

import com.alten.FamousHotel.entity.Reservation;
import com.alten.FamousHotel.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.alten.FamousHotel.util.Constants.MAX_DAYS_ADVANCE;
import static com.alten.FamousHotel.util.Constants.MIN_DAYS_ADVANCE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.iterate;

/**
 * Responsible for intermediation between the persistence layer and the API
 */
@Service
@Transactional
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository repository;

    public List<Reservation> findAll() {
        return this.repository.findAll();
    }

    public Reservation findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public void save(Reservation reservation) {
        this.repository.save(reservation);
        log.info("Reservation saved with success!");
    }

    public void delete(Reservation reservation) {
        this.repository.delete(reservation);
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

    public List<LocalDate> getDatesAvailable() {
        List<LocalDate> datesOccupied = this.getDatesOccupied();

        /* Using Java 8 stream, iterates through all days between the minimum and maximum reservation date,
         * then add them all into a list, remove the occupied dates from that list and sort it */
        Set<LocalDate> datesAvailable = new HashSet<>(
                iterate(LocalDate.now().plusDays(MIN_DAYS_ADVANCE), date -> date.plusDays(1))
                        .limit(MAX_DAYS_ADVANCE).collect(toList()));
        datesOccupied.forEach(datesAvailable::remove);

        List<LocalDate> sortedList = new ArrayList<>(datesAvailable);
        Collections.sort(sortedList);
        return sortedList;
    }

    public List<LocalDate> getDatesOccupied() {
        List<LocalDate> datesOccupied = new ArrayList<>();
        log.info("Fetching dates that already have a reservation");
        //Gets every date between check-in and check-out
        for (Reservation res : this.findAll()) {
            long numOfDays = ChronoUnit.DAYS.between(res.getCheckin(), res.getCheckout());
            datesOccupied.addAll(iterate(res.getCheckin(), date -> date.plusDays(1))
                    .limit(numOfDays + 1).collect(toList()));
        }
        return datesOccupied;
    }

}
