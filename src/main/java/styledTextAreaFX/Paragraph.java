package styledTextAreaFX;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class Paragraph extends FlowPane {

    private List<TextExtended> listText;

    public Paragraph(){
        rowValignmentProperty().set(VPos.BASELINE);
        listText = new ArrayList<>();
    }

    public void addMe(ScrollPane scrollPane, FlowPane textOverlay){
        prefWidthProperty().bind(scrollPane.widthProperty());
        listText.stream().forEach(p->getChildren().add(p));
        textOverlay.getChildren().add(this);
    }

    public void addTexts(TextExtended... texts){
        getChildren().addAll(texts);
    }

    public List<TextExtended> getListText() {
        return listText;
    }

}
