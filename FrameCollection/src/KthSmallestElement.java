// Finds the kth smallest element from an ArrayList
import java.util.*;

public class KthSmallestElement {
    public static int findKthSmallest(List<Integer> list, int k) {
        Collections.sort(list);
        return list.get(k - 1); // index starts at 0
    }

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(7, 2, 5, 3, 9, 1));
        int k = 3;
        System.out.println(k + "rd smallest: " + findKthSmallest(numbers, k));
    }
}
