package Task3.HotelReservationSystem;

import java.io.*;
import java.util.*;

public class FileManager {

    private static final String DATA_FOLDER = "data";

    private static final String ROOMS_FILE =
            DATA_FOLDER + File.separator + "rooms.txt";

    private static final String RESERVATIONS_FILE =
            DATA_FOLDER + File.separator + "reservations.txt";

    private static final String PAYMENTS_FILE =
            DATA_FOLDER + File.separator + "payments.txt";


    // ==========================================
    // INITIALIZE FILES
    // ==========================================

    public static void initializeFiles() {

        File folder = new File(DATA_FOLDER);

        if (!folder.exists()) {
            folder.mkdir();
        }

        File roomsFile = new File(ROOMS_FILE);

        if (!roomsFile.exists()) {
            createDefaultRooms();
        }

        createFileIfNotExists(RESERVATIONS_FILE);
        createFileIfNotExists(PAYMENTS_FILE);
    }


    // ==========================================
    // CREATE FILE
    // ==========================================

    private static void createFileIfNotExists(String fileName) {

        try {

            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error creating file: " + e.getMessage()
            );
        }
    }


    // ==========================================
    // CREATE DEFAULT ROOMS
    // ==========================================

    private static void createDefaultRooms() {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(ROOMS_FILE))) {

            writer.write("101,Standard,1500.0,true");
            writer.newLine();

            writer.write("102,Standard,1500.0,true");
            writer.newLine();

            writer.write("103,Standard,1500.0,true");
            writer.newLine();

            writer.write("201,Deluxe,2500.0,true");
            writer.newLine();

            writer.write("202,Deluxe,2500.0,true");
            writer.newLine();

            writer.write("203,Deluxe,2500.0,true");
            writer.newLine();

            writer.write("301,Suite,4000.0,true");
            writer.newLine();

            writer.write("302,Suite,4000.0,true");
            writer.newLine();

        } catch (IOException e) {

            System.out.println(
                    "Error creating default rooms."
            );
        }
    }


    // ==========================================
    // LOAD ROOMS
    // ==========================================

    public static List<Room> loadRooms() {

        List<Room> rooms = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(ROOMS_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                int roomNumber =
                        Integer.parseInt(data[0]);

                String category =
                        data[1];

                double price =
                        Double.parseDouble(data[2]);

                boolean available =
                        Boolean.parseBoolean(data[3]);

                Room room = new Room(
                        roomNumber,
                        category,
                        price,
                        available
                );

                rooms.add(room);
            }

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Error loading rooms: " + e.getMessage()
            );
        }

        return rooms;
    }


    // ==========================================
    // SAVE ROOMS
    // ==========================================

    public static void saveRooms(List<Room> rooms) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(ROOMS_FILE))) {

            for (Room room : rooms) {

                writer.write(
                        room.getRoomNumber() + "," +
                        room.getCategory() + "," +
                        room.getPricePerNight() + "," +
                        room.isAvailable()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving rooms: " + e.getMessage()
            );
        }
    }


    // ==========================================
    // SAVE RESERVATION
    // ==========================================

    public static void saveReservation(
            Reservation reservation) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     RESERVATIONS_FILE,
                                     true))) {

            Customer customer =
                    reservation.getCustomer();

            writer.write(
                    reservation.getBookingId() + "|" +
                    customer.getCustomerId() + "|" +
                    customer.getName() + "|" +
                    customer.getPhone() + "|" +
                    reservation.getRoom().getRoomNumber() + "|" +
                    reservation.getRoom().getCategory() + "|" +
                    reservation.getCheckIn() + "|" +
                    reservation.getCheckOut() + "|" +
                    reservation.getNights() + "|" +
                    reservation.getTotalAmount() + "|" +
                    reservation.getBookingStatus()
            );

            writer.newLine();

        } catch (IOException e) {

            System.out.println(
                    "Error saving reservation: "
                            + e.getMessage()
            );
        }
    }


    // ==========================================
    // LOAD RESERVATIONS
    // ==========================================

    public static List<String> loadReservations() {

        List<String> reservations =
                new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(
                                     RESERVATIONS_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    reservations.add(line);
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading reservations: "
                            + e.getMessage()
            );
        }

        return reservations;
    }


    // ==========================================
    // SAVE PAYMENT
    // ==========================================

    public static void savePayment(
            Payment payment) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     PAYMENTS_FILE,
                                     true))) {

            writer.write(
                    payment.getTransactionId() + "|" +
                    payment.getBookingId() + "|" +
                    payment.getAmount() + "|" +
                    payment.getPaymentMethod() + "|" +
                    payment.getPaymentStatus()
            );

            writer.newLine();

        } catch (IOException e) {

            System.out.println(
                    "Error saving payment: "
                            + e.getMessage()
            );
        }
    }
}