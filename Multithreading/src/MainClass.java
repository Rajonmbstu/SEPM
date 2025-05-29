public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        int maxQueueSize = 5;
        int numAgents = 3;
        int numCars = 20;

        ParkingPool pool = new ParkingPool(maxQueueSize);

        // Start ParkingAgents
        for (int i = 1; i <= numAgents; i++) {
            new ParkingAgent(pool, i).start();
        }

        // Simulate car arrivals
        for (int carId = 1; carId <= numCars; carId++) {
            RegistrarParking request = new RegistrarParking(carId);
            pool.addParkingRequest(request);
            Thread.sleep(200); // Cars arrive every 200ms
        }
    }
}
