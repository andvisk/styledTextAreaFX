package styledtextareafx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

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

    public void addString(String string, String font, double size, Color color, StyledTextAreaFX styledTextAreaFX) {
        List<String> splits = Arrays.asList(string.split(" ", -1));
        List<String> splitsWithSpaces = new ArrayList<>();

        for (String split : splits) {
            if (split.length() > 0) {
                splitsWithSpaces.add(split);
                splitsWithSpaces.add(" ");
            } else {
                splitsWithSpaces.add(" ");
            }
        }

        List<Word> words = splitsWithSpaces.stream().map(p ->
                new Word(p, font, size, color, styledTextAreaFX)
        ).toList();
        addWords(words);
    }

    public void addWords(List<Word> words) {
        int lastTextIndex = -1;
        if (listText.size() > 0) {
            lastTextIndex = listText.size() - 1;
        }
        for (Word word : words) {
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
                "listText=" + listText.stream().map(p -> p.getText()).collect(Collectors.joining(";")) +
                '}';
    }
}
