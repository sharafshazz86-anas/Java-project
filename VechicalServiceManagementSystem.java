import java.io.Serializable;
import java.time.LocalDate;
import java.io.*;
import java.util.*;
import java.util.Scanner;

class Vehicle implements Serializable {
    private String vehicleNumber;
    private String ownerName;
    private String serviceType;
    private String mechanicName;
    private String serviceDescription;
    private double cost;
    private LocalDate serviceDate;
    private int kmsDriven;
    private String showroomHistory;

    public Vehicle(String vehicleNumber, String ownerName, String serviceType,
                   String mechanicName, String serviceDescription, double cost,
                   int kmsDriven, String showroomHistory) {
        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.serviceType = serviceType;
        this.mechanicName = mechanicName;
        this.serviceDescription = serviceDescription;
        this.cost = cost;
        this.kmsDriven = kmsDriven;
        this.showroomHistory = showroomHistory;
        this.serviceDate = LocalDate.now(); // current date
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    @Override
    public String toString() {
        return "Vehicle No: " + vehicleNumber +
               "\nOwner Name: " + ownerName +
               "\nService Type: " + serviceType +
               "\nMechanic Name: " + mechanicName +
               "\nService Description: " + serviceDescription +
               "\nKMs Driven: " + kmsDriven +
               "\nShowroom History: " + showroomHistory +
               "\nCost: ₹" + cost +
               "\nService Date: " + serviceDate +
               "\n---------------------------------";
    }
}

class ServiceManager {
    private ArrayList<Vehicle> vehicleList;
    private final String FILE_NAME = "vehicles.dat";

    public ServiceManager() {
        loadFromFile();
    }

    public void addVehicle(Vehicle v) {
        vehicleList.add(v);
        saveToFile();
        System.out.println("Vehicle service record added successfully!");
    }

    public void displayAll() {
        if (vehicleList.isEmpty()) {
            System.out.println("No service records found.");
            return;
        }
        System.out.println("\n==== Vehicle Service Records ====");
        for (Vehicle v : vehicleList) {
            System.out.println(v);
        }
    }

    public void searchByVehicleNumber(String number) {
        boolean found = false;
        for (Vehicle v : vehicleList) {
            if (v.getVehicleNumber().equalsIgnoreCase(number)) {
                System.out.println("\nRecord Found:");
                System.out.println(v);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No record found with vehicle number: " + number);
        }
    }

    public void delete(String number) {
        boolean removed = vehicleList.removeIf(v -> v.getVehicleNumber().equalsIgnoreCase(number));
        if (removed) {
            saveToFile();
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("No record found with vehicle number: " + number);
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(vehicleList);
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            vehicleList = (ArrayList<Vehicle>) ois.readObject();
        } catch (Exception e) {
            vehicleList = new ArrayList<>();
        }
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServiceManager sm = new ServiceManager();

        while (true) {
            System.out.println("\n==== Smart Vehicle Service Management ====");
            System.out.println("1. Add Vehicle Service Record");
            System.out.println("2. Display All Records");
            System.out.println("3. Search by Vehicle Number");
            System.out.println("4. Delete Record");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Vehicle Number: ");
                    String vNo = sc.nextLine();

                    System.out.print("Owner Name: ");
                    String owner = sc.nextLine();

                    System.out.print("Service Type: ");
                    String type = sc.nextLine();

                    System.out.print("Mechanic Name: ");
                    String mechanic = sc.nextLine();

                    System.out.print("Service Description: ");
                    String desc = sc.nextLine();

                    System.out.print("Cost: ₹");
                    double cost = sc.nextDouble();

                    System.out.print("KMs Driven: ");
                    int kms = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Showroom History: ");
                    String showroom = sc.nextLine();

                    Vehicle v = new Vehicle(vNo, owner, type, mechanic, desc, cost, kms, showroom);
                    sm.addVehicle(v);
                    break;

                case 2:
                    sm.displayAll();
                    break;

                case 3:
                    System.out.print("Enter Vehicle Number to search: ");
                    String searchNo = sc.nextLine();
                    sm.searchByVehicleNumber(searchNo);
                    break;

                case 4:
                    System.out.print("Enter Vehicle Number to delete: ");
                    String delNo = sc.nextLine();
                    sm.delete(delNo);
                    break;

                case 5:
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
