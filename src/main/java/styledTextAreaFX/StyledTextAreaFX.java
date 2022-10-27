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

    private StackPane rootElement;
    private ScrollPane scrollPane;
    private Group caretOverlay;
    private FlowPane textOverlay;
    private Caret caret;
    private Group rootGroup;

    private List<Paragraph> paragraphList;

    public StyledTextAreaFX(StackPane rootElement) {

        this.rootElement = rootElement;

        scrollPane = new ScrollPane();

        rootGroup = new Group();

        caretOverlay = new Group();
        textOverlay = new FlowPane();

        caret = new Caret(caretOverlay);

        rootGroup.getChildren().addAll(textOverlay, caretOverlay);
        scrollPane.setContent(rootGroup);
        rootElement.getChildren().addAll(scrollPane);

        paragraphList = new ArrayList<>();

        onMousePress();
        onMouseReleased();
    }

    private void onMousePress() {
        scrollPane.setOnMousePressed((mouseEvent) -> {
            TextExtended text = getTextByCoord(mouseEvent.getX(), mouseEvent.getY());
            if (text != null) {
                log.info("selected text: " + text.toString());
                MousePosition mousePositionLocal = text.getLocalMousePosition(mouseEvent.getX(), mouseEvent.getY());
                caret.moveCaret(mousePositionLocal.x(), mousePositionLocal.y(), text);
            }
        });
    }

    private void onMouseReleased() {
        rootElement.setOnMouseReleased((mouseEvent) -> {
            TextExtended text = getTextByCoord(mouseEvent.getX(), mouseEvent.getY());
            if (text != null) {
                MousePosition mousePositionLocal = text.getLocalMousePosition(mouseEvent.getX(), mouseEvent.getY());
                PathIndex nearestPathIndex = new PathIndex(text, mousePositionLocal.x());
                // if mouse release is not the same index or different text
                if (nearestPathIndex.getNearestIndex() != caret.getNearestPathIndex().getNearestIndex() || caret.getiAmOnText().getUuid().compareTo(text.getUuid()) != 0) {
                    TextSelection textSelection = new TextSelection(paragraphList, nearestPathIndex, caret.getNearestPathIndex());
                    log.info("mouse release, selecting text: " + text.toString() + ", len=" + text.getText().length() + ", selection index " + nearestPathIndex.getNearestIndex());
                }
            }
        });
    }

    public void addParagraphs(Paragraph... paragraphs) {
        paragraphList.addAll(Arrays.asList(paragraphs));
        Arrays.asList(paragraphs).stream().forEach(p -> p.addMe(scrollPane, textOverlay));
    }

    private TextExtended getTextByCoord(double mouseEventX, double mouseEventY) {
        Paragraph paragraph = getParagraphByCoord(mouseEventX, mouseEventY);
        TextExtended text = paragraph.getListText().stream().filter(p -> {
            Bounds textBoundsInParagraph = p.getBoundsInParent();
            Bounds textBoundsInAllParagraphsFlowPane = paragraph.localToParent(textBoundsInParagraph);
            return checkCoordIsWithinBounds(textBoundsInAllParagraphsFlowPane, mouseEventX, mouseEventY);
        }).findAny().orElse(null);
        return text;
    }

    private Paragraph getParagraphByCoord(double mouseEventX, double mouseEventY) {
        Paragraph paragraph = paragraphList.stream().parallel().filter(p -> {
            Bounds paragraphBoundsInParent = p.getBoundsInParent();
            return checkCoordIsWithinBounds(paragraphBoundsInParent, mouseEventX, mouseEventY);
        }).findAny().orElse(null);
        return paragraph;
    }

    private boolean checkCoordIsWithinBounds(Bounds bounds, double checkX, double checkY) {
        double nodeBoundsX = bounds.getMinX();
        double nodeBoundsY = bounds.getMinY();
        double nodeBoundsWidth = bounds.getWidth();
        double nodeBoundsHeight = bounds.getHeight();
        if (checkX >= nodeBoundsX && checkX <= nodeBoundsX + nodeBoundsWidth &&
                checkY >= nodeBoundsY && checkY <= nodeBoundsY + nodeBoundsHeight) {
            return true;
        }
        return false;
    }

    //todo
    public void selectText(double textX, double textY, TextExtended text) {

    }

    public Caret getCaret() {
        return caret;
    }
}
