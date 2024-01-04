package com.driver.repositories;

import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PassengerRepository {
    Map<Integer, Passenger> passengerDB = new HashMap<>();
    Map<Integer, List<Integer>> passengerFlightDB = new HashMap<>();

    public String addPassenger(Passenger passenger) {
        int passengerId = passenger.getPassengerId();
        this.passengerDB.put(passengerId, passenger);
        return "SUCCESS";
    }

    public boolean isPassenger(int passengerId) {
        return this.passengerDB.containsKey(passengerId);
    }

    public void bookATicket(int flightId, int passengerId) {
        List<Integer> flights = new ArrayList<>();
        if(this.passengerFlightDB.containsKey(passengerId)) {
            flights = this.passengerFlightDB.get(passengerId);
            flights.remove(flightId);
        }
        flights.add(flightId);
        this.passengerFlightDB.put(passengerId, flights);
        return;
    }

    public void cancelATicket(int flightId, int passengerId) {
        this.passengerFlightDB.get(passengerId).remove(flightId);
        return;
    }

    public int countOfBookingsDoneByPassengerAllCombined(int passengerId) {
        if(this.passengerFlightDB.containsKey(passengerId))
            return this.passengerFlightDB.get(passengerId).size();
        return 0;
    }


}
