package vntd.demo.gamehdh;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vntd.demo.gamehdh.activities.MainActivity;

public class Enemy {
    private Rect enemyRect;
    private List<Bullet> bullets;
    private Point enemyPosition;
    private int enemyWidth;
    private int enemyHeight;

    private boolean isActive;

    public Enemy(int x, int y, int width, int height) {
        isActive = true;
        enemyPosition = new Point(x, y);
        enemyWidth = width;
        enemyHeight = height;
        enemyRect = new Rect(x, y, x + width, y + height);
        bullets = new ArrayList<>();
    }

    public Enemy(Rect playerRect, List<Bullet> bullets, Point playerPosition, int enemyWidth, int enemyHeight) {
        this.enemyRect = playerRect;
        this.bullets = bullets;
        this.enemyPosition = playerPosition;
        this.enemyWidth = enemyWidth;
        this.enemyHeight = enemyHeight;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean update(float delta) {

        return true;
    }

    public int update(int timeToCreateBullet) {
        Random random = new Random();

        if (isActive == true) {
            this.getEnemyPosition().y += 8;
            this.setEnemyRect(this.getEnemyPosition().y);
        }

        //bullet của enemy
        for (Bullet bullet: bullets) {
            bullet.getPoint().y += 15;

            if (bullet.getPoint().y >= MainActivity.GAME_HEIGHT)
                bullets.remove(bullet);
        }

        if(isActive == true) {
            if (timeToCreateBullet > 0) {
                timeToCreateBullet--;
            } else {
                timeToCreateBullet = random.nextInt(100) + 50;
                this.getBullets().add(new Bullet(this.getEnemyPosition().x, this.getEnemyPosition().y));

            }
        }
        return timeToCreateBullet;
    }

    public Rect getEnemyRect() {
        return enemyRect;
    }

    public void setEnemyRect(Rect enemyRect) {
        this.enemyRect = enemyRect;
    }

    public void setEnemyRect(int x, int y) {
        this.enemyRect.left = x;
        this.enemyRect.right = x + this.enemyWidth;

        this.enemyRect.top = y;
        this.enemyRect.bottom = y + this.enemyHeight;
    }

    public void setEnemyRect(int y) {
        this.enemyRect.top = y;
        this.enemyRect.bottom = y + this.enemyHeight;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void addBullet(Bullet bullet) {
        this.bullets.add(bullet);
    }

    public Point getEnemyPosition() {
        return enemyPosition;
    }

    public void setEnemyPosition(Point enemyPosition) {
        this.enemyPosition = enemyPosition;
    }

    public void setEnemyPosition(int x, int y) {
        this.enemyPosition.x = x;
        this.enemyPosition.y = y;
    }

    public void setEnemyPosition(int x) {
        this.enemyPosition.x = x;
    }

    public int getEnemyWidth() {
        return enemyWidth;
    }

    public void setEnemyWidth(int playerWidth) {
        this.enemyWidth = playerWidth;
    }

    public int getEnemyHeight() {
        return enemyHeight;
    }

    public void setEnemyHeight(int playerHeight) {
        this.enemyHeight = playerHeight;
    }
}
