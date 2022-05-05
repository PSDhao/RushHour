import model.Car;
import model.Location;
import model.ParkingLot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        boolean[][] empty = new boolean[6][6];
        Set<Car> cars = new HashSet<>();
        Location location = new Location();
        location.setX(2);
        location.setY(1);
        Car car = new Car(location, 2, 2, 0);
        cars.add(car);

        Location dest = new Location();
        dest.setX(2);
        dest.setY(5);

        Location location1 = new Location();
        location1.setX(1);
        location1.setY(4);
        Car car1 = new Car(location1, 2, 2, 1);
        cars.add(car1);

        Location location2 = new Location();
        location2.setX(2);
        location2.setY(3);
        Car car2 = new Car(location2, 1, 2, 2);
        cars.add(car2);

        Location location3 = new Location();
        location3.setX(4);
        location3.setY(4);
        Car car3 = new Car(location3, 2, 2, 3);
        cars.add(car3);

        ParkingLot parkingLot = read();
        parkingLot.printLot();
        parkingLot.solve();
        parkingLot.printBest();
    }

    private static ParkingLot read() {
        try {
            List<String> allLines = Files.readAllLines(Paths.get("/Users/home/projects/hiddendragon/ParkingLot/src/game3"));
            Set<Car> cars = new HashSet<>();
            Car myCar = null;
            for (int i = 0; i < allLines.size(); i++) {
                String[] data = allLines.get(i).split(" ");
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                int direction = Integer.parseInt(data[2]);
                int length = Integer.parseInt(data[3]);

                Location location = new Location();
                location.setX(x);
                location.setY(y);
                Car car = new Car(location, direction, length, i);
                cars.add(car);

                if (i == 0) {
                    myCar = car;
                }
            }

            Location dest = new Location();
            dest.setX(2);
            dest.setY(5);

            return new ParkingLot(new boolean[6][6], cars, myCar, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
