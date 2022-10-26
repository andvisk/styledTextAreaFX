package styledTextAreaFX;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Caret {

    long pulseTimeMs = 500;
    private Line line;
    private boolean visible = true;
    private StackPane stackPane;
    private int stackPaneWidth = 1;
    private AnimationTimer animationTimer;
    private TextExtended iAmOnText;

    public Caret(Group caretOverlay) {

        line = new Line(1, 1, 1, 50);

        stackPane = new StackPane();
        //stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(50, 100, 150), CornerRadii.EMPTY, Insets.EMPTY)));
        stackPane.setMouseTransparent(true);
        stackPane.setAlignment(Pos.BOTTOM_LEFT);
        stackPane.getChildren().add(line);

        caretOverlay.getChildren().add(stackPane);

        line.setVisible(false);

        animationTimer = new AnimationTimer() {
            long prev = -1;

            public void handle(long now) {
                if (prev < 0 || now - prev >= pulseTimeMs * 1000000) {
                    visible = !visible;
                    line.setVisible(visible);
                    prev = now;
                }
            }
        };

    }

    public void restartPulse() {
        line.setVisible(true);
        animationTimer.stop();
        animationTimer.start();
    }

    public void moveCaret(double textX, double textY, TextExtended text) {

        FlowPane paragraphFlowPane = (FlowPane) text.getParent(); //FlowPane represents paragraph
        FlowPane textAreaFlowPane = (FlowPane) paragraphFlowPane.getParent(); //FlowPane represents paragraphs flow

        Bounds textBounds = text.getBoundsInParent();
        Bounds paragraphBounds = paragraphFlowPane.getBoundsInParent();
        Bounds textAreaBounds = textAreaFlowPane.getBoundsInParent();

        stackPane.setPrefWidth(getStackPaneWidth());
        stackPane.setPrefHeight(textBounds.getHeight());

        line.setStartX(1);
        line.setStartY(1);
        line.setEndX(1);
        line.setEndY(textBounds.getHeight());

        double posX = textBounds.getMinX() + paragraphBounds.getMinX() + textAreaBounds.getMinX();
        double posY = textBounds.getMinY() + paragraphBounds.getMinY() + textAreaBounds.getMinY();

        PathIndex nearestPathIndex = new PathIndex(text, textX);
        double relocateX = posX + nearestPathIndex.getNearestPathX();

        if (relocateX < 0) relocateX = 0;
        double relocateY = posY;
        if (relocateY < 0) relocateY = 0;

        stackPane.relocate(relocateX, relocateY);

        restartPulse();

        iAmOnText = text;

    }

    public TextExtended getiAmOnText() {
        return iAmOnText;
    }

    public Line getLine() {
        return line;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public int getStackPaneWidth() {
        return stackPaneWidth;
    }
}
