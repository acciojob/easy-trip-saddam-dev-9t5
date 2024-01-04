package com.driver.services;

import com.driver.model.Airport;
import org.springframework.stereotype.Service;
import com.driver.repositories.AirportRepository;

import java.util.Map;

@Service
public class AirportService {
    AirportRepository airportRepository = new AirportRepository();

    public String addAirport(Airport airport) {
        return airportRepository.addAirport(airport);
    }

    public String getLargestAirportName() {
        Map<String, Airport> airports = airportRepository.getAllAirport();
        String airportName = "";
        int maxTerminal = 0;
        for (Airport airport: airports.values()) {
            if(airport.getNoOfTerminals() > maxTerminal) {
                maxTerminal = airport.getNoOfTerminals();
                airportName = airport.getAirportName();
            }else if(airport.getNoOfTerminals() == maxTerminal) {
                if(airportName.compareTo(airport.getAirportName()) > 0) {
                    airportName = airport.getAirportName();
                }
            }
        }
        return airportName;
    }
}
