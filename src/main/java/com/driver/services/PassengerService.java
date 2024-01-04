package com.driver.services;

import com.driver.model.Passenger;
import com.driver.repositories.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    PassengerRepository passengerRepository = new PassengerRepository();

    public String addPassenger(Passenger passenger) {
        return passengerRepository.addPassenger(passenger);
    }

    public int countOfBookingsDoneByPassengerAllCombined(int passengerId) {
        return passengerRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }
}
