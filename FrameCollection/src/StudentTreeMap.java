// Student ID to Student Details mapping using TreeMap
import java.util.*;

class Student {
    String name;
    int age;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return name + " (" + age + " years old)";
    }
}

public class StudentTreeMap {
    public static void main(String[] args) {
        TreeMap<Integer, Student> students = new TreeMap<>();
        students.put(101, new Student("Rajon", 22));
        students.put(102, new Student("Sahriair", 23));
        students.put(103, new Student("Rahat", 21));

        for (Map.Entry<Integer, Student> entry : students.entrySet()) {
            System.out.println("ID: " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
