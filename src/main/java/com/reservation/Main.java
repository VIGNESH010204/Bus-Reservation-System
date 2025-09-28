package com.reservation;

import java.util.List;
import java.util.Scanner;

import com.reservation.model.Bus;
import com.reservation.model.Passenger;
import com.reservation.model.Reservation;
import com.reservation.service.PassengerService;
import com.reservation.service.ReservationService;
import com.reservation.service.EmailService;
import com.reservation.service.OtpService;

public class Main {
    public static void main(String[] args) {
        ReservationService reservationService = new ReservationService();
        PassengerService passengerService = new PassengerService();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Bus Reservation System ===");
            System.out.println("1. View Buses");
            System.out.println("2. Register Passenger");
            System.out.println("3. View Passengers");
            System.out.println("4. Book Ticket");
            System.out.println("5. View Reservations");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                choice = -1;
            }

            switch (choice) {
                case 1:
                    List<Bus> buses = reservationService.getAvailableBuses();
                    buses.forEach(System.out::println);
                    break;

                case 2:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.print("Enter Phone Number: ");
                    String phone = sc.nextLine();

                    // 1️⃣ OTP verification via phone
                    String otp = OtpService.generateOtp();
                    OtpService.sendOtp(phone, otp);

                    System.out.print("Enter OTP received: ");
                    String userOtp = sc.nextLine();

                    if (!otp.equals(userOtp)) {
                        System.out.println("❌ OTP verification failed!");
                        break; // stop registration
                    }
                    System.out.println("✅ OTP verified successfully!");

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    // 2️⃣ Add passenger to service
                    passengerService.addPassenger(new Passenger(name, age, email, phone));

                    // 3️⃣ Send email confirmation
                    System.out.print("Enter Email: ");
                    email = sc.nextLine(); // ✅ use existing variable

                    // Add passenger to service
                    passengerService.addPassenger(new Passenger(name, age, email, phone));

                    // Send confirmation email
                    EmailService.sendConfirmation(email, name);

                    System.out.println("✅ Passenger registered successfully! Confirmation email sent.");

                case 3:
                    List<Passenger> passengers = passengerService.getAllPassengers();
                    passengers.forEach(System.out::println);
                    break;

                case 4:
                    System.out.print("Enter Bus ID: ");
                    int busId = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.print("Enter Passenger ID: ");
                    int passengerId = sc.nextInt();
                    sc.nextLine(); // consume newline

                    Reservation res = reservationService.makeReservation(busId, passengerId);
                    if (res != null) {
                        System.out.println("✅ Booking Confirmed: " + res);
                    } else {
                        System.out.println("❌ Booking Failed.");
                    }
                    break;

                case 5:
                    List<Reservation> reservations = reservationService.getAllReservations();
                    reservations.forEach(System.out::println);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    if (choice != -1) System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        sc.close();
    }
}
