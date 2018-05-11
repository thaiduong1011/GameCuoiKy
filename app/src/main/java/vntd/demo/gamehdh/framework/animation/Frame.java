package vntd.demo.gamehdh.framework.animation;

import android.graphics.Bitmap;

public class Frame {
    private Bitmap image; //the picture for this frame
    private double duration; //how long this frame is displayed

    public Frame(Bitmap image, double duration) {
        this.image = image;
        this.duration = duration;
    }

    public double getDuration() {
        return duration;
    }

    public Bitmap getImage() {
        return image;
    }
}
