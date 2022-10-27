package styledTextAreaFX;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Word {

    private List<TextExtended> textList;

    public Word(String word, String font, double size, Color color, StyledTextAreaFX styledTextAreaFX) {
        textList = new ArrayList<>();
        textList.add(new TextExtended(word, font, size, color, styledTextAreaFX));
    }

    public Word(List<TextExtended> textList) {
        this.textList = textList;
    }

    public List<TextExtended> getTextList() {
        return textList;
    }
}
