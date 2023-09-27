public enum RoomType {
    SINGLE("single bed, bathroom, TV, closet", 20.0),
    DOUBLE("double bed, bathroom, TV, closet", 35.0),
    DELUXE("minibar, bathtub, king-size bed, sitting area", 55.0);

    private final String description;
    private final double pricePerDay;


    RoomType(String description, double pricePerDay) {
        this.description = description;
        this.pricePerDay = pricePerDay;
    }

    public String getDescription() {
        return description;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }
}
