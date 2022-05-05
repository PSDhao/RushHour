package model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private Location location;
    private int direction;
    private int length;
    private int id;

    public Car(Location location, int direction, int length, int id) {
        this.location = location;
        this.direction = direction;
        this.length = length;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Location> getMoves(boolean[][] empty) {
        int row = empty.length;
        int col = empty[0].length;
        List<Location> moves = new ArrayList<>();
        int r = location.getX();
        int c = location.getY();
        int delta;

        switch (direction) {
            case 1:
                r = location.getX();
                c = location.getY();
                r--;
                delta = -1;
                while (r >= 0) {
                    if (empty[r][c]) {
                        Location oneLocation = new Location();
                        oneLocation.setX(delta);
                        oneLocation.setY(0);
                        moves.add(oneLocation);
                        r--;
                        delta--;
                    } else {
                        break;
                    }
                }

                r = location.getX();
                c = location.getY();
                r++;
                delta = 1;
                while (r+length-1 < row) {
                    if (empty[r+length-1][c]) {
                        Location oneLocation = new Location();
                        oneLocation.setX(delta);
                        oneLocation.setY(0);
                        moves.add(oneLocation);
                        r++;
                        delta++;
                    } else {
                        break;
                    }
                }
                break;

            case 2:
                r = location.getX();
                c = location.getY();
                c--;
                delta = -1;
                while (c-length+1 >= 0) {
                    if (empty[r][c-length+1]) {
                        Location oneLocation = new Location();
                        oneLocation.setX(0);
                        oneLocation.setY(delta);
                        moves.add(oneLocation);
                        c--;
                        delta--;
                    } else {
                        break;
                    }
                }

                r = location.getX();
                c = location.getY();
                c++;
                delta = 1;
                while (c < col) {
                    if (empty[r][c]) {
                        Location oneLocation = new Location();
                        oneLocation.setX(0);
                        oneLocation.setY(delta);
                        moves.add(oneLocation);
                        c++;
                        delta++;
                    } else {
                        break;
                    }
                }
                break;
        }

        return moves;
    }

    public void move(Location location, boolean[][] empty) {
        //System.out.println(getLocation() + " move " + location);
        updateLot(empty, true);
        this.location.setX(this.location.getX() + location.getX());
        this.location.setY(this.location.getY() + location.getY());
        updateLot(empty, false);
    }

    public void moveBack(Location location, boolean[][] empty) {
        updateLot(empty, true);
        //System.out.println(getLocation() + " moveback " + location);
        this.location.setX(this.location.getX() - location.getX());
        this.location.setY(this.location.getY() - location.getY());
        updateLot(empty, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !o.getClass().isAssignableFrom(Car.class)) return false;

        Car car = (Car) o;

        if (direction != car.direction) return false;
        if (length != car.length) return false;
        return location.equals(car.location);
    }

    @Override
    public int hashCode() {
        int result = location.hashCode();
        result = 31 * result + direction;
        result = 31 * result + length;
        return result;
    }

    public Car copy() {
        Location location = new Location();
        location.setY(this.location.getY());
        location.setX(this.location.getX());

        return new Car(location, getDirection(), getLength(), getId());
    }

    public boolean reachDestination(Location destination) {
        return getLocation().equals(destination);
    }

    public void updateLot(boolean[][] empty, boolean mark) {
        int x = getLocation().getX();
        int y = getLocation().getY();
        if (direction == 1) {
            for (int i = 0; i < length; i++) {
                empty[x+i][y] = mark;
            }
        } else {
            for (int i = 0; i < length; i++) {
                empty[x][y-i] = mark;
            }
        }
    }
}
