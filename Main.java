import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static Hotel hotel = new Hotel();
    private static final String keyHotel ="1234";
    public static void main(String[] args) {

        hotel = Hotel.loadStateFromFile();

        while (true) {
            System.out.println("Press 1 if you Operator. ");
            System.out.println("Press 2 if you Customer. ");
            System.out.println("Press 0 if you want to exit. ");
            if(input.hasNext()) {
                switch (input.next()) {
                    case "1": {
                        operatorMode();
                        break;
                    }
                    case "2": {
                        customerMode();
                        break;
                    }
                    case "0": {
                        hotel.saveStateToFile();
                        return;
                    }
                    default:
                        System.out.println("You entered incorrectly , try again");
                }
            }
            hotel.saveStateToFile();
        }


    }
    public static void operatorMode(){
        System.out.println("Input hotel key: ");
        String key = input.next();

        if(key.equals(keyHotel)){
            while (true){

                System.out.println("Press 1 if you have an account");
                System.out.println("Press 2 if you want registration");
                System.out.println("Press 0 if you want to exit. ");

                if (input.hasNext()){
                    switch (input.next()){
                        case "1" :{
                            System.out.println("Input your email: ");
                            String email = input.next();
                            System.out.println("Input your password: ");
                            String password = input.next();
                            if(hotel.logInOperator(email, password)){
                                operatorMenu();
                            }
                            else {
                                System.out.println("error");
                            }
                            break;
                        }
                        case "2":{
                            System.out.println("Input your name: ");
                            String name = input.next();
                            System.out.println("Input your email: ");
                            String email = input.next();
                            System.out.println("Input your password: ");
                            String password = input.next();
                            if(hotel.addOperator(new OperatorUser(name, email, password))){
                                operatorMenu();
                            }
                            break;
                        }
                        case "0" : return;
                        default:
                            System.out.println("You entered incorrectly , try again");
                    }
                }

            }
        }
        else {
            System.out.println("Invalid password");
        }
    }
    public static void operatorMenu() throws NullPointerException {
        while (true) {
            Scanner input = new Scanner(System.in);

            System.out.println("Press 1 if you want to Add Room. ");
            System.out.println("Press 2 if you want to Revenue Room. ");
            System.out.println("Press 3 if you want to Revenue Customer. ");
            System.out.println("Press 4 if you want to Revenue All. ");
            System.out.println("Press 5 if you want to Add Customer. ");
            System.out.println("Press 6 if get history booking in file. ");
            System.out.println("Press 0 if you want to exit. ");

            switch (input.nextLine()) {
                case "1": {
                    RoomType roomType = chooseRoomType();
                    if(roomType != null){
                        hotel.addRoom(new Room(roomType));
                    }
                    break;
                }
                case "2": {
                    Room room = chooseRoom();
                    if(room != null) {
                        if (hotel.getHistory().checkRoom(room)) {
                            hotel.getHistory().roomBill(room);
                        } else {
                            System.out.println("The room doesn't matter revenue ");
                        }
                    }
                        break;

                }
                case "3": {
                    CustomerUser customerUser = chooseCostumer();
                    if(customerUser != null) {
                        if (hotel.getHistory().checkCustomer(customerUser)) {
                            hotel.getHistory().customerBill(customerUser);
                        } else {
                            System.out.println("Costumer revenue will be sum 0.0 dollars");
                        }
                    }
                    break;
                }
                case "4": {
                    hotel.getHistory().allBill();
                    break;
                }
                case "5": {
                    System.out.println("Input customer name: ");
                    String name1 = input.next();
                    System.out.println("Input customer email: ");
                    String email1 = input.next();
                    System.out.println("Input customer password: ");
                    String password1 = input.next();
                    hotel.addCustomer(new CustomerUser(name1, email1, password1));
                    System.out.println("If you want to go to customer menu press 1: ");
                    if(input.next().equals("1")){
                        CustomerUser customerUser = hotel.selectCustomer(email1);
                        customerMenu(customerUser);
                    }
                    break;

                }
                case "6": {
                    System.out.println("Input filename");
                    String fileName = input.next();
                    hotel.getHistory().saveHistory(fileName+".txt");
                    break;
                }
                case "0":{
                    return;
                }

                default:
                    System.out.println("You entered incorrectly , try again");

            }
        }
    }

    public static void customerMode(){

        while (true){
            System.out.println("Press 1 if you have an account");
            System.out.println("Press 2 if you want registration");
            System.out.println("Press 0 if you want to exit. ");

            if (input.hasNext()){
                switch (input.nextLine()){
                    case "1" :{
                        System.out.println("Input your email: ");
                        String email = input.nextLine();
                        System.out.println("Input your password: ");
                        String password = input.nextLine();
                        if(hotel.loginCustomer(email, password)){
                            CustomerUser customerUser = hotel.selectCustomer(email);
                            customerMenu(customerUser);
                        }
                        else {
                            System.out.println("error");
                        }
                        break;

                    }
                    case "2":{
                        System.out.println("Input your name: ");
                        String name1 = input.next();
                        System.out.println("Input your email: ");
                        String email1 = input.next();
                        System.out.println("Input your password: ");
                        String password1 = input.next();
                        if(hotel.addCustomer(new CustomerUser(name1, email1, password1))) {
                            CustomerUser customerUser = hotel.selectCustomer(email1);
                            customerMenu(customerUser);
                        }
                        break;
                    }
                    case "0" : return;
                    default:
                        System.out.println("You entered incorrectly , try again");
                }
            }
        }
    }
    public static void customerMenu(CustomerUser customerUser){
        while (true){
            System.out.println("Press 1 if you want to booked a room. ");
            System.out.println("Press 0 if you want to exit. ");

            switch (input.next()) {
                case "1": {
                    RoomType roomType = chooseRoomType();
                    if(roomType != null) {
                        System.out.println("Input start date :Days/Month/Year");
                        LocalDate startDate = dateInput(input.next());
                        System.out.println("Input end date :Days/Month/Year");
                        LocalDate endDate = dateInput(input.next());
                        if ((startDate.isBefore(endDate) || startDate.equals(endDate)) && (startDate.equals(LocalDate.now()) || startDate.isAfter(LocalDate.now()))) {
                            for (int i = 0; i < hotel.getRooms().size(); i++) {
                                Room room = hotel.getRooms().get(i);
                                if (room.getType() == roomType && hotel.isBooked(room, startDate, endDate)) {
                                    hotel.addBooked(room, customerUser, startDate, endDate);
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Invalid date");
                        }
                    }
                    break;

                }
                case "0": {
                    return;
                }
                default:
                    System.out.println("You entered incorrectly , try again");

            }
        }
    }
    public static LocalDate dateInput(String userInput) {
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
            return LocalDate.parse(userInput, dateFormat);
        }
        catch (DateTimeParseException e){
            System.out.println("not correct format " + e);
            System.out.println("Selected time " + LocalDate.now());
            return LocalDate.now();
        }

    }
    private static RoomType chooseRoomType() {
        RoomType roomType = null;
        boolean exit = false;
        while (!exit) {

            System.out.println("\nChoose room type:");
            System.out.println("1. Single room");
            System.out.println("2. Double room");
            System.out.println("3. Deluxe room");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = input.next();

            switch (choice) {
                case "1" -> {
                    roomType = RoomType.SINGLE;
                    exit = true;
                }
                case "2" -> {
                    roomType = RoomType.DOUBLE;
                    exit = true;
                }
                case "3" -> {
                    roomType = RoomType.DELUXE;
                    exit = true;
                }
                case "0" -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        return roomType;
    }

    public static Room chooseRoom() {
        try {
            for (int i = 0; i < hotel.getRooms().size(); i++) {
                System.out.println(i + ". " + hotel.getRooms().get(i).toString());
            }
            System.out.println("Press index room for get room bill");
                return hotel.getRooms().get(Integer.parseInt(input.next()));

        }catch(IndexOutOfBoundsException | NumberFormatException e){
            System.out.println("There is no room with such an index");
            return null;
        }

    }

    public static CustomerUser chooseCostumer() {
        try {
            for (int i = 0; i < hotel.getCustomers().size(); i++) {
                System.out.println(i + ". " + hotel.getCustomers().get(i).toString());
            }
            System.out.println("Press index customer for get customer bill");
            return hotel.getCustomers().get(Integer.parseInt(input.next()));
        }catch(IndexOutOfBoundsException | NumberFormatException e){
            System.out.println("There is no Customer with such an index");
            return null;
        }

    }


}

