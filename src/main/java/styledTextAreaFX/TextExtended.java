package styledTextAreaFX;

import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextExtended extends Text {

    private Logger log = LogManager.getLogger(this.getClass());

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

            log.info(mouseEvent.getX() + " ----- " + mouseEvent.getY());
        });
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
