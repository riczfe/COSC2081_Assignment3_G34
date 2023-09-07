public class PortManagerSystem {
    public static void main(String[] args) {
        // Create instances of ships, trucks, and containers
        Ship ship1 = new Ship("SHIP001", 100, 50, "SHIP001", 1000);
        Truck truck1 = new Truck("TRUCK001", 20, 10, "TRUCK001", 500);
        Container container1 = new Container("CONT001", 500);
        Container container2 = new Container("CONT002", 700);

        // Load containers onto the ship and truck
        ship1.loadContainer(container1);
        ship1.loadContainer(container2);
        truck1.loadContainer(container1);

        // Unload containers from the ship and truck
        ship1.unloadContainer(container1);
        truck1.unloadContainer(container1);

        // Determine if a vehicle can move to a port with its current load
        Port port1 = new Port("PORT001", "Port 1", 0, 0, 1000, true);
        Admin admin = new Admin("admin", "password");
        boolean canMoveToPort = admin.canMoveToPort(ship1, port1);
        System.out.println("Can ship move to port? " + canMoveToPort);

        // Refuel a vehicle
        admin.refuelVehicle(truck1);
        System.out.println("Truck refueled. New weight: " + truck1.getWeight());

        // Perform statistics operations on a port
        admin.performStatisticsOperations(port1);
    }
}