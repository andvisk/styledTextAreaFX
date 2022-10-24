package styledTextAreaFX;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Line;

public class Caret {

    long pulseTimeMs = 500;
    private Line line;
    private boolean visible = false;

    public Caret() {
        line = new Line(1, 1, 1, 50);
        new AnimationTimer() {

            long prev = -1;

            public void handle(long now) {

                if(prev < 0 || now - prev >= pulseTimeMs *1000000) {
                    visible = !visible;
                    line.setVisible(visible);
                    prev = now;
                }
            }
        }.start();
    }

    public Line getLine() {
        return line;
    }
}
