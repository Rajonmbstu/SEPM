interface Engine {
    void startEngine();  // Method to start the engine
    void stopEngine();   // Method to stop the engine
}

// Abstract class: base class for all vehicles
abstract class Vehicle {
    String brand;  // Common attribute for all vehicles
    int year;

    // Constructor: sets brand and year
    public Vehicle(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }

    // Abstract method: must be implemented by all subclasses
    abstract void drive();

    // Concrete method: shared behavior for all vehicles
    public void displayInfo() {
        System.out.println("Brand: " + brand + ", Year: " + year);
    }
}

// Subclass: Car inherits from Vehicle and implements Engine interface
class Car extends Vehicle implements Engine {

    // Constructor: calls superclass constructor to set brand and year
    public Car(String brand, int year) {
        super(brand, year);
    }

    // Implementing startEngine method from Engine interface
    @Override
    public void startEngine() {
        System.out.println("Car engine started.");
    }

    // Implementing stopEngine method from Engine interface
    @Override
    public void stopEngine() {
        System.out.println("Car engine stopped.");
    }

    // Implementing abstract drive method from Vehicle class
    @Override
    void drive() {
        System.out.println("Driving a car.");
    }
}

// Subclass: Bike inherits from Vehicle and implements Engine interface
class Bike extends Vehicle implements Engine {

    // Constructor: calls superclass constructor to set brand and year
    public Bike(String brand, int year) {
        super(brand, year);
    }

    // Implementing startEngine method from Engine interface
    @Override
    public void startEngine() {
        System.out.println("Bike engine started.");
    }

    // Implementing stopEngine method from Engine interface
    @Override
    public void stopEngine() {
        System.out.println("Bike engine stopped.");
    }

    // Implementing abstract drive method from Vehicle class
    @Override
    void drive() {
        System.out.println("Riding a bike.");
    }
}

// Main class: entry point of the program
public class Test_abstruct_Class {
    public static void main(String[] args) {

        // Create a Car object using a Vehicle reference (polymorphism)
        Vehicle car = new Car("Toyota", 2022);
        car.displayInfo();  // Calls concrete method from Vehicle
        ((Engine) car).startEngine(); // Cast to Engine to access startEngine
        car.drive();  // Calls overridden drive method in Car
        ((Engine) car).stopEngine();  // Cast to Engine to access stopEngine

        System.out.println();  // Just a line break

        // Create a Bike object using a Vehicle reference
        Vehicle bike = new Bike("Yamaha", 2023);
        bike.displayInfo();  // Calls concrete method from Vehicle
        ((Engine) bike).startEngine(); // Cast to Engine to access startEngine
        bike.drive();  // Calls overridden drive method in Bike
        ((Engine) bike).stopEngine();  // Cast to Engine to access stopEngine
    }
}