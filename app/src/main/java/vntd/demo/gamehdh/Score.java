package vntd.demo.gamehdh;

import android.graphics.Point;

import java.util.Comparator;

public class Score {
    private int userId;
    private String userName;
    private int score;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    private Point point;

    public Score(int userId, String userName, int score) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
        point = new Point();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", score=" + score +
                '}';
    }

    public static Comparator<Score> comparator = new Comparator<Score>() {

        public int compare(Score s1, Score s2) {

            int no1 = s1.getScore();
            int no2 = s2.getScore();

            /*For descending order*/
            return no2-no1;
        }};
}
