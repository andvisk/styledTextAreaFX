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
import javafx.scene.text.Font;
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
    private String font;
    private double fontSize;
    private Color fillColor;

    public TextExtended(String text, String font, double fontSize, Color fillColor, StyledTextAreaFX styledTextAreaFX) {
        super(text);
        setFont(Font.font(font, fontSize));
        setFill(fillColor);
        this.font = font;
        this.fontSize = fontSize;
        this.fillColor = fillColor;
        this.styledTextAreaFX = styledTextAreaFX;
        /*onMousePress();
        onMouseReleased();*/
    }

    //todo remove
    private void onMousePress() {
        super.setOnMousePressed((mouseEvent) -> {
            log.info("root bounds: " + this.getBoundsInParent());
            styledTextAreaFX.getCaret().moveCaret(mouseEvent.getX(), mouseEvent.getY(), this);
        });
    }

    //todo remove
    private void onMouseReleased() {
        super.setOnMouseReleased((mouseEvent) -> {
            styledTextAreaFX.selectText(mouseEvent.getX(), mouseEvent.getY(), this);
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

    public List<Path> getPaths() {
        if (paths == null || paths.size() <= 0) {
            calculatePaths();
        }
        return paths;
    }

    @Override
    public String toString() {
        return "TextExtended{" + this.getText() + "}";
    }
}
