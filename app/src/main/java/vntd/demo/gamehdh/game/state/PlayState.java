package vntd.demo.gamehdh.game.state;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vntd.demo.gamehdh.Assets;
import vntd.demo.gamehdh.Enemy;
import vntd.demo.gamehdh.MainActivity;
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

            //enemy
            for (int i = 0; i < enemyList.size(); i++) {
                if ((enemyList.get(i).getEnemyPosition().y > MainActivity.GAME_HEIGHT + 15)) {
                    enemyList.remove(i);
                }

                //bullet của enemy
                timeToCreateBullet = enemyList.get(i).update(timeToCreateBullet);
            }

            checkStrigger();
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

        //button play_pause
        if (isPause == false)
            g.drawImage(Assets.pauseButton, MainActivity.GAME_WIDTH - 80, 30, 60, 60);
        else
            g.drawImage(Assets.play_pauseButton, MainActivity.GAME_WIDTH - 80, 30, 60, 60);


        //player
        g.setFont(MainActivity.font, 30);
        g.drawString(scrore + "", 20, 30); // set score
        for (int i = 0; i < player.getBullets().size(); i++) {
            g.drawImage(Assets.playerBullet, player.getBullets().get(i).getPoint().x, player.getBullets().get(i).getPoint().y);
        }

        g.drawImage(Assets.player, player.getPlayerPosition().x, player.getPlayerPosition().y, player.getPlayerWidth(), player.getPlayerHeight());
        //g.drawRect(player.getPlayerRect());


        //enemy
        for (int i = 0; i < enemyList.size(); i++) {
            //enemy bullet
            for (int j = 0; j < enemyList.get(i).getBullets().size(); j++) {
                g.drawImage(Assets.enemyBullet,
                        enemyList.get(i).getBullets().get(j).getPoint().x,
                        enemyList.get(i).getBullets().get(j).getPoint().y);
            }

            //g.drawRect(enemyList.get(i).getEnemyRect());
            if (enemyList.get(i).isActive() == true){
                g.drawImage(Assets.enemy, enemyList.get(i).getEnemyPosition().x,
                        enemyList.get(i).getEnemyPosition().y,
                        enemyList.get(i).getEnemyWidth(),
                        enemyList.get(i).getEnemyHeight());
            }

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
        for (int i = 0; i < enemyList.size(); i++) {
            //enemy chạm player
            if (player.getPlayerRect().intersect(enemyList.get(i).getEnemyRect())) {
                player.setActive(false);
                return;
            }
            //enemy bắn trứng player
            for (int j = 0; j < enemyList.get(i).getBullets().size(); j++) {
                if (player.getPlayerRect().contains(enemyList.get(i).getBullets().get(j).getPoint().x,
                        enemyList.get(i).getBullets().get(j).getPoint().y)) {
                    player.setActive(false);
                    return;
                }
            }

            // player bắn trúng enemy
            for (int j = 0; j < player.getBullets().size(); j++) {
                //check active enemy
                if (enemyList.get(i).getEnemyPosition().x > 5 && enemyList.get(i).getEnemyRect().contains(player.getBullets().get(j).getPoint().x,
                        player.getBullets().get(j).getPoint().y)) {
                    enemyList.get(i).setActive(false);
                    Assets.playSound(DIED_ENEMY);
                    enemyList.remove(i);
                    scrore++;
                    return;
                }
            }
        }

    }


}
