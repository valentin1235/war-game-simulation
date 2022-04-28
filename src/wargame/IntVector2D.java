package wargame;

public class IntVector2D {
    private int x;
    private int y;

    public IntVector2D(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        if (x < 0) {
            this.x = 0;
        } else if (x > 15) {
            this.x = 15;
        } else {
            this.x = x;
        }
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        if (y < 0) {
            this.y = 0;
        } else if (y > 7) {
            this.y = 7;
        } else {
            this.y = y;
        }
    }

    public boolean equals(IntVector2D position) {
        return this.x == position.x && this.y == position.y;
    }
}
