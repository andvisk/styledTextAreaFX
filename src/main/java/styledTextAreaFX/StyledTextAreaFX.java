package styledTextAreaFX;

import javafx.geometry.Bounds;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StyledTextAreaFX {

    private Logger log = LogManager.getLogger(this.getClass());

    private ScrollPane scrollPane;
    private Group caretOverlay;
    private FlowPane textOverlay;
    private Caret caret;

    private List<Paragraph> paragraphList;

    public StyledTextAreaFX(StackPane rootElement) {

        scrollPane = new ScrollPane();

        Group root = new Group();

        caretOverlay = new Group();
        textOverlay = new FlowPane();

        caret = new Caret(caretOverlay);

        root.getChildren().addAll(textOverlay, caretOverlay);
        scrollPane.setContent(root);
        rootElement.getChildren().addAll(scrollPane);

        paragraphList = new ArrayList<>();

    }

    public void addParagraphs(Paragraph... paragraphs) {
        paragraphList.addAll(Arrays.asList(paragraphs));
        Arrays.asList(paragraphs).stream().forEach(p -> p.addMe(scrollPane, textOverlay));
    }

    public void moveCaret(double textX, double textY, TextExtended text) {

        FlowPane paragraphFlowPane = (FlowPane) text.getParent(); //FlowPane represents paragraph
        FlowPane textAreaFlowPane = (FlowPane) paragraphFlowPane.getParent(); //FlowPane represents paragraphs flow

        Bounds textBounds = text.getBoundsInParent();
        Bounds paragraphBounds = paragraphFlowPane.getBoundsInParent();
        Bounds textAreaBounds = textAreaFlowPane.getBoundsInParent();

        caret.getStackPane().setPrefWidth(caret.getStackPaneWidth());
        caret.getStackPane().setPrefHeight(textBounds.getHeight());

        caret.getLine().setStartX(1);
        caret.getLine().setStartY(1);
        caret.getLine().setEndX(1);
        caret.getLine().setEndY(textBounds.getHeight());

        double posX = textBounds.getMinX() + paragraphBounds.getMinX() + textAreaBounds.getMinX();
        double posY = textBounds.getMinY() + paragraphBounds.getMinY() + textAreaBounds.getMinY();

        double nearestIndex = text.getNearestPathIndex(textX);
        double relocateX = posX + text.getNearestPathX(textX);

        if (relocateX < 0) relocateX = 0;
        double relocateY = posY;
        if (relocateY < 0) relocateY = 0;

        caret.getStackPane().relocate(relocateX, relocateY);

        caret.restartPulse();

        log.info("posX " + (posX + textX) + " posY " + posY);


    }
}
