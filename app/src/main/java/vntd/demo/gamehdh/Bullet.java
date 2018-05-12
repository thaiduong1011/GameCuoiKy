package vntd.demo.gamehdh;

import android.graphics.Point;

public class Bullet {
    private Point point;

    private boolean isActive;

    public Bullet() {

        isActive = false;

        point = new Point();
    }

    public Bullet(int x, int y) {
        point = new Point(x + 2, y);
    }

    public boolean shoot(int startX, int startY, float direction) {
        if (!isActive) {
            point.x = startX;
            point.y = startY;

            isActive = true;
            return true;
        }

        // Bullet already active
        return false;
    }

    public Point getPoint(){
        return  point;
    }

    public boolean getStatus(){
        return isActive;
    }

    public void setInactive(){
        isActive = false;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
    public void setPoint(int x, int y) {
        this.point.x = x + 2;
        this.point.y = y;
    }
}
