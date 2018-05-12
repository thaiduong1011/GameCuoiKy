package vntd.demo.gamehdh.game.state;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vntd.demo.gamehdh.Assets;
import vntd.demo.gamehdh.Bullet;
import vntd.demo.gamehdh.Enemy;
import vntd.demo.gamehdh.activities.MainActivity;
import vntd.demo.gamehdh.Player;
import vntd.demo.gamehdh.framework.util.Painter;

public class PlayState extends State {
    public static Player player;
    int playerMaxTime;

    public List<Enemy> enemyList;
    int enemyMaxTime;
    int timeToCreateBullet;

    int scrore;

    int GAMEOVER = Assets.loadSound("gameoverSound.wav");
    int DIED_ENEMY = Assets.loadSound("enemyDieSound.wav");

    boolean temp = false;
    boolean isPause = false;

    //button pause
    Rect btnPause;

    @Override
    public void init() {
        //init player
        player = new Player(MainActivity.GAME_WIDTH / 2, MainActivity.GAME_HEIGHT - 100, 80, 80);
        scrore = 0;

        playerMaxTime = 10; // set time de sinh ra bullet

        enemyList = new ArrayList<>();
        Enemy enemy = new Enemy(MainActivity.GAME_WIDTH / 2, -15, 80, 80);
        enemyList.add(enemy);

        enemyMaxTime = 50;
        timeToCreateBullet = 120;

        btnPause = new Rect();
    }

    @Override
    public void update(float delta) {
        btnPause.set(MainActivity.GAME_WIDTH - 80, 30, MainActivity.GAME_WIDTH - 20, 90);

        if (isPause == true){
            return;
        }

        if (player.isActive() == true) {
            //player
            playerMaxTime = player.update(playerMaxTime);

            //enemy
            Random random = new Random();
            if (enemyMaxTime > 0) { // thoi gian tao ra 1 enemy
                enemyMaxTime--;
            } else {
                enemyMaxTime = random.nextInt(100) + 50;
                int x = random.nextInt(MainActivity.GAME_WIDTH - 20);
                Enemy enemy = new Enemy(x, -10, 80, 80);
                enemyList.add(enemy);
            }
            // xử lý va chạm
            checkStrigger();

            //xử lý enemy
            int lastBullet;
            for (Enemy enemy: enemyList){
                timeToCreateBullet = enemy.update(timeToCreateBullet);
                if (enemy.isActive() == false){
                    lastBullet = enemy.getBullets().size() - 1;
                    if (lastBullet > MainActivity.GAME_HEIGHT){
                        enemyList.remove(enemy);
                    }
                }
            }
        }
        else if (temp == false && player.isActive() == false){
            Assets.playSound(GAMEOVER);
            temp = true;

            MainActivity.sGame.setCurrentState(new GameOverState(scrore));
        }

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.menuBackground, 0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);

        //player
        g.setFont(MainActivity.font, 40);
        g.drawString(scrore + "", 30, 30); // set score
        for (Bullet playerBullet :player.getBullets()) {
            g.drawImage(Assets.playerBullet, playerBullet.getPoint().x, playerBullet.getPoint().y);
        }

        g.drawImage(Assets.player, player.getPlayerPosition().x, player.getPlayerPosition().y, player.getPlayerWidth(), player.getPlayerHeight());
        //g.drawRect(player.getPlayerRect());

        //enemy

        for (Enemy enemy : enemyList){
            if (enemy.isActive()){
                g.drawImage(Assets.enemy, enemy.getEnemyPosition().x, enemy.getEnemyPosition().y,
                        enemy.getEnemyWidth(), enemy.getEnemyHeight());
            }

            for (Bullet bullet: enemy.getBullets()){
                g.drawImage(Assets.enemyBullet, bullet.getPoint().x, bullet.getPoint().y);
            }
        }

        //button play_pause
        if (isPause == false) {
            g.drawImage(Assets.pauseButton, MainActivity.GAME_WIDTH - 80, 30, 60, 60);
        }
        else {
            g.drawImage(Assets.play_pauseButton, MainActivity.GAME_WIDTH - 80, 30, 60, 60);
            g.setFont(MainActivity.font, 80);
            g.drawString("pause", 130, 300);
        }

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (btnPause.contains(scaledX, scaledY)){
            if (isPause == true) {
                isPause = false;
            }else
                isPause = true;
            return false;
        }else if (isPause == false) {
            player.setPlayerPosition(scaledX);
            player.setPlayerRect(scaledX);
            return true; //This needs to be set to true if there is touch input
        }
        return false;
    }

    private void checkStrigger() {
        for (Enemy enemy: enemyList) {
            //enemy chạm player
            if (player.getPlayerRect().intersect(enemy.getEnemyRect())) {
                player.setActive(false);
                return;
            }
            //enemy bắn trứng player
            for (Bullet bullet: enemy.getBullets()) {
                if (player.getPlayerRect().contains(bullet.getPoint().x,
                        bullet.getPoint().y)) {
                    player.setActive(false);
                    return;
                }
            }

            //enemy trúng đạn của player
            for (Bullet bullet: player.getBullets()){
                if (enemy.isActive() == true && enemy.getEnemyRect().contains(bullet.getPoint().x, bullet.getPoint().y)){
                    enemy.setActive(false);
                    Assets.playSound(GAMEOVER);
                    scrore++;
                }
            }
        }

    }


}
