package com.driver.services;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import org.springframework.stereotype.Service;
import com.driver.repositories.AirportRepository;
import com.driver.repositories.FlightRepository;
import com.driver.repositories.PassengerRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FlightService {
    FlightRepository flightRepository = new FlightRepository();
    PassengerRepository passengerRepository = new PassengerRepository();
    AirportRepository airportRepository = new AirportRepository();

    public String addFlight(Flight flight) {
        return flightRepository.addFlight(flight);
    }

    public String bookATicket(int flightId, int passengerId) {

        if(flightRepository.isFlight(flightId) && passengerRepository.isPassenger(passengerId) && flightRepository.bookATicket(flightId, passengerId)) {
            passengerRepository.bookATicket(flightId, passengerId);
            return "SUCCESS";
        }else {
            return "FAILURE";
        }
    }

    public String cancelATicket(int flightId, int passengerId) {
        if(flightRepository.cancelATicket(flightId, passengerId)) {
            passengerRepository.cancelATicket(flightId, passengerId);
            return "SUCCESS";
        }else {
            return "FAILURE";
        }
    }

    public String getAirportNameFromFlightId(int flightId) {
        City cityName = flightRepository.getCityNameFromFlightId(flightId);
        if(cityName != null) {
            for(Airport airport: airportRepository.getAllAirport().values()) {
                if(airport.getCity() == cityName) return airport.getAirportName();
            }
        }
        return null;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        Map<Integer, Flight> allFlight = flightRepository.getAllFlight();
        double max = Double.MAX_VALUE;
        double minDuration = max;
        for(Flight flight: allFlight.values()) {
            if(flight.getFromCity() == fromCity && flight.getToCity() == toCity) {
                minDuration = Math.min(minDuration, flight.getDuration());
            }
        }
        if(minDuration == max) minDuration = -1;
        return minDuration;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        City cityName = airportRepository.getCityByName(airportName);
        List<Integer> flightList = new ArrayList<>();
        if(cityName != null) {
            Map<Integer, Flight> flights = flightRepository.getAllFlight();
            for (Flight flight: flights.values()) {
                if(flight.getFlightDate().compareTo(date) == 0 && (flight.getFromCity().compareTo(cityName) == 0 || flight.getToCity().compareTo(cityName) == 0)) {
                    flightList.add(flight.getFlightId());
                }
            }
        }
        int numberOfPeople = 0;
        if(!flightList.isEmpty()) {
            for (int flightId: flightList) {
                numberOfPeople += flightRepository.getNumberOfPassengerByFlightId(flightId);
            }
        }
        return numberOfPeople;
    }

    public int calculateFlightFare(int flightId) {
        if(flightRepository.isFlight(flightId))
            return 3000 + 50*flightRepository.getNumberOfPassengerByFlightId(flightId);
        return 0;
    }

    public int calculateRevenueOfAFlight(int flightId) {
        return flightRepository.getRevenue(flightId);
    }
}
