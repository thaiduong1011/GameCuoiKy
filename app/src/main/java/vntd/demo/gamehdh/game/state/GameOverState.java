package vntd.demo.gamehdh.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import vntd.demo.gamehdh.Assets;
import vntd.demo.gamehdh.MainActivity;
import vntd.demo.gamehdh.framework.util.Painter;

public class GameOverState extends State{
    private int playerScore;

    public GameOverState(int playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {
        if (playerScore > MainActivity.getHighScore()){
            MainActivity.setHighScore(playerScore);
        }
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.gameOverPanel, 100, 300);

        g.setFont(MainActivity.font, 60);
        g.setColor(Color.BLACK);
        g.drawString("" + playerScore, 250, 380); // diem player
        g.setColor(Color.YELLOW);
        g.drawString("" + MainActivity.getHighScore(), 250, 500);// diem cao nhat
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
