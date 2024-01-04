package com.driver.repositories;

import com.driver.model.City;
import com.driver.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlightRepository {
    Map<Integer, Flight> flightDB = new HashMap<>();
    Map<Integer, List<Integer>> flightPassengerDB = new HashMap<>();

    Map<Integer, Integer> revenueDB = new HashMap<>();

    public String addFlight(Flight flight) {
        int flightId = flight.getFlightId();
        this.flightDB.put(flightId, flight);
        return "SUCCESS";
    }

    public Map<Integer, Flight> getAllFlight() {
        return this.flightDB;
    }

    public boolean isFlight(int flightId) {
        return this.flightDB.containsKey(flightId);
    }

    public boolean bookATicket(int flightId, int passengerId) {
        List<Integer> passengers = new ArrayList<>();
        if(this.flightPassengerDB.containsKey(flightId)) {
            passengers = this.flightPassengerDB.get(flightId);
            int flightCapacity = this.flightDB.get(flightId).getMaxCapacity();
            if(flightCapacity <= passengers.size() || passengers.contains(passengerId)) {
                return false;
            }
        }
        passengers.add(passengerId);
        this.flightPassengerDB.put(flightId, passengers);
        int revenue = 0;
        if(this.revenueDB.containsKey(flightId)) revenue = this.revenueDB.get(flightId);
        revenue += 3000 + 50 * (passengers.size()-1);
        this.revenueDB.put(flightId, revenue);
        return true;
    }

    public boolean cancelATicket(int flightId, int passengerId) {
        if(this.flightPassengerDB.containsKey(flightId)) {
            List<Integer> passengers = this.flightPassengerDB.get(flightId);
            if(passengers.contains(passengerId)) {
                int idx = passengers.indexOf(passengerId);
                passengers.remove(passengerId);
                if(this.revenueDB.containsKey(flightId)) {
                    int currentRevenue = this.revenueDB.get(flightId);
                    int canceletionCharge = 3000 + 50*idx;
                    this.revenueDB.put(flightId, currentRevenue-canceletionCharge);
                }
                return true;
            }
        }
        return false;
    }

    public City getCityNameFromFlightId(int flightId) {
        if(this.flightDB.containsKey(flightId)) {
            return  this.flightDB.get(flightId).getFromCity();
        }
        return null;
    }

    public int getNumberOfPassengerByFlightId(int flightId) {
        if(this.flightPassengerDB.containsKey(flightId)) return this.flightPassengerDB.get(flightId).size();
        return 0;
    }

    public int getRevenue(int flightId) {
        if(this.revenueDB.containsKey(flightId)) {
            return this.revenueDB.get(flightId);
        }
        return 0;
    }

}
