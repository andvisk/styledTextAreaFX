package styledTextAreaFX;

import java.util.List;

public class TextSelection {
    private List<TextExtended> selectedTextList;
    private PathIndex startSelection;
    private PathIndex endSelection;

    public TextSelection(List<Paragraph> paragraphs, PathIndex nearestPathIndex1, PathIndex nearestPathIndex2) {
        Paragraph startParagraph =
                (nearestPathIndex1.getText().getParagraph().getBoundsInParent().getMinY()
                        < nearestPathIndex2.getText().getParagraph().getBoundsInParent().getMinY()) ?
                        nearestPathIndex1.getText().getParagraph() :
                        nearestPathIndex2.getText().getParagraph();
        Paragraph endParagraph = (startParagraph.getUuid().compareTo(nearestPathIndex1.getText().getParagraph().getUuid()) == 0) ?
                nearestPathIndex2.getText().getParagraph() : nearestPathIndex1.getText().getParagraph();

        int startParIndex = -1;
        int endIndex = -1;
        for(int i = 0; i < paragraphs.size(); i++){
            aaa
        }

        List<Paragraph> selectedPar = null;
    }
}
