package vntd.demo.gamehdh.framework.util;

import android.view.MotionEvent;
import android.view.View;

import vntd.demo.gamehdh.activities.MainActivity;
import vntd.demo.gamehdh.game.state.State;

public class InputHandler implements View.OnTouchListener {
    private State currentState;

    //GestureDetector gestureDetector;

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int scaledX = (int) ((event.getX() / v.getWidth()) * MainActivity.GAME_WIDTH);
        int scaledY = (int) ((event.getY() / v.getHeight()) * MainActivity.GAME_HEIGHT);

        return currentState.onTouch(event, scaledX, scaledY);
    }
}


