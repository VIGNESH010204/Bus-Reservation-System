package com.reservation.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.reservation.model.Passenger;
import com.reservation.storage.DBConnection;

public class PassengerService {

    public void addPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (name, age, email) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, passenger.getName());
            ps.setInt(2, passenger.getAge());
            ps.setString(3, passenger.getEmail());
            ps.executeUpdate();
            System.out.println("✅ Passenger registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Passenger> getAllPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM passengers";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                passengers.add(new Passenger(
                        rs.getInt("passenger_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }
}
