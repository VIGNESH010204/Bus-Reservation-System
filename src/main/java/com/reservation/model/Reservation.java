


package com.reservation.model;

public class Reservation {
    private int id;
    private Passenger passenger;
    private Bus bus;

    public Reservation(int id, Passenger passenger, Bus bus) {
        this.id = id;
        this.passenger = passenger;
        this.bus = bus;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + id +
               " | Passenger: " + passenger.getName() +
               " | Bus: " + bus.getRoute() +
               " | Time: " + bus.getDepartureTime();
    }
}
