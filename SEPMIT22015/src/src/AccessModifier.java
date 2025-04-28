package src;

class Person {
    private String name;  // private: accessible only within the class

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }
}

public class AccessModifier {
    public static void main(String[] args) {
        Person p = new Person();
        p.setName("Rajon ISlam Noyon");
        System.out.println(p.getName());
    }
}
