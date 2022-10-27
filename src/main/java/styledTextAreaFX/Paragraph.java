package styledTextAreaFX;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Paragraph extends FlowPane {

    private UUID uuid;
    private List<TextExtended> listText;

    public Paragraph() {
        uuid = UUID.randomUUID();
        rowValignmentProperty().set(VPos.BASELINE);
        listText = new ArrayList<>();
    }

    public void addMe(ScrollPane scrollPane, FlowPane textOverlay) {
        prefWidthProperty().bind(scrollPane.widthProperty());
        listText.stream().forEach(p -> getChildren().add(p));
        textOverlay.getChildren().add(this);
    }

    public void addWords(Word... words) {
        int lastTextIndex = -1;
        if(listText.size() > 0){
            lastTextIndex = listText.size() - 1;
        }
        for(Word word:words) {
            for (TextExtended text : word.getTextList()) {
                ++lastTextIndex;
                listText.add(text.addMe(lastTextIndex));
            }
        }
    }

    public List<TextExtended> getListText() {
        return listText;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "listText=" + listText.stream().map(p->p.getText()).collect(Collectors.joining(";")) +
                '}';
    }
}
