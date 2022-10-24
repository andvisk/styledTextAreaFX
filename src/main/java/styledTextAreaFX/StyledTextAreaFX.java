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

    public void moveCaret(double textX, double textY, double posX, double posY, double height, double baselineOffset) {

        //caret.getStackPane().getChildren().clear();
        caretOverlay.getChildren().clear();

        caret.getStackPane().setPrefWidth(posX + textX);
        caret.getStackPane().setPrefHeight(posY + baselineOffset);aa

        caret.getLine().setStartX(posX + textX);
        caret.getLine().setStartY(posY);
        caret.getLine().setEndX(posX + textX);
        caret.getLine().setEndY(posY + baselineOffset);

        caretOverlay.getChildren().add(caret.getStackPane());

        //caret.getStackPane().relocate(x, y*-1);
        //log.info("stack pane " + caret.getStackPane().getLayoutX() + " - " + caret.getStackPane().getLayoutY());

        /*caret.getStackPane().setLayoutX(15);
        caret.getStackPane().setLayoutY(15);*/

    }

    public List<Node> getOverlays() {
        return Arrays.asList(textFlowPane, caretOverlay);
    }

    public FlowPane getTextFlowPane() {
        return textFlowPane;
    }
}
