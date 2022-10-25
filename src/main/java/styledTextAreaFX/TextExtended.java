package styledTextAreaFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextExtended extends Text {

    private Logger log = LogManager.getLogger(this.getClass());
    private StyledTextAreaFX styledTextAreaFX;
    private List<Path> paths;

    public TextExtended(String text, StyledTextAreaFX styledTextAreaFX) {
        super(text);
        this.styledTextAreaFX = styledTextAreaFX;
        mouseActionsInit();
    }

    private void mouseActionsInit() {
        onMousePress();
        onMouseRelease();
    }

    private void onMousePress() {
        super.setOnMousePressed((mouseEvent) -> {

            styledTextAreaFX.moveCaret(mouseEvent.getX(), mouseEvent.getY(), this);

        });
    }

    public void calculatePaths() {
        String text = this.getText();
        clearPaths();
        for (int i = 0; i < text.length(); i++) {
            paths.add(new Path(this.rangeShape(i, i + 1)));
        }
        log.info(paths);
    }

    public void clearPaths() {
        paths = new ArrayList<>();
    }

    public void addMe(Paragraph paragraph) {

        /*final Rectangle redBorder = new Rectangle(0, 0, Color.TRANSPARENT);
        redBorder.setStroke(Color.RED);
        redBorder.setManaged(false);
        redBorder.setMouseTransparent(true);
        TextExtended textExtendedThis = this;
        this.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> observable,
                                Bounds oldValue, Bounds newValue) {

                redBorder.setLayoutX(textExtendedThis.getBoundsInParent().getMinX());
                redBorder.setLayoutY(textExtendedThis.getBoundsInParent().getMinY());
                redBorder.setWidth(textExtendedThis.getBoundsInParent().getWidth());
                redBorder.setHeight(textExtendedThis.getBoundsInParent().getHeight());
            }

        });*/
        /*styledTextAreaFX.getTextFlowPane().getChildren().add(this);
        styledTextAreaFX.getTextFlowPane().getChildren().add(redBorder);*/
        this.styledTextAreaFX = styledTextAreaFX;
    }

    private void onMouseRelease() {
        /*super.setOnMouseReleased((mouseEvent) -> {
            super.caretPositionProperty().set(7);

            PathElement[] pathElements = super.rangeShape(1, 2);
            Path path = new Path(pathElements);

            log.info(super.selectionEndProperty().get() + " -----");
        });*/
    }

    public List<Path> getPaths() {
        if (paths == null || paths.size() <= 0) {
            calculatePaths();
        }
        return paths;
    }

    public int getNearestPathIndex(double x) {
        double closestX = -1;
        List<Path> paths = getPaths();
        int index = -1;
        int indexToReturn = -1;
        for (Path path : paths) {
            ++index;
            double deltaLeft = Math.abs(path.getBoundsInParent().getMinX() - x);
            double deltaRight = Math.abs(path.getBoundsInParent().getMinX() + path.getBoundsInLocal().getWidth() - x);
            if(closestX < 0 || closestX > deltaLeft){
                closestX = deltaLeft;
                indexToReturn = index;
            }
            if(closestX > deltaRight){
                closestX = deltaRight;
                indexToReturn = index + 1;
            }
        }
        log.info("nearest index " + indexToReturn);
        return indexToReturn;
    }

    public double getNearestPathX(double x){
        double xToReturn = - 1;
        List<Path> paths = getPaths();
        int index = getNearestPathIndex(x);
        if(index == paths.size()){
            Path path = paths.get(paths.size()-1);
            return path.getBoundsInParent().getMinX() + path.getBoundsInLocal().getWidth();
        }else{
            return paths.get(index).getBoundsInParent().getMinX();
        }
    }
}
