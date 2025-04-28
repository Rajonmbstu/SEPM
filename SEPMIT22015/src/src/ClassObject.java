package src;

class Car {
    String color;
    int speed;

    void display() {
        System.out.println("Car is driving...");
        System.out.println("Color: " + color);
        System.out.println("Speed: " + speed);
    }
}

public class ClassObject {
    public static void main(String[] args) {
        Car myCar = new Car(); // object creation
        myCar.color = "Red";
        myCar.speed = 100;
        myCar.display();
    }
}