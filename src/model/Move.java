package model;

public class Move {
    private Car car;
    private Location move;

    public Move(Car car, Location move) {
        this.car = car;
        this.move = move;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Car");
        sb.append(car.getId());

        String direction;
        int unit = 0;
        if (move.getX() > 0) {
            direction = "\u2193";
            unit = move.getX();
        } else if (move.getX() < 0) {
            direction = "\u2191";
            unit = -move.getX();
        } else if (move.getY() > 0) {
            direction = "\u2192";
            unit = move.getY();
        } else {
            direction = "\u2190";
            unit = -move.getY();
        }

        sb.append(" ");
        sb.append(direction);
        sb.append(" ");
        sb.append(unit);
        return sb.toString();
    }
}
