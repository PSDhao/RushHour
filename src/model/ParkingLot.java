package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParkingLot {
    private boolean[][] empty;
    private Set<Car> cars;
    private Car myCar;
    private Location destination;
    private Map<ParkingLot, Integer> state = new HashMap<>();
    private boolean solved;
    private ArrayList<Move> solution = new ArrayList<>();
    private ArrayList<Move> best = new ArrayList<>();
    private int currentMoves = 0;
    int found = 0;

    public ParkingLot(boolean[][] empty, Set<Car> cars, Car myCar, Location destination) {
        this.empty = empty;
        this.cars = cars;
        this.myCar = myCar;
        this.destination = destination;
        sync();
    }

    public ParkingLot() {

    }

    public boolean[][] getEmpty() {
        return empty;
    }

    public void setEmpty(boolean[][] empty) {
        this.empty = empty;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public Car getMyCar() {
        return myCar;
    }

    public void setMyCar(Car myCar) {
        this.myCar = myCar;
    }

    public void solve() {
        for (ParkingLot lot : state.keySet()) {
            int value = state.get(lot);
            if (lot.equals(this) && value <= currentMoves) {
                return;
            }
        }

        if (myCar.reachDestination(destination)) {
            //System.out.println("found solution!");
            found++;
            //if (found > 200) {
              //  this.printBest();
                //System.exit(0);
            //}
            solved = true;
            state.put(this.copyState(), currentMoves);
            if (best.isEmpty() || solution.size() < best.size()) {
                best.clear();
                best.addAll(solution);
            }

            return;
        }

        state.put(this.copyState(), currentMoves);

        for (Car car : cars) {
            List<Location> moves = car.getMoves(empty);
            for (Location move : moves) {
                car.move(move, empty);
                currentMoves++;
                solution.add(new Move(car, move));
                //sync();
                solve();
                car.moveBack(move, empty);
                currentMoves--;
                solution.remove(solution.size() - 1);
                //sync();
            }
        }
    }

    private void sync() {
        for (int i = 0; i < empty.length; i++) {
            for (int j = 0; j < empty[0].length; j++) {
                empty[i][j] = true;
            }
        }

        for (Car car : cars) {
            int r = car.getLocation().getX();
            int c = car.getLocation().getY();

            if (car.getDirection() == 1) {
                for (int i = 0; i < car.getLength(); i++) {
                    empty[r+i][c] = false;
                }
            } else if (car.getDirection() == 2) {
                for (int i = 0; i < car.getLength(); i++) {
                    empty[r][c-i] = false;
                }
            }
        }
    }

    public void printBest() {
        for (Move move : best) {
            System.out.println(move);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingLot that = (ParkingLot) o;

        if (!cars.equals(that.cars)) return false;
        return myCar.equals(that.myCar);
    }

    @Override
    public int hashCode() {
        int result = cars.hashCode();
        result = 31 * result + myCar.hashCode();
        return result;
    }

    private ParkingLot copyState() {
        ParkingLot copy = new ParkingLot();

        Set<Car> cars = new HashSet<>();
        for (Car car : this.cars) {
            cars.add(car.copy());
        }

        Car mycar = getMyCar().copy();
        copy.setCars(cars);
        copy.setMyCar(mycar);

        return copy;
    }

    public void printLot() {
        for (int i = 0; i < empty.length; i++) {
            for (int j = 0; j < empty[0].length; j++) {
                System.out.print(empty[i][j] + "\t");
            }

            System.out.println();
        }
    }
}
