package styledTextAreaFX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StyledTextAreaFX {

    private Logger log = LogManager.getLogger(this.getClass());

    private FlowPane textFlowPane;
    private Group caretOverlay;
    private Caret caret;

    public StyledTextAreaFX() {
        textFlowPane = new FlowPane();
        textFlowPane.rowValignmentProperty().set(VPos.BASELINE);
        caretOverlay = new Group();
        caret = new Caret();
    }

    public void addText(TextExtended text) {
        textFlowPane.getChildren().add(text);
    }

    public void moveCaret(double x, double y, double height) {
        caretOverlay.getChildren().clear();
        StackPane stackPane = new StackPane();
        stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        stackPane.setMouseTransparent(true);
        //stackPane.setPrefWidth(x);
        //stackPane.setPrefHeight(y+height);
        stackPane.setAlignment(Pos.TOP_RIGHT);
        caret.getLine().setStartX(x);
        caret.getLine().setStartY(y);
        caret.getLine().setEndX(x);
        caret.getLine().setEndY(y+height);
        stackPane.getChildren().add(caret.getLine());
        caretOverlay.getChildren().add(stackPane);

        stackPane.relocate(100, 100);
    }

    public List<Node> getOverlays() {
        return Arrays.asList(textFlowPane, caretOverlay);
    }

    public FlowPane getTextFlowPane() {
        return textFlowPane;
    }
}
