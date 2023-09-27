import java.io.Serializable;

public class Room implements Serializable {
    private final int id;
    private final RoomType type;
    private final double price;

    public Room( RoomType type) {
        this.id = System.identityHashCode(this);
        this.type = type;
        price = type.getPricePerDay();
    }

    public RoomType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Room ID: " + id + ", Type: " + type + " Description: " + type.getDescription() +"Price per day: " + price ;
    }
}
