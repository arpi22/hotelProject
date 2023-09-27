import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Booked implements Serializable {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Room room;
    private final CustomerUser customerUser;
    private final static double taxRate = 0.2;
    private final static double serviceFeeRate = 0.1;


    public Booked(Room room, CustomerUser customerUser,LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.customerUser = customerUser;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Room getRoom() {
        return room;
    }

    public CustomerUser getCustomerUser() {
        return customerUser;
    }
    //Bill for a room for booking period.
    public double easyBill(){
        double sum = 0;
        long daysCount = ChronoUnit.DAYS.between(this.getStartDate(), this.getEndDate());
        daysCount++;
        sum += daysCount * (this.getRoom().getType().getPricePerDay() + this.getRoom().getType().getPricePerDay() * taxRate + this.getRoom().getType().getPricePerDay() * serviceFeeRate);
        System.out.println("The price of the room for " + daysCount + " days will be " +sum + " dollars");
        return sum;
    }

    @Override
    public String toString() {
        return "Booked{" +
        " customerUser=" + customerUser +
                ", room=" + room +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", price = " + easyBill() +"dollars"+
                '}';
    }

}
