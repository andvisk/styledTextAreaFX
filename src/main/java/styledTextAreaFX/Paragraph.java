package styledTextAreaFX;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {

    private FlowPane textFlowPane;
    private List<TextExtended> listText;

    public Paragraph(){
        textFlowPane = new FlowPane();
        textFlowPane.rowValignmentProperty().set(VPos.BASELINE);
        listText = new ArrayList<>();
    }

    public void addMe(ScrollPane scrollPane, FlowPane textOverlay){
        textFlowPane.prefWidthProperty().bind(scrollPane.widthProperty());
        listText.stream().forEach(p->textFlowPane.getChildren().add(p));
        textOverlay.getChildren().add(textFlowPane);
    }

    public void addTexts(TextExtended... texts){
        textFlowPane.getChildren().addAll(texts);
    }

    public FlowPane getTextFlowPane() {
        return textFlowPane;
    }

    public List<TextExtended> getListText() {
        return listText;
    }

}
