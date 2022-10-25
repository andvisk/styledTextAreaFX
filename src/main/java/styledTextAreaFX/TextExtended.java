package styledTextAreaFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextExtended extends Text {

    private Logger log = LogManager.getLogger(this.getClass());
    private StyledTextAreaFX styledTextAreaFX;

    public TextExtended(String text) {
        super(text);
        mouseActionsInit();
    }

    private void mouseActionsInit() {
        onMousePress();
        onMouseRelease();
    }

    private void onMousePress() {
        super.setOnMousePressed((mouseEvent) -> {

            styledTextAreaFX.moveCaret(mouseEvent.getX(), mouseEvent.getY(), this.getBoundsInParent().getMinX(), this.getBoundsInParent().getMinY(), this.getBoundsInLocal().getHeight(), this.getBaselineOffset());

            //log.info(mouseEvent.getX() + " - " + mouseEvent.getY() + ", layout " + this.getBoundsInParent().getMinX() + " - " + this.getBoundsInParent().getMinY() + " height " + this.boundsInLocalProperty().get().getHeight());
        });
    }

    public void addMeToStyledArea(StyledTextAreaFX styledTextAreaFX){
        final Rectangle redBorder = new Rectangle(0, 0, Color.TRANSPARENT);
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

        });
        styledTextAreaFX.getTextFlowPane().getChildren().add(this);
        styledTextAreaFX.getTextFlowPane().getChildren().add(redBorder);
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

}
