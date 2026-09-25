package Task3.HotelReservationSystem;

public class Room {

    private int roomNumber;
    private String category;
    private double pricePerNight;
    private boolean available;

    public Room(int roomNumber, String category,
                double pricePerNight, boolean available) {

        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.available = available;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {

        return String.format(
                "Room %-4d | %-10s | Rs. %.2f/night | %s",
                roomNumber,
                category,
                pricePerNight,
                available ? "Available" : "Occupied"
        );
    }
}