// Checks if two LinkedLists are equal
import java.util.*;

public class LinkedListEquality {
    public static void main(String[] args) {
        LinkedList<Integer> list1 = new LinkedList<>(Arrays.asList(1, 2, 3));
        LinkedList<Integer> list2 = new LinkedList<>(Arrays.asList(1, 2, 3));
        LinkedList<Integer> list3 = new LinkedList<>(Arrays.asList(1, 3, 2));

        System.out.println("list1 == list2: " + list1.equals(list2));
        System.out.println("list1 == list3: " + list1.equals(list3));
    }
}
