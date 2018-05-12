package vntd.demo.gamehdh.game.state;

import android.view.MotionEvent;

import vntd.demo.gamehdh.activities.MainActivity;
import vntd.demo.gamehdh.framework.util.Painter;

public abstract class State {
    public void setCurrentState(State newState) {
        MainActivity.sGame.setCurrentState(newState);
    }

    public abstract void init();

    public abstract void update(float delta);

    public abstract void render(Painter g);

    public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);

    public void onPause(){}

    public void onResume() {}

    public void load() {}

    public void unload() {}
}
