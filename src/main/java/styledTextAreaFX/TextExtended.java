package styledTextAreaFX;

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
            styledTextAreaFX.moveCaret(mouseEvent.getX(), mouseEvent.getY(), 20);

            log.info(mouseEvent.getX() + " ----- " + mouseEvent.getY());
        });
    }

    public void addMeToStyledArea(StyledTextAreaFX styledTextAreaFX){
        styledTextAreaFX.getTextFlowPane().getChildren().add(this);
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
