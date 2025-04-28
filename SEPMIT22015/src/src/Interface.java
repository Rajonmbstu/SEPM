package src;

interface An {
    void sound();
}

class Cat implements An {
    public void sound() {
        System.out.println("Meow");
    }
}

public class Interface {
    public static void main(String[] args) {
        Cat c = new Cat();
        c.sound();
    }
}