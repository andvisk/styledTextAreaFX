package styledTextAreaFX;

import javafx.geometry.VPos;
import javafx.scene.layout.FlowPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StyledTextAreaFX extends FlowPane {

    private Logger log = LogManager.getLogger(this.getClass());

    public StyledTextAreaFX() {
        super();
        super.rowValignmentProperty().set(VPos.BASELINE);

    }

    public void add(TextExtended text) {

        super.getChildren().add(text);

    }
}
