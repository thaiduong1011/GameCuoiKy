package vntd.demo.gamehdh.game.state;

import android.view.MotionEvent;

import vntd.demo.gamehdh.Assets;
import vntd.demo.gamehdh.framework.util.Painter;

public class LoadPlayState extends State{
    @Override
    public void init() {
        load();
    }

    @Override
    public void update(float delta) {
        unload();
        setCurrentState(new PlayState());
    }

    @Override
    public void render(Painter g) {

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }

    @Override
    public void load() {
        Assets.player = Assets.loadBitmap("player.png", true);
        Assets.playerBullet = Assets.loadBitmap("playerbullet.png", true);
        Assets.enemy = Assets.loadBitmap("enemy.png", true);
        Assets.enemyBullet = Assets.loadBitmap("enemy_Bullet.png", true);

        Assets.pauseButton = Assets.loadBitmap("pauseButton.png", true);
        Assets.play_pauseButton = Assets.loadBitmap("playButton.png", true);

        //game over
        Assets.gameOverPanel = Assets.loadBitmap("GameOverPanel.png", true);
        Assets.replayButton = Assets.loadBitmap("replay.png", true);
        Assets.homeButton = Assets.loadBitmap("homeButton.png", true);
    }

    @Override
    public void unload() {
    }
}
