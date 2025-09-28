package com.reservation.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.reservation.model.Bus;
import com.reservation.model.Passenger;
import com.reservation.model.Reservation;
import com.reservation.storage.DBConnection;


public class ReservationService {

    // Get all available buses
    public List<Bus> getAvailableBuses() {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bus bus = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("route"),
                        rs.getString("departure_time"),
                        rs.getInt("total_seats"),
                        rs.getInt("available_seats")
                );
                buses.add(bus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }

    // Make a reservation
    public Reservation makeReservation(int busId, int passengerId) {
        String checkSeats = "SELECT route, departure_time, available_seats FROM buses WHERE bus_id=? FOR UPDATE";
        String updateSeats = "UPDATE buses SET available_seats = available_seats - 1 WHERE bus_id=?";
        String insertRes = "INSERT INTO reservations (bus_id, passenger_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psCheck = conn.prepareStatement(checkSeats);
                 PreparedStatement psUpdate = conn.prepareStatement(updateSeats);
                 PreparedStatement psInsert = conn.prepareStatement(insertRes, Statement.RETURN_GENERATED_KEYS)) {

                // Check seat availability
                psCheck.setInt(1, busId);
                ResultSet rs = psCheck.executeQuery();
                if (!rs.next()) {
                    conn.rollback();
                    return null;
                }

                int availableSeats = rs.getInt("available_seats");
                String route = rs.getString("route");
                String departureTime = rs.getString("departure_time");

                if (availableSeats <= 0) {
                    conn.rollback();
                    return null;
                }

                // Update seats
                psUpdate.setInt(1, busId);
                psUpdate.executeUpdate();

                // Insert reservation
                psInsert.setInt(1, busId);
                psInsert.setInt(2, passengerId);
                psInsert.executeUpdate();

                // Get reservation ID
                int resId = -1;
                try (ResultSet keys = psInsert.getGeneratedKeys()) {
                    if (keys.next()) {
                        resId = keys.getInt(1);
                    }
                }

                conn.commit();

                Passenger passenger = new Passenger(passengerId, "Unknown", -1, "");
                Bus bus = new Bus(busId, route, departureTime, -1, availableSeats - 1);

                return new Reservation(resId, passenger, bus);

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.id, p.passenger_id, p.name, p.age, p.email, " +
                     "b.bus_id, b.route, b.departure_time " +
                     "FROM reservations r " +
                     "JOIN passengers p ON r.passenger_id = p.passenger_id " +
                     "JOIN buses b ON r.bus_id = b.bus_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Passenger p = new Passenger(
                        rs.getInt("passenger_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("email")
                );

                Bus b = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("route"),
                        rs.getString("departure_time"),
                        -1, -1
                );

                reservations.add(new Reservation(rs.getInt("id"), p, b));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}
