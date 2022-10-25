package styledTextAreaFX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
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

    public StyledTextAreaFX(StackPane rootElement) {

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(300, 300);

        Group root = new Group();
        textFlowPane = new FlowPane();
        textFlowPane.rowValignmentProperty().set(VPos.BASELINE);

        caretOverlay = new Group();
        //caretOverlay.setAutoSizeChildren(false);

        caret = new Caret(caretOverlay);

        //rootElement.getChildren().addAll(textFlowPane, caretOverlay);
        root.getChildren().addAll(textFlowPane, caretOverlay);
        scrollPane.setContent(root);
        rootElement.getChildren().addAll(scrollPane);

    }

    public void addText(TextExtended text) {
        textFlowPane.getChildren().add(text);
    }

    public void moveCaret(double textX, double textY, double posX, double posY, double height, double baselineOffset) {

        //caret.getStackPane().getChildren().clear();
        //caretOverlay.getChildren().clear();

        caret.getStackPane().setPrefWidth(caret.getStackPaneWidth());
        caret.getStackPane().setPrefHeight(height);

        caret.getLine().setStartX(1);
        caret.getLine().setStartY(1);
        caret.getLine().setEndX(1);
        caret.getLine().setEndY(height);

        caret.getStackPane().relocate(posX + textX - caret.getStackPaneWidth(), posY);

        log.info("posX " + (posX + textX) + " posY " + posY);
        //log.info("group " + caretOverlay.getBoundsInParent().toString());


        //caretOverlay.getChildren().add(caret.getStackPane());

        //caret.getStackPane().relocate(x, y*-1);
        //log.info("stack pane " + caret.getStackPane().getLayoutX() + " - " + caret.getStackPane().getLayoutY());

        /*caret.getStackPane().setLayoutX(15);
        caret.getStackPane().setLayoutY(15);*/

    }

    public FlowPane getTextFlowPane() {
        return textFlowPane;
    }
}
