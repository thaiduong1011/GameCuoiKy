package vntd.demo.gamehdh;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Rect playerRect;
    private List<Bullet> bullets;
    private Point playerPosition;
    private int playerWidth;
    private int playerHeight;

    private boolean isActive;

    int SHOOT = Assets.loadSound("shoot.wav");

    public Player(int x, int y, int width, int height) {
        playerPosition = new Point(x, y);
        playerWidth = width;
        playerHeight = height;
        playerRect = new Rect(x, y, x + width, y + height);
        bullets = new ArrayList<>();

        isActive = true;
    }

    public Player(Rect playerRect, List<Bullet> bullets, Point playerPosition, int playerWidth, int playerHeight) {
        this.playerRect = playerRect;
        this.bullets = bullets;
        this.playerPosition = playerPosition;
        this.playerWidth = playerWidth;
        this.playerHeight = playerHeight;
    }

    public boolean update(float delta){

        return true;
    }

    public Rect getPlayerRect() {
        return playerRect;
    }

    public void setPlayerRect(Rect playerRect) {
        this.playerRect = playerRect;
    }

    public void setPlayerRect(int x) {
        this.playerRect.left = x;
        this.playerRect.right = x + this.playerWidth;
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

    public Point getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Point playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setPlayerPosition(int x, int y) {
        this.playerPosition.x = x;
        this.playerPosition.y = y;
    }

    public void setPlayerPosition(int x) {
        this.playerPosition.x = x;
    }

    public int getPlayerWidth() {
        return playerWidth;
    }

    public void setPlayerWidth(int playerWidth) {
        this.playerWidth = playerWidth;
    }

    public int getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(int playerHeight) {
        this.playerHeight = playerHeight;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int update(int playerMaxTime) {
        //player
        if (playerMaxTime > 0) {
            playerMaxTime--;
        }
        else {
            playerMaxTime = 4;
            this.addBullet(new Bullet(this.getPlayerPosition().x + 40, this.getPlayerPosition().y));
            Assets.playSound(SHOOT);

        }

        for (int i = 0; i < this.getBullets().size(); i++) {
            this.getBullets().get(i).getPoint().y -= 10;

            if (this.getBullets().get(i).getPoint().y <= -5)
                this.getBullets().remove(i);
        }
        return playerMaxTime;
    }


}
