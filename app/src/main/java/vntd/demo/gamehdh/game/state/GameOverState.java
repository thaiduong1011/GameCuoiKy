package vntd.demo.gamehdh.game.state;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import vntd.demo.gamehdh.Assets;
import vntd.demo.gamehdh.async.AsyncInsertScore;
import vntd.demo.gamehdh.activities.MainActivity;
import vntd.demo.gamehdh.framework.util.Painter;

public class GameOverState extends State{
    private int playerScore;

    Rect btnReplay;
    Rect btnHome;
    int btnWidth;
    int btnHeight;

    public GameOverState(int playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public void init() {
        btnWidth = 90;
        btnHeight = 90;
        btnHome = new Rect(140, 580, 140 + btnWidth, 580 + btnHeight);
        btnReplay = new Rect(260, 580, 260 + btnWidth, 580 + btnHeight);

        new AsyncInsertScore().execute(MainActivity.userId +"",playerScore +"");
    }

    @Override
    public void update(float delta) {
        if (playerScore > MainActivity.getHighScore()){
            MainActivity.setHighScore(playerScore);
        }
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.gameOverPanel, 100, 280);

        g.setFont(MainActivity.font, 60);
        g.setColor(Color.BLACK);
        g.drawString("" + playerScore, 250, 360); // diem player
        g.setColor(Color.YELLOW);
        g.drawString("" + MainActivity.getHighScore(), 250, 480);// diem cao nhat

        //Button
        g.drawImage(Assets.homeButton, btnHome.left, btnHome.top, btnWidth, btnHeight);
        g.drawImage(Assets.replayButton, btnReplay.left, btnReplay.top,  btnWidth, btnHeight);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (btnReplay.contains(scaledX, scaledY)){
            setCurrentState(new PlayState());
        }else if (btnHome.contains(scaledX,scaledY)){
            setCurrentState(new MenuState());
        }

        return false;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
