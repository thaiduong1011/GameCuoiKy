package vntd.demo.gamehdh.game.state;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.List;

import vntd.demo.gamehdh.Assets;
import vntd.demo.gamehdh.Score;
import vntd.demo.gamehdh.activities.MainActivity;
import vntd.demo.gamehdh.framework.util.Painter;

public class ScoreState extends State{
    public static List<Score> scoreArray;
    Rect rect;

    @Override
    public void init() {
        int x = 110;
        int y = 250;
        for (Score score: scoreArray){
            score.setPoint(new Point(x, y));
            y += 80;
        }

        rect = new Rect(50, 150, MainActivity.GAME_WIDTH - 70, MainActivity.GAME_HEIGHT - 120);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.menuBackground, 0, 0, MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
        g.setFont(60);
        if (scoreArray != null){
            g.drawImage(Assets.bgScoreBoard, 50, 150, MainActivity.GAME_WIDTH - 120, MainActivity.GAME_HEIGHT - 270);
            g.drawString("SCORE BOARD", 50, 100);
            g.setFont(30);
            g.setColor(Color.WHITE);

            for (int i = 0; i < scoreArray.size(); i++){
                if (i == 5)
                    break;

                g.drawString(scoreArray.get(i).getUserName(), scoreArray.get(i).getPoint().x, scoreArray.get(i).getPoint().y);
                g.drawString(scoreArray.get(i).getScore() + "", scoreArray.get(i).getPoint().x + 200, scoreArray.get(i).getPoint().y);
            }
        }else {
            g.drawString("LOADING", 130, 130);
        }
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (!rect.contains(scaledX, scaledY)){
            setCurrentState(new MenuState());
        }
        return false;
    }
}
