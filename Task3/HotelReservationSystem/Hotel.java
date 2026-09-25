package Task3.HotelReservationSystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Hotel {

    private List<Room> rooms;
    private List<Reservation> reservations;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Hotel() {

        FileManager.initializeFiles();

        rooms = FileManager.loadRooms();

        reservations = new ArrayList<>();
    }


    // ==========================================
    // DISPLAY ALL ROOMS
    // ==========================================

    public void displayAllRooms() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("              ALL ROOMS");
        System.out.println("==========================================");

        if (rooms.isEmpty()) {

            System.out.println(
                    "No rooms available."
            );

            return;
        }

        for (Room room : rooms) {
            System.out.println(room);
        }
    }


    // ==========================================
    // SEARCH ROOMS
    // ==========================================

    public void searchRooms(String category) {

        boolean found = false;

        System.out.println();
        System.out.println("==========================================");
        System.out.println(
                "AVAILABLE " +
                category.toUpperCase() +
                " ROOMS"
        );
        System.out.println("==========================================");

        for (Room room : rooms) {

            if (room.isAvailable()
                    && room.getCategory()
                    .equalsIgnoreCase(category)) {

                System.out.println(room);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No available rooms found."
            );
        }
    }


    // ==========================================
    // FIND ROOM
    // ==========================================

    public Room findRoom(int roomNumber) {

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }


    // ==========================================
    // MAKE RESERVATION
    // ==========================================

    public Reservation makeReservation(
            String customerName,
            String phone,
            int roomNumber,
            String checkIn,
            String checkOut) {

        Room room = findRoom(roomNumber);

        if (room == null) {

            System.out.println(
                    "Room does not exist."
            );

            return null;
        }

        if (!room.isAvailable()) {

            System.out.println(
                    "Room is already occupied."
            );

            return null;
        }

        try {

            LocalDate startDate =
                    LocalDate.parse(checkIn);

            LocalDate endDate =
                    LocalDate.parse(checkOut);

            if (!endDate.isAfter(startDate)) {

                System.out.println(
                        "Check-out date must be after "
                                + "check-in date."
                );

                return null;
            }

            long numberOfNights =
                    ChronoUnit.DAYS.between(
                            startDate,
                            endDate
                    );

            double totalAmount =
                    numberOfNights *
                    room.getPricePerNight();


            String bookingId =
                    generateBookingId();

            String customerId =
                    generateCustomerId();


            Customer customer =
                    new Customer(
                            customerId,
                            customerName,
                            phone
                    );


            Reservation reservation =
                    new Reservation(
                            bookingId,
                            customer,
                            room,
                            checkIn,
                            checkOut,
                            (int) numberOfNights,
                            totalAmount,
                            "PENDING"
                    );


            room.setAvailable(false);

            reservations.add(reservation);


            FileManager.saveRooms(rooms);

            FileManager.saveReservation(
                    reservation
            );


            System.out.println();
            System.out.println(
                    "Reservation created successfully!"
            );

            reservation.displayDetails();

            return reservation;

        } catch (Exception e) {

            System.out.println(
                    "Invalid date."
            );

            System.out.println(
                    "Use format: YYYY-MM-DD"
            );

            return null;
        }
    }


    // ==========================================
    // GENERATE BOOKING ID
    // ==========================================

    private String generateBookingId() {

        return "BK" +
                (1001 + reservations.size());
    }


    // ==========================================
    // GENERATE CUSTOMER ID
    // ==========================================

    private String generateCustomerId() {

        return "CUS" +
                (1001 + reservations.size());
    }


    // ==========================================
    // FIND RESERVATION
    // ==========================================

    public Reservation findReservation(
            String bookingId) {

        for (Reservation reservation :
                reservations) {

            if (reservation.getBookingId()
                    .equalsIgnoreCase(bookingId)) {

                return reservation;
            }
        }

        return null;
    }


    // ==========================================
    // MAKE PAYMENT
    // ==========================================

    public boolean makePayment(
            String bookingId,
            String paymentMethod) {

        Reservation reservation =
                findReservation(bookingId);

        if (reservation == null) {

            System.out.println(
                    "Booking not found."
            );

            return false;
        }


        if (reservation.getBookingStatus()
                .equalsIgnoreCase("CANCELLED")) {

            System.out.println(
                    "Cannot pay for a cancelled booking."
            );

            return false;
        }


        if (reservation.getBookingStatus()
                .equalsIgnoreCase("CONFIRMED")) {

            System.out.println(
                    "Payment has already been completed."
            );

            return false;
        }


        System.out.println();
        System.out.println(
                "Processing payment..."
        );


        try {

            Thread.sleep(1500);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }


        String transactionId =
                "TXN" +
                (System.currentTimeMillis() % 100000);


        Payment payment =
                new Payment(
                        transactionId,
                        bookingId,
                        reservation.getTotalAmount(),
                        paymentMethod,
                        "SUCCESS"
                );


        reservation.setBookingStatus(
                "CONFIRMED"
        );


        FileManager.savePayment(payment);


        System.out.println();
        System.out.println(
                "Payment successful!"
        );

        System.out.println(
                "Transaction ID : " +
                transactionId
        );

        System.out.printf(
                "Amount Paid    : Rs. %.2f%n",
                payment.getAmount()
        );

        System.out.println(
                "Payment Method : " +
                payment.getPaymentMethod()
        );


        return true;
    }


    // ==========================================
    // CANCEL RESERVATION
    // ==========================================

    public boolean cancelReservation(
            String bookingId) {

        Reservation reservation =
                findReservation(bookingId);


        if (reservation == null) {

            System.out.println(
                    "Booking not found."
            );

            return false;
        }


        if (reservation.getBookingStatus()
                .equalsIgnoreCase("CANCELLED")) {

            System.out.println(
                    "Booking is already cancelled."
            );

            return false;
        }


        reservation.setBookingStatus(
                "CANCELLED"
        );


        Room room =
                reservation.getRoom();

        room.setAvailable(true);


        FileManager.saveRooms(rooms);


        System.out.println();
        System.out.println(
                "Booking cancelled successfully."
        );

        System.out.println(
                "Room " +
                room.getRoomNumber() +
                " is now available."
        );


        return true;
    }


    // ==========================================
    // VIEW BOOKING
    // ==========================================

    public void viewBooking(
            String bookingId) {

        Reservation reservation =
                findReservation(bookingId);


        if (reservation == null) {

            System.out.println(
                    "Booking not found."
            );

            return;
        }


        reservation.displayDetails();
    }


    // ==========================================
    // DISPLAY ALL RESERVATIONS
    // ==========================================

    public void displayAllReservations() {

        System.out.println();
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "          ALL RESERVATIONS"
        );

        System.out.println(
                "=========================================="
        );


        if (reservations.isEmpty()) {

            System.out.println(
                    "No reservations found."
            );

            return;
        }


        for (Reservation reservation :
                reservations) {

            System.out.println(
                    "Booking ID : " +
                    reservation.getBookingId()
            );

            System.out.println(
                    "Customer   : " +
                    reservation.getCustomer().getName()
            );

            System.out.println(
                    "Room       : " +
                    reservation.getRoom().getRoomNumber()
            );

            System.out.println(
                    "Status     : " +
                    reservation.getBookingStatus()
            );

            System.out.println(
                    "------------------------------------------"
            );
        }
    }


    // ==========================================
    // LOAD EXISTING RESERVATIONS
    // ==========================================

    public void loadExistingReservations() {

        List<String> data =
                FileManager.loadReservations();


        for (String line : data) {

            try {

                String[] values =
                        line.split("\\|");


                String bookingId =
                        values[0];

                String customerId =
                        values[1];

                String customerName =
                        values[2];

                String phone =
                        values[3];

                int roomNumber =
                        Integer.parseInt(values[4]);

                String checkIn =
                        values[6];

                String checkOut =
                        values[7];

                int nights =
                        Integer.parseInt(values[8]);

                double total =
                        Double.parseDouble(values[9]);

                String status =
                        values[10];


                Room room =
                        findRoom(roomNumber);


                if (room != null) {

                    Customer customer =
                            new Customer(
                                    customerId,
                                    customerName,
                                    phone
                            );


                    Reservation reservation =
                            new Reservation(
                                    bookingId,
                                    customer,
                                    room,
                                    checkIn,
                                    checkOut,
                                    nights,
                                    total,
                                    status
                            );


                    reservations.add(
                            reservation
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "Invalid reservation record skipped."
                );
            }
        }
    }
}