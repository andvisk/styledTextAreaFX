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

        onMousePress();
        onMouseReleased();
    }

    private void onMousePress() {
        scrollPane.setOnMousePressed((mouseEvent) -> {
            //styledTextAreaFX.getCaret().moveCaret(mouseEvent.getX(), mouseEvent.getY(), this);
            log.info("scroll pane pressed"); aa
        });
    }

    private void onMouseReleased() {
        scrollPane.setOnMouseReleased((mouseEvent) -> {
            //styledTextAreaFX.selectText(mouseEvent.getX(), mouseEvent.getY(), this);
        });
    }

    public void addParagraphs(Paragraph... paragraphs) {
        paragraphList.addAll(Arrays.asList(paragraphs));
        Arrays.asList(paragraphs).stream().forEach(p -> p.addMe(scrollPane, textOverlay));
    }

    public void selectText(double textX, double textY, TextExtended text){

    }

    public Caret getCaret(){
        return caret;
    }
}
