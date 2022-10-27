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
import java.util.UUID;

public class TextExtended extends Text {

    private UUID uuid;
    private Logger log = LogManager.getLogger(this.getClass());
    private StyledTextAreaFX styledTextAreaFX;
    private List<Path> paths;
    private String font;
    private double fontSize;
    private Color fillColor;

    public TextExtended(String text, String font, double fontSize, Color fillColor, StyledTextAreaFX styledTextAreaFX) {
        super(text);
        uuid = UUID.randomUUID();
        setFont(Font.font(font, fontSize));
        setFill(fillColor);
        this.font = font;
        this.fontSize = fontSize;
        this.fillColor = fillColor;
        this.styledTextAreaFX = styledTextAreaFX;

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

    //todo remove
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

    public MousePosition getLocalMousePosition(double globalX, double globalY){
        Bounds textBoundsInParagraph = getBoundsInParent();
        Bounds textBoundsInAllParagraphsFlowPane = getParagraph().localToParent(textBoundsInParagraph);
        return new MousePosition(globalX - textBoundsInAllParagraphsFlowPane.getMinX(), globalY - textBoundsInAllParagraphsFlowPane.getMinY());
    }

    public Paragraph getParagraph() {
        return (Paragraph) this.getParent();
    }

    public List<Path> getPaths() {
        if (paths == null || paths.size() <= 0) {
            calculatePaths();
        }
        return paths;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "TextExtended{" + this.getText() + "}";
    }


}
