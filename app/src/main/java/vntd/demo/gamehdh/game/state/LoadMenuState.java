package vntd.demo.gamehdh.game.state;

import android.view.MotionEvent;

import vntd.demo.gamehdh.Assets;
import vntd.demo.gamehdh.framework.util.Painter;

public class LoadMenuState extends State{
    @Override
    public void init() {
        load();
    }

    @Override
    public void update(float delta) {
        setCurrentState(new MenuState());
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
        Assets.menuBackground = Assets.loadBitmap("menuBackground.png", true);
        Assets.playButton = Assets.loadBitmap("startGameButton.png", true);
        Assets.highScoreButton = Assets.loadBitmap("highScoreButton.png", true);
        Assets.quitButton = Assets.loadBitmap("quitButton.png", true);

        Assets.loginButton = Assets.loadBitmap("logintButton.png", true);
        Assets.signupButton = Assets.loadBitmap("signuptButton.png", true);

        //scoreboard
        Assets.bgScoreBoard = Assets.loadBitmap("scoreboard.png", true);
    }
}
