package src;

class Animal {
    protected String type = "Animal";

    void display() {
        System.out.println("This is an animal.");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println(type + " says eahh!");
    }
}

public class Inheritance_and_Protected_Access {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.display();
        d.bark();
    }
}