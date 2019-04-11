public class Point {
    private int x;
    private int y;
    public Point(int x, int y) {
        this.x  = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Point)) {
            return false;
        }
        Point o = (Point) other;
        if (this.x == o.getX() && this.y == o.getY()) {
            System.out.println("TEst");

            return true;
        }
        return false;
    }
}