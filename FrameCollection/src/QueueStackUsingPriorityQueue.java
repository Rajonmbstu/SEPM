// Stack and Queue using PriorityQueue with custom comparator
import java.util.*;

class CustomElement {
    int value;
    int order;

    CustomElement(int value, int order) {
        this.value = value;
        this.order = order;
    }
}

public class QueueStackUsingPriorityQueue {
    static int counter = 0;

    public static void main(String[] args) {
        // Queue - FIFO
        PriorityQueue<CustomElement> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.order));

        // Stack - LIFO
        PriorityQueue<CustomElement> stack = new PriorityQueue<>((a, b) -> b.order - a.order);

        for (int i : new int[]{10, 20, 30}) {
            queue.add(new CustomElement(i, counter));
            stack.add(new CustomElement(i, counter));
            counter++;
        }

        System.out.println("Queue order:");
        while (!queue.isEmpty()) System.out.println(queue.poll().value);

        System.out.println("Stack order:");
        while (!stack.isEmpty()) System.out.println(stack.poll().value);
    }
}
