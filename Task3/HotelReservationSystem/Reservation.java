package Task3.HotelReservationSystem;


public class Reservation {

    private String bookingId;
    private Customer customer;
    private Room room;
    private String checkIn;
    private String checkOut;
    private int nights;
    private double totalAmount;
    private String bookingStatus;

    public Reservation(String bookingId,
                       Customer customer,
                       Room room,
                       String checkIn,
                       String checkOut,
                       int nights,
                       double totalAmount,
                       String bookingStatus) {

        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void displayDetails() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("            BOOKING DETAILS");
        System.out.println("==========================================");

        System.out.println("Booking ID       : " + bookingId);
        System.out.println("Customer ID      : " + customer.getCustomerId());
        System.out.println("Customer Name    : " + customer.getName());
        System.out.println("Phone            : " + customer.getPhone());

        System.out.println("------------------------------------------");

        System.out.println("Room Number      : " + room.getRoomNumber());
        System.out.println("Room Category    : " + room.getCategory());

        System.out.printf(
                "Price Per Night  : Rs. %.2f%n",
                room.getPricePerNight()
        );

        System.out.println("Check-in         : " + checkIn);
        System.out.println("Check-out        : " + checkOut);
        System.out.println("Number of Nights : " + nights);

        System.out.println("------------------------------------------");

        System.out.printf(
                "Total Amount     : Rs. %.2f%n",
                totalAmount
        );

        System.out.println("Booking Status   : " + bookingStatus);

        System.out.println("==========================================");
    }
}