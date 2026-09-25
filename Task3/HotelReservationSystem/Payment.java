package Task3.HotelReservationSystem;


public class Payment {

    private String transactionId;
    private String bookingId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;

    public Payment(String transactionId,
                   String bookingId,
                   double amount,
                   String paymentMethod,
                   String paymentStatus) {

        this.transactionId = transactionId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}