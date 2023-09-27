
import java.time.LocalDate;
import java.util.*;
import java.io.*;
public class Hotel implements Serializable {
    public static final String FILE = "hotel.bin";
    private final List<Room> rooms;
    private final History history;
    private final List<CustomerUser> customers;
    private final List<OperatorUser> operators;

    public Hotel() {
        rooms = new ArrayList<>();
        customers = new ArrayList<>();
        operators = new ArrayList<>();
        history = new History();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public History getHistory() {
        return history;
    }

    public List<CustomerUser> getCustomers() {
        return customers;
    }
    public void addBooked(Room room, CustomerUser customerUser, LocalDate startDate, LocalDate endDate){
        this.history.addBooked(room, customerUser, startDate, endDate);
    }
    //method checks if the room is booked for that day
    public boolean isBooked(Room room, LocalDate startDate,LocalDate endDate) {
        boolean result = true;
        for (Booked booked : this.history.getBookedList()) {
            if(booked.getRoom() == room && !(booked.getStartDate().isBefore(startDate) && booked.getStartDate().isBefore(endDate)))
                result = false;
            else if(booked.getRoom() == room && !(booked.getEndDate().isAfter(startDate) && booked.getEndDate().isAfter(endDate)))
                result = false;
        }
        return result;

    }
    public void addRoom(Room room) {
        rooms.add(room);
    }

    //checks the customer's access to the system
    public boolean loginCustomer(String email, String password){
        try {
            for (CustomerUser customerUser : this.customers) {
                if(customerUser.getEmail().equals(email) && customerUser.getPassword().equals(password)){
                    System.out.println("Welcome");
                    return true;
                }
            }
            throw new Exception("not exist customer");
        }
        catch (Exception e){
            System.out.println("Incorrect data");
            return false;
        }

    }
    //checks the operator's access to the system
    public boolean logInOperator(String email, String password){
        try {
            for (OperatorUser operatorUser : this.operators) {
                if(operatorUser.getEmail().equals(email) && operatorUser.getPassword().equals(password)){
                    System.out.println("Welcome");
                    return true;
                }
            }
            throw new Exception("not exist customer");
        }
        catch (Exception e){
            System.out.println("Incorrect data");
            return false;
        }
    }
    //adds a customer to the customers list
    public boolean addCustomer(CustomerUser customer) {
        for (CustomerUser customer1 : customers) {
            if(customer1.getEmail().equals(customer.getEmail())) {
                System.out.println(customer.getName() + " is already registered");
                return false;
            }
        }
        customers.add(customer);
        System.out.println(customer.getName() + " is added");
        return true;
    }
    //adds an operator to the operators list
    public boolean addOperator(OperatorUser operator) {
        for (OperatorUser operator1 : operators) {
            if(operator.getId() == operator1.getId()) {
                System.out.println(operator.getName() + " is already registered");
                return false;
            }
        }
        operators.add(operator);
        System.out.println(operator.getName() + " is added");
        return true;
    }

    public void saveStateToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(this);
            System.out.println("Hotel database saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving hotel database: " + e.getMessage());
        }
    }

    public static Hotel loadStateFromFile() {
        Hotel hotel;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Hotel.FILE))) {
            hotel = (Hotel) ois.readObject();
            if(hotel.rooms.isEmpty()){
                for (int i = 0; i < 5; i++) {
                    Room room1 = new Room(RoomType.SINGLE);
                    hotel.addRoom(room1);
                }
                for (int i = 0; i < 5; i++) {
                    Room room2 = new Room(RoomType.DELUXE);
                    hotel.addRoom(room2);
                }
                for (int i = 0; i < 5; i++) {
                    Room room3 = new Room(RoomType.DOUBLE);
                    hotel.addRoom(room3);
                }
            }
            System.out.println("Hotel database loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Hotel database file not found. Starting with an empty database.");
            hotel = new Hotel();
            if(hotel.rooms.isEmpty()){
                for (int i = 0; i < 5; i++) {
                    Room room1 = new Room(RoomType.SINGLE);
                    hotel.addRoom(room1);
                }
                for (int i = 0; i < 5; i++) {
                    Room room2 = new Room(RoomType.DELUXE);
                    hotel.addRoom(room2);
                }
                for (int i = 0; i < 5; i++) {
                    Room room3 = new Room(RoomType.DOUBLE);
                    hotel.addRoom(room3);
                }
            }
        }
        return hotel;
    }

    public CustomerUser selectCustomer(String email){
        for (CustomerUser user:
                this.customers) {
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

}

