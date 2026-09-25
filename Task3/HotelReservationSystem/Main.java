
package Task3.HotelReservationSystem;

import java.util.Scanner;

public class Main {

    private static Scanner scanner =
            new Scanner(System.in);


    public static void main(String[] args) {

        Hotel hotel = new Hotel();

        hotel.loadExistingReservations();


        while (true) {

            displayMenu();

            int choice =
                    readInt(
                            "Enter your choice: "
                    );


            switch (choice) {

                case 1:

                    hotel.displayAllRooms();

                    break;


                case 2:

                    searchRooms(hotel);

                    break;


                case 3:

                    makeReservation(hotel);

                    break;


                case 4:

                    makePayment(hotel);

                    break;


                case 5:

                    viewBooking(hotel);

                    break;


                case 6:

                    cancelBooking(hotel);

                    break;


                case 7:

                    hotel.displayAllReservations();

                    break;


                case 8:

                    System.out.println();

                    System.out.println(
                            "Thank you for using "
                                    + "Hotel Reservation System!"
                    );

                    scanner.close();

                    return;


                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }


    // ==========================================
    // DISPLAY MENU
    // ==========================================

    private static void displayMenu() {

        System.out.println();

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "       HOTEL RESERVATION SYSTEM"
        );

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "1. View All Rooms"
        );

        System.out.println(
                "2. Search Available Rooms"
        );

        System.out.println(
                "3. Make Reservation"
        );

        System.out.println(
                "4. Make Payment"
        );

        System.out.println(
                "5. View Booking Details"
        );

        System.out.println(
                "6. Cancel Reservation"
        );

        System.out.println(
                "7. View All Reservations"
        );

        System.out.println(
                "8. Exit"
        );

        System.out.println(
                "=========================================="
        );
    }


    // ==========================================
    // SEARCH ROOMS
    // ==========================================

    private static void searchRooms(
            Hotel hotel) {

        System.out.println();

        System.out.println(
                "1. Standard"
        );

        System.out.println(
                "2. Deluxe"
        );

        System.out.println(
                "3. Suite"
        );


        int choice =
                readInt(
                        "Select room category: "
                );


        String category;


        switch (choice) {

            case 1:

                category = "Standard";

                break;


            case 2:

                category = "Deluxe";

                break;


            case 3:

                category = "Suite";

                break;


            default:

                System.out.println(
                        "Invalid category."
                );

                return;
        }


        hotel.searchRooms(category);
    }


    // ==========================================
    // MAKE RESERVATION
    // ==========================================

    private static void makeReservation(
            Hotel hotel) {

        System.out.println();

        System.out.println(
                "========== MAKE RESERVATION =========="
        );


        String name =
                readString(
                        "Enter customer name: "
                );


        String phone =
                readString(
                        "Enter phone number: "
                );


        hotel.displayAllRooms();


        int roomNumber =
                readInt(
                        "\nEnter room number: "
                );


        String checkIn =
                readString(
                        "Enter check-in date (YYYY-MM-DD): "
                );


        String checkOut =
                readString(
                        "Enter check-out date (YYYY-MM-DD): "
                );


        hotel.makeReservation(
                name,
                phone,
                roomNumber,
                checkIn,
                checkOut
        );
    }


    // ==========================================
    // PAYMENT
    // ==========================================

    private static void makePayment(
            Hotel hotel) {

        System.out.println();

        System.out.println(
                "========== PAYMENT =========="
        );


        String bookingId =
                readString(
                        "Enter Booking ID: "
                );


        System.out.println();

        System.out.println(
                "1. UPI"
        );

        System.out.println(
                "2. Credit Card"
        );

        System.out.println(
                "3. Debit Card"
        );

        System.out.println(
                "4. Cash"
        );


        int choice =
                readInt(
                        "Select payment method: "
                );


        String method;


        switch (choice) {

            case 1:

                method = "UPI";

                break;


            case 2:

                method = "Credit Card";

                break;


            case 3:

                method = "Debit Card";

                break;


            case 4:

                method = "Cash";

                break;


            default:

                System.out.println(
                        "Invalid payment method."
                );

                return;
        }


        hotel.makePayment(
                bookingId,
                method
        );
    }


    // ==========================================
    // VIEW BOOKING
    // ==========================================

    private static void viewBooking(
            Hotel hotel) {

        System.out.println();

        String bookingId =
                readString(
                        "Enter Booking ID: "
                );


        hotel.viewBooking(
                bookingId
        );
    }


    // ==========================================
    // CANCEL BOOKING
    // ==========================================

    private static void cancelBooking(
            Hotel hotel) {

        System.out.println();

        System.out.println(
                "========== CANCEL BOOKING =========="
        );


        String bookingId =
                readString(
                        "Enter Booking ID: "
                );


        hotel.cancelReservation(
                bookingId
        );
    }


    // ==========================================
    // READ INTEGER
    // ==========================================

    private static int readInt(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }


    // ==========================================
    // READ STRING
    // ==========================================

    private static String readString(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();


            if (!input.isEmpty()) {

                return input;
            }


            System.out.println(
                    "Input cannot be empty."
            );
        }
    }
}
