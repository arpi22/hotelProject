import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class History implements Serializable{
    private final List<Booked> bookedList;

    public History() {
        bookedList = new ArrayList<>();
    }

    public List<Booked> getBookedList() {
        return bookedList;
    }

    //The method adds a booking to the list of booking list.
    public void addBooked(Room room, CustomerUser customerUser, LocalDate startDate, LocalDate endDate) {
        Booked booked = new Booked(room, customerUser, startDate, endDate);
        this.bookedList.add(booked);
        System.out.println("Room is added");
        booked.easyBill();
    }
    //The method calculates the revenue of 1 room over the history of the hotel.
    public void roomBill(Room room) {
        double sum = 0;
        try {
            int counter = 0;
            for (Booked booked : this.bookedList) {
                if (booked.getRoom() == room) {
                    counter++;

                    sum += booked.easyBill();
                }

            }
            System.out.println("Room revenue will be " + sum + " dollars");
            System.out.println("Room booked " + counter + " times");
        }
        catch (NullPointerException e){
            System.out.println("No booked list, please relaunch app " + e);
        }

    }
    //The method counts the amount spent per customer.
    public void customerBill(CustomerUser customerUser) {
        double sum = 0;
        try {
            int counter = 0;
            for (Booked booked : this.bookedList) {
                if (booked.getCustomerUser() == customerUser) {
                    counter++;
                    sum += booked.easyBill();
                }

            }
            System.out.println("Customer revenue will be " + sum + " dollars");
            System.out.println("Customer booked " + counter + " times");
        }
        catch (NullPointerException e){
            System.out.println("No booked list, please relaunch app " + e);
        }

    }
    // The method counts all revenue of hotel.
    public void allBill() {
        double sum = 0;
        try{
            for (Booked booked : this.bookedList) {
                sum += booked.easyBill();
            }
            System.out.println("Customers revenue will be " + sum + " dollars");
            System.out.println("Booked " + bookedList.size() + " times");
        } catch (NullPointerException e){
            System.out.println("No booked list, please relaunch app " + e);
        }
    }

    public boolean checkRoom(Room room) {
        for (Booked booked :
                this.bookedList) {
            if (booked.getRoom() == room) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCustomer(CustomerUser customerUser) {
        for (Booked booked :
                this.bookedList) {
            if (booked.getCustomerUser() == customerUser) {
                return true;
            }
        }
        return false;
    }

    public void saveHistory(String fileName){
        File file = new File(fileName);

        try (FileWriter fw = new FileWriter(file)) {
            for (Booked booked : bookedList) {
                fw.write(booked.toString() + '\n');
            }
            System.out.println("Successfully write to file.");
        }  catch (IOException e) {
            System.out.println("Not saved the data in a file");
        }
    }

}
