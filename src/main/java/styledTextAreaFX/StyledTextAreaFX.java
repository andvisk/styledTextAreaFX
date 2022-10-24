package styledTextAreaFX;

import javafx.geometry.VPos;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class StyledTextAreaFX extends FlowPane {

    public StyledTextAreaFX(){
        super();
        super.rowValignmentProperty().set(VPos.BASELINE);
    }

    public void add(Text text){
        super.getChildren().add(text);
    }
}
