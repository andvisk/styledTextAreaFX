package styledTextAreaFX;

import javafx.concurrent.Task;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

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

    private boolean mouseIsDown = false;

    private List<Paragraph> paragraphList;

    private ExecutorService selectionExecutorService;

    public StyledTextAreaFX(StackPane rootElement) {

        startExecutorService();

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
        setOnMouseMoved();
    }

    public void startExecutorService() {

        if (selectionExecutorService == null || selectionExecutorService.isShutdown() || selectionExecutorService.isTerminated()) {
            int poolSize = 1;
            int queueSize = 2;
            RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();

            selectionExecutorService = new ThreadPoolExecutor(poolSize, poolSize,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(queueSize),
                    handler);


        }
    }

    private void onMousePress() {
        scrollPane.setOnMousePressed((mouseEvent) -> {
            mouseIsDown = true;
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
            mouseIsDown = false;
        });
    }

    private void setOnMouseMoved() {
        rootElement.setOnMouseMoved((mouseEvent) -> {
            log.info("mouse is "+((mouseIsDown)?"down":"released")+", move " + System.currentTimeMillis());
            if (mouseIsDown)
                mouseIsMovingWithoutRelease(mouseEvent.getX(), mouseEvent.getY());
        });
        rootElement.setOnMouseDragged((mouseEvent) -> {
            log.info("mouse is "+((mouseIsDown)?"down":"released")+", move " + System.currentTimeMillis());
            if (mouseIsDown)
                mouseIsMovingWithoutRelease(mouseEvent.getX(), mouseEvent.getY());
        });
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
            if (text != null) {
                MousePosition mousePositionLocal = text.getLocalMousePosition(mouseGlobalX, mouseGlobalY);
                PathIndex nearestPathIndex = new PathIndex(text, mousePositionLocal.x());
                // if mouse release is not the same index or different text
                if (nearestPathIndex.getNearestIndex() != caret.getNearestPathIndex().getNearestIndex() || caret.getiAmOnText().getUuid().compareTo(text.getUuid()) != 0) {
                    textSelection = new TextSelection(paragraphList, nearestPathIndex, caret.getNearestPathIndex());

                    textSelection.setConsumer(p -> ((TextSelection) p).selectTexts());
                    log.info("mouse release --------------------------------- ");
                    for (TextExtended textSel : textSelection.getSelectedTextList()) {
                        log.info("mouse release, selected text: " + textSel.toString());
                    }
                    log.info("mouse release --------------------------------- ");
                } else {
                    //the same text, the same index
                    if (nearestPathIndex.getNearestIndex() == caret.getNearestPathIndex().getNearestIndex() || caret.getiAmOnText().getUuid().compareTo(text.getUuid()) == 0) {
                        textSelection = new TextSelection(paragraphList, null, null);
                        textSelection.setConsumer(p -> ((TextSelection) p).deselectTexts());
                    }
                }
            } else {
                textSelection = new TextSelection(paragraphList, null, null);
                textSelection.setConsumer(p -> ((TextSelection) p).deselectTexts()); // mouse release out of text area //todo select by nearest text
            }
            return textSelection;
        });

        task.setOnSucceeded(e -> {
            TextSelection textSelection = task.getValue();
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

    public Caret getCaret() {
        return caret;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (!selectionExecutorService.isShutdown())
                selectionExecutorService.shutdown();
        } finally {
            super.finalize();
        }
    }
}
