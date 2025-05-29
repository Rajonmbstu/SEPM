public class ParkingAgent extends Thread {
    private final ParkingPool pool;
    private final int agentId;

    public ParkingAgent(ParkingPool pool, int agentId) {
        this.pool = pool;
        this.agentId = agentId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                RegistrarParking request = pool.getParkingRequest();
                System.out.println("Agent#" + agentId + " parked " + request);
                Thread.sleep(500); // Simulating time taken to park
            }
        } catch (InterruptedException e) {
            System.out.println("Agent#" + agentId + " stopped.");
        }
    }
}
