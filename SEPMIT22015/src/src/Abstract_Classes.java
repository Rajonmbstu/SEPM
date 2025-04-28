package src;

abstract class A{
    abstract void makeSound();

    void sleep() {
        System.out.println("Sleeping...");
    }
}

class D extends A {
    void makeSound() {
        System.out.println("Bark Bark");
    }
}

public class Abstract_Classes {
    public static void main(String[] args) {
        D d = new D();
        d.makeSound();
        d.sleep();
    }
}