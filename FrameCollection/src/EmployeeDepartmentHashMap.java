// Maps employee IDs to their departments using HashMap
import java.util.*;

public class EmployeeDepartmentHashMap {
    public static void main(String[] args) {
        HashMap<Integer, String> employeeMap = new HashMap<>();

        employeeMap.put(1001, "HR");
        employeeMap.put(1002, "IT");
        employeeMap.put(1003, "Finance");

        for (Map.Entry<Integer, String> entry : employeeMap.entrySet()) {
            System.out.println("Employee ID: " + entry.getKey() + ", Department: " + entry.getValue());
        }
    }
}
