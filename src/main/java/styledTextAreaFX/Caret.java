package styledTextAreaFX;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Line;

public class Caret {

    long pulseTimeMs = 300;
    private Line line;
    private boolean visible = false;

    public Caret() {
        line = new Line(1, 1, 50, 50);
        new AnimationTimer() {

            long prev = -1;

            public void handle(long now) {

                long prevPlusPulse = prev + pulseTimeMs *1000000;
                long sub = now - prevPlusPulse;

                if(prev < 0 || prev + pulseTimeMs *1000000 >= now) {
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
