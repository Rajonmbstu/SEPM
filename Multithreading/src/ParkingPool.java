import java.util.LinkedList;
import java.util.Queue;

public class ParkingPool {
    private final Queue<RegistrarParking> parkingQueue = new LinkedList<>();
    private final int MAX_SIZE;

    public ParkingPool(int maxSize) {
        this.MAX_SIZE = maxSize;
    }

    public synchronized void addParkingRequest(RegistrarParking request) throws InterruptedException {
        while (parkingQueue.size() == MAX_SIZE) {
            wait();
        }
        parkingQueue.offer(request);
        System.out.println(request + " arrived at the parking gate.");
        notifyAll();
    }

    public synchronized RegistrarParking getParkingRequest() throws InterruptedException {
        while (parkingQueue.isEmpty()) {
            wait();
        }
        RegistrarParking request = parkingQueue.poll();
        notifyAll();
        return request;
    }
}
