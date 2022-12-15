package styledtextareafx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.concurrent.Task;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StyledTextAreaFX {

    private Logger log = LogManager.getLogger(this.getClass());

    private StackPane rootElement;
    private ScrollPane scrollPane;
    private Group caretOverlay;
    private FlowPane textOverlay;
    private Caret caret;
    private Group rootGroup;

    private TextSelection textSelection;
    private List<Paragraph> paragraphList;

    private ExecutorService selectionExecutorService;

    public StyledTextAreaFX(StackPane rootElement, Stage primaryStage) {

        startExecutorService();

        textSelection = new TextSelection();

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

        primaryStage.addEventFilter(WindowEvent.WINDOW_HIDING, e -> {
            shutDownSelectionExecutorService();
        });

        rootElement.addEventFilter(MouseEvent.ANY, e -> registerMouseEvents(e));

        rootElement.addEventFilter(KeyEvent.ANY, e -> registerKeyEvents(e));

    }

    public void startExecutorService() {

        if (selectionExecutorService == null || selectionExecutorService.isShutdown()
                || selectionExecutorService.isTerminated()) {
            int poolSize = 1;
            int queueSize = 2;
            RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();

            selectionExecutorService = new ThreadPoolExecutor(poolSize, poolSize,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(queueSize),
                    handler);

        }
    }

    private void registerKeyEvents(KeyEvent keyEvent) {
        if (keyEvent.getCharacter() != KeyEvent.CHAR_UNDEFINED) {
            log.debug(keyEvent.getCharacter());
        }
    }

    private void registerMouseEvents(MouseEvent mouseEvent) {

        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            TextExtended text = getTextByCoord(mouseEvent.getX(), mouseEvent.getY());
            if (text != null) {
                log.info("selected text: " + text.toString());
                textSelection.deselectTexts();
                MousePosition mousePositionLocal = text.getLocalMousePosition(mouseEvent.getX(), mouseEvent.getY());
                caret.moveCaret(mousePositionLocal.x(), mousePositionLocal.y(), text);
            } else {
                textSelection.deselectTexts();
            }
        }
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            TextExtended text = getTextByCoord(mouseEvent.getX(), mouseEvent.getY());
            if (text != null)
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 3) {
                        log.info("Triple clicked");

                        // todo select row

                    } else if (mouseEvent.getClickCount() == 2) {
                        log.info("Double clicked");

                        // todo select word, not textExtended

                        textSelection.deselectTexts();
                        PathIndex startPathIndex = new PathIndex(text, 0);
                        PathIndex endPathIndex = new PathIndex(text);
                        textSelection.selectionChange(paragraphList, startPathIndex, endPathIndex);
                        textSelection.selectTexts();
                    }

                }
        }

        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            mouseIsMovingWithoutRelease(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    public void addParagraphs(Paragraph... paragraphs) {
        paragraphList.addAll(Arrays.asList(paragraphs));
        Arrays.asList(paragraphs).stream().forEach(p -> p.addMe(scrollPane, textOverlay));
    }

    private TextExtended getTextByCoord(double mouseEventX, double mouseEventY) {
        Paragraph paragraph = getParagraphByCoord(mouseEventX, mouseEventY);
        if (paragraph == null)
            return null;
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

    private void mouseIsMovingWithoutRelease(double mouseGlobalX, double mouseGlobalY) {
        Task<TextSelection> task = JFxTasksUtils.createTask(() -> {
            TextSelection textSelection = null;
            TextExtended text = getTextByCoord(mouseGlobalX, mouseGlobalY);

            if (text == null) {
                text = getNearestTextExtended(mouseGlobalX, mouseGlobalY);
            }

            if (text != null) {
                MousePosition mousePositionLocal = text.getLocalMousePosition(mouseGlobalX, mouseGlobalY);
                PathIndex nearestPathIndex = new PathIndex(text, mousePositionLocal.x());

                // if mouse move is not the same index or different text
                if (nearestPathIndex.getNearestIndex() != caret.getNearestPathIndex().getNearestIndex()
                        || caret.getiAmOnText().getUuid().compareTo(text.getUuid()) != 0) {
                    textSelection = new TextSelection();
                    textSelection.selectionChange(paragraphList, nearestPathIndex, caret.getNearestPathIndex());

                    textSelection.setConsumer(p -> ((TextSelection) p).selectTexts());
                    for (TextExtended textSel : textSelection.getSelectedTextList()) {
                        log.info("selected text: " + textSel.toString());
                    }
                } else {

                    // the same text, the same index
                    if (nearestPathIndex.getNearestIndex() == caret.getNearestPathIndex().getNearestIndex()
                            || caret.getiAmOnText().getUuid().compareTo(text.getUuid()) == 0) {
                        textSelection = new TextSelection();
                        textSelection.selectionChange(paragraphList, null, null);
                        textSelection.setConsumer(p -> ((TextSelection) p).deselectTexts());
                    }
                }
            } else {

                textSelection = new TextSelection();
                textSelection.selectionChange(paragraphList, null, null);
                textSelection.setConsumer(p -> ((TextSelection) p).deselectTexts()); // mouse move out of text area
                                                                                     // //todo select by nearest text
            }
            log.debug("mouse move, " + System.currentTimeMillis());
            return textSelection;
        });

        task.setOnSucceeded(e -> {
            textSelection = task.getValue();
            if (textSelection != null)
                textSelection.getConsumer().accept(textSelection);
            log.info("mouse is down, move, success " + System.currentTimeMillis());
        });
        task.setOnFailed(e -> {
            log.error(task.getException().getMessage(), task.getException());
        });
        if (!selectionExecutorService.isShutdown())
            selectionExecutorService.execute(task);
    }

    private Paragraph getNearestParagraph(double mouseGlobalY) {
        double lambda = Double.MAX_VALUE;
        Paragraph nearestParagraph = null;
        for (Paragraph paragraph : paragraphList) {
            if (lambda > Math.abs(paragraph.getBoundsInParent().getMinY() - mouseGlobalY)) {
                lambda = Math.abs(paragraph.getBoundsInParent().getMinY() - mouseGlobalY);
                nearestParagraph = paragraph;
            }
            if (lambda > Math.abs(paragraph.getBoundsInParent().getMaxY() - mouseGlobalY)) {
                lambda = Math.abs(paragraph.getBoundsInParent().getMaxY() - mouseGlobalY);
                nearestParagraph = paragraph;
            }
        }
        log.debug("nearest paragraph:" + nearestParagraph.toString());
        return nearestParagraph;
    }

    private Point2D getNearestPoint(TextExtended textExtended, double mouseGlobalX, double mouseGlobalY) {
        Bounds bounds = textExtended.getBoundsInAllParagraphsFlowPane();

        double x = 0;
        double y = 0;

        if (bounds.getMinX() > mouseGlobalX) {
            x = bounds.getMinX();
        } else if (bounds.getMaxX() < mouseGlobalX) {
            x = bounds.getMaxX();
        } else {
            x = mouseGlobalX;
        }
        if (bounds.getMinY() > mouseGlobalY) {
            y = bounds.getMinY();
        } else if (bounds.getMaxY() < mouseGlobalY) {
            y = bounds.getMaxY();
        } else {
            y = mouseGlobalY;
        }

        Point2D point = new Point2D(x, y);

        return point;
    }

    private TextExtended getNearestTextExtended(double mouseGlobalX, double mouseGlobalY) {
        Point2D mousePoint = new Point2D(mouseGlobalX, mouseGlobalY);
        double minDist = Double.MAX_VALUE;
        Paragraph nearestParagraph = getNearestParagraph(mouseGlobalY);
        TextExtended nearestTextExtended = null;
        for (TextExtended textExtended : nearestParagraph.getListText()) {
            Point2D textPoint = getNearestPoint(textExtended, mouseGlobalX, mouseGlobalY);
            double distance = textPoint.distance(mousePoint);
            if (distance < minDist) {
                minDist = distance;
                nearestTextExtended = textExtended;
            }
        }
        return nearestTextExtended;
    }

    public Caret getCaret() {
        return caret;
    }

    public void shutDownSelectionExecutorService() {
        log.info("shutdown");
        if (!selectionExecutorService.isShutdown())
            selectionExecutorService.shutdown();
    }

}
