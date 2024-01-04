package com.driver.repositories;

import com.driver.model.Airport;
import com.driver.model.City;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AirportRepository {
    Map<String, Airport> airportDB = new HashMap<>();

    public String addAirport(Airport airport) {
        String message = "SUCCESS";
        String airportName = airport.getAirportName();
        this.airportDB.put(airportName, airport);
        return message;
    }

    public Map<String, Airport> getAllAirport() {
        return this.airportDB;
    }

    public City getCityByName(String airportName) {
        for (Airport airport: this.airportDB.values()) {
            if(airportName.equals(airport.getAirportName())) {
                return airport.getCity();
            }
        }
        return null;
    }
}
