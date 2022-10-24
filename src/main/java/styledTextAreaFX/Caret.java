package styledTextAreaFX;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Caret {

    long pulseTimeMs = 500;
    private Line line;
    private boolean visible = false;
    private StackPane stackPane;

    public Caret() {

        line = new Line(1, 1, 1, 50);

        stackPane = new StackPane();
        //stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        stackPane.setMouseTransparent(true);
        stackPane.setAlignment(Pos.BOTTOM_RIGHT);
        stackPane.getChildren().add(line);


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

    public StackPane getStackPane(){
        return stackPane;
    }
}
