package com.reservation.model;

public class Bus {
    private int id;
    private String route;
    private String departureTime;
    private int totalSeats;
    private int availableSeats;

    public Bus(int id, String route, String departureTime, int totalSeats, int availableSeats) {
        this.id = id;
        this.route = route;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getRoute() { return route; }
    public String getDepartureTime() { return departureTime; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }

    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", route='" + route + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", totalSeats=" + totalSeats +
                ", availableSeats=" + availableSeats +
                '}';
    }
}

